package io.github.pixzarpg.core.impl.spigot.config.database;

import org.bukkit.configuration.Configuration;

public class SQLDatabaseConfig extends DatabaseConfig {

    public SQLDatabaseConfig(Configuration pluginConfig) {
        super(pluginConfig, "sql");
    }

    public String getDatabase() {
        return this.pluginConfig.getString(this.formatConfigKey("database"));
    }

}
