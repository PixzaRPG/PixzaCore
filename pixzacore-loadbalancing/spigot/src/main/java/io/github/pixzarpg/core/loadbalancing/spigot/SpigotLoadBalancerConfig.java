package io.github.pixzarpg.core.loadbalancing.spigot;

import org.bukkit.configuration.file.FileConfiguration;

public class SpigotLoadBalancerConfig {

    private final FileConfiguration config;


    public SpigotLoadBalancerConfig(FileConfiguration config) {
        this.config = config;
    }

    public String getServerId() {
        return this.config.getString("server_id");
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
