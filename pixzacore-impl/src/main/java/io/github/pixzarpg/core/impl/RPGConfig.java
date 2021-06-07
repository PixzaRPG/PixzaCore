package io.github.pixzarpg.core.impl;

import io.github.pixzarpg.core.api.APIRPGConfig;
import org.bukkit.configuration.file.FileConfiguration;

public class RPGConfig implements APIRPGConfig {

    private final FileConfiguration configuration;


    public RPGConfig(FileConfiguration config) {
        this.configuration = config;
    }

    public String getWorldName() {
        return this.configuration.getString("world-name");
    }

}
