package io.github.pixzarpg.core.impl.spigot.config.database;


import org.bukkit.configuration.Configuration;

public abstract class DatabaseConfig {

    private final String prefix;
    protected final Configuration pluginConfig;


    public DatabaseConfig(Configuration pluginConfig, String prefix) {
        this.pluginConfig = pluginConfig;
        this.prefix = prefix;
    }

    public String getIp() {
        return this.pluginConfig.getString(this.formatConfigKey("ip"));
    }

    public int getPort() {
        return this.pluginConfig.getInt(this.formatConfigKey("port"));
    }

    public String getUsername() {
        return this.pluginConfig.getString(this.formatConfigKey("username"));
    }

    public String getPassword() {
        return this.pluginConfig.getString(this.formatConfigKey("password"));
    }

    public String formatConfigKey(String path) {
        return this.prefix + "-" + path;
    }

}
