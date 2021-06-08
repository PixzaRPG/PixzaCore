package io.github.pixzarpg.core.bungee;

import net.md_5.bungee.config.Configuration;

public class PixzaBungeeConfig {

    private final Configuration config;


    public PixzaBungeeConfig(Configuration config) {
        this.config = config;
    }

    public float getTargetGameServerCapacity() {
        return this.config.getFloat("server_target_capacity");
    }

}
