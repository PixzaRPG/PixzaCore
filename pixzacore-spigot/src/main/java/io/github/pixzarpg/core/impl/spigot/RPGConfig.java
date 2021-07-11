package io.github.pixzarpg.core.impl.spigot;

import org.bukkit.configuration.file.FileConfiguration;

public class RPGConfig {

    private final FileConfiguration configuration;


    public RPGConfig(FileConfiguration config) {
        this.configuration = config;
    }

    public String getWorldName() {
        return this.configuration.getString("world-name");
    }

}
