package io.github.pixzarpg.core.loadbalancing.bungee;

import net.md_5.bungee.config.Configuration;

public class BungeeLoadBalancerConfig {

    private final Configuration config;


    public BungeeLoadBalancerConfig(Configuration config) {
        this.config = config;
    }

    public double getTargetGameServerCapacity() {
        return this.config.getDouble("server_target_capacity");
    }

    public String getProxyId() {
        return this.config.getString("proxy_id");
    }

    public String getRedisHost() {
        return this.config.getString("host");
    }

    public int getRedisPort() {
        return this.config.getInt("port");
    }

    public String getRedisUsername() {
        return this.config.getString("username");
    }

    public String getRedisPassword() {
        return this.config.getString("password");
    }

}
