package io.github.pixzarpg.core.loadbalancing.bungee.load;

import io.github.pixzarpg.core.loadbalancing.bungee.PixzaBungee;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Optional;

public class LoadBalancer implements Listener {

    // Key that stores load of all servers
    private final static String REDIS_SERVER_KEY = "pizzabungee_server_load";

    private final PixzaBungee bungee;
    private final JedisPool connectionPool;


    public LoadBalancer(PixzaBungee bungee) {
        this.bungee = bungee;

        this.connectionPool = new JedisPool(
                this.bungee.getConfig().getRedisHost(),
                this.bungee.getConfig().getRedisPort(),
                this.bungee.getConfig().getRedisUsername(),
                this.bungee.getConfig().getRedisPassword());

        bungee.getProxy().getPluginManager().registerListener(bungee, this);
    }

    @EventHandler
    public void onPlayerConnect(ServerConnectEvent event) {
        if (event.getReason() != ServerConnectEvent.Reason.JOIN_PROXY) {
            event.setCancelled(true);
            return;
        }

        ServerInfo recommendServer = this.getRecommendedServer();
        if (recommendServer != null) {
            event.setTarget(recommendServer);
        } else {
            event.getPlayer().disconnect(new TextComponent("Sorry, there was no available server found. Please try again later."));
            event.setCancelled(true);
        }
    }

    private ServerInfo getRecommendedServer() {
        try (Jedis jedis = this.connectionPool.getResource()) {
            Optional<ServerInfo> bestServer = jedis.zrangeByScoreWithScores(REDIS_SERVER_KEY, 0, 1)
                    .stream()
                    .filter(server -> this.bungee.getProxy().getServerInfo(server.getElement()) != null)
                    .filter(data -> data.getScore() < 1)    // Full server
                    .sorted(new TargetSpacityComparator(this.bungee.getConfig().getTargetGameServerCapacity()))
                    .map(tuple -> this.bungee.getProxy().getServerInfo(tuple.getElement()))
                    .findFirst();

            return bestServer.orElse(null);
        }
    }

    public void close() {
        this.bungee.getProxy().getPluginManager().unregisterListener(this);
        this.connectionPool.close();
    }

}
