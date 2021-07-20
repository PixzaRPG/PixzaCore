package io.github.pixzarpg.core.impl.spigot.config;

import io.github.pixzarpg.core.impl.spigot.config.database.SQLDatabaseConfig;
import org.bukkit.configuration.Configuration;

public class RPGConfig {

    private final Configuration configuration;

    private final SQLDatabaseConfig sqlConfig;


    public RPGConfig(Configuration config) {
        this.configuration = config;
        this.sqlConfig = new SQLDatabaseConfig(config);
    }

    public String getWorldName() {
        return this.configuration.getString("world-name");
    }

    public SQLDatabaseConfig getSQLConfig() {
        return this.sqlConfig;
    }

}
