package io.github.pixzarpg.core.bungee.load;

import io.github.pixzarpg.core.bungee.PixzaBungeeConfig;
import net.md_5.bungee.api.config.ServerInfo;
import redis.clients.jedis.JedisPool;

import java.util.Collection;

public class LoadBalancer {

    private final PixzaBungeeConfig config;
    private final Collection<ServerInfo> servers;

    private final JedisPool connectionPool;


    public LoadBalancer(PixzaBungeeConfig config, Collection<ServerInfo> servers) {
        this.config = config;
        this.servers = servers;

        this.connectionPool = new JedisPool(
                this.config.getRedisHost(),
                this.config.getRedisPort(),
                this.config.getRedisUsername(),
                this.config.getRedisPassword());

    }

}
