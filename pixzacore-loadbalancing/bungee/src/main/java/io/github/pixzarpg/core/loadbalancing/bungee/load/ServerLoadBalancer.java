package io.github.pixzarpg.core.loadbalancing.bungee.load;

import com.google.gson.Gson;
import io.github.pixzarpg.core.loadbalancing.bungee.BungeeLoadBalancer;
import net.md_5.bungee.api.config.ServerInfo;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class ServerLoadBalancer extends Thread {

    // Broadcasts all player counts for servers
    private final static String REDIS_PLAYER_COUNT_CHANNEL = "pixza_loadbalancing_playercount";
    // Used to shutdown a bungee's subscribe listener
    private final static String REDIS_SUB_SHUTDOWN_CHANNEL = "pixza_loadbalancing_sub_shutdown";

    private final static Gson GSON = new Gson();

    private final BungeeLoadBalancer bungee;
    private final JedisPool connectionPool;

    private final Map<String, ServerLoadResponse> currentPlayerCounts = new ConcurrentHashMap<>();


    public ServerLoadBalancer(BungeeLoadBalancer bungee) {
        this.bungee = bungee;

        this.connectionPool = new JedisPool(
                this.bungee.getConfig().getRedisHost(),
                this.bungee.getConfig().getRedisPort(),
                this.bungee.getConfig().getRedisUsername(),
                this.bungee.getConfig().getRedisPassword());
    }

    @Override
    public void run() {
        try (Jedis jedis = this.connectionPool.getResource()) {

            jedis.subscribe(new JedisPubSub() {

                @Override
                public void onMessage(String channel, String message) {
                    switch (channel) {
                        case REDIS_PLAYER_COUNT_CHANNEL:
                            ServerLoadResponse response = GSON.fromJson(message, ServerLoadResponse.class);
                            if (response.getSpacity() != -1) {
                                // update player count
                                ServerLoadBalancer.this.currentPlayerCounts.put(response.getServerId(), response);
                            } else {
                                // game server is going down
                                ServerLoadBalancer.this.currentPlayerCounts.remove(response.getServerId());
                            }
                            break;
                        case REDIS_SUB_SHUTDOWN_CHANNEL:
                            // This load balancer is being shutdown, stop listening.
                            if (message.equals(ServerLoadBalancer.this.bungee.getConfig().getProxyId())) {
                                this.unsubscribe();
                                ServerLoadBalancer.this.currentPlayerCounts.clear();
                            }
                            break;
                    }
                }

            }, REDIS_PLAYER_COUNT_CHANNEL, REDIS_SUB_SHUTDOWN_CHANNEL);

        }
    }

    public ServerInfo getRecommendedServer() {
        Optional<ServerInfo> bestServer = Collections.unmodifiableCollection(this.currentPlayerCounts.values())
                .stream()
                .filter(server -> this.bungee.getProxy().getServerInfo(server.getServerId()) != null)
                .filter(server -> server.getSpacity() < 1)
                .min(new TargetSpacityComparator(this.bungee.getConfig().getTargetGameServerCapacity()))
                .map(server -> this.bungee.getProxy().getServerInfo(server.getServerId()));

        return bestServer.orElse(null);
    }

    public void close() {
        try (Jedis jedis = this.connectionPool.getResource()) {
            jedis.publish(REDIS_SUB_SHUTDOWN_CHANNEL, this.bungee.getConfig().getProxyId());
        }
        this.connectionPool.close();
    }

}
