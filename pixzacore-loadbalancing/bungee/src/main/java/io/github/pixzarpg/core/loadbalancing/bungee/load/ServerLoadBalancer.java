package io.github.pixzarpg.core.loadbalancing.bungee.load;

import io.github.pixzarpg.core.loadbalancing.bungee.BungeeLoadBalancer;
import net.md_5.bungee.api.config.ServerInfo;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class ServerLoadBalancer extends Thread {

    // Broadcasts all player counts for servers
    private final static String REDIS_PLAYER_COUNT_CHANNEL = "pixza_loadbalancing_playercount";

    // Used to shutdown a bungee's subscribe listener
    private final static String REDIS_SUB_SHUTDOWN_CHANNEL = "pixza_loadbalancing_sub_shutdown";

    private final BungeeLoadBalancer bungee;
    private final JedisPool connectionPool;

    private final Map<String, Double> currentPlayerCounts = new ConcurrentHashMap<>();


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

    }

    public ServerInfo getRecommendedServer() {
        Optional<ServerInfo> bestServer = Collections.unmodifiableSet(this.currentPlayerCounts.entrySet())
                .stream()
                .filter(server -> this.bungee.getProxy().getServerInfo(server.getKey()) != null)
                .filter(server -> server.getValue() < 1)
                .min(new TargetSpacityComparator(this.bungee.getConfig().getTargetGameServerCapacity()))
                .map(server -> this.bungee.getProxy().getServerInfo(server.getKey()));

        return bestServer.orElse(null);
    }

    public void close() {
        this.connectionPool.close();
    }

}
