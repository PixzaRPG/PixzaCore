package io.github.pixzarpg.core.bungee.load;

import io.github.pixzarpg.core.bungee.PixzaBungeeConfig;
import net.md_5.bungee.api.config.ServerInfo;

import java.util.Collection;

public class LoadBalancer {

    private final PixzaBungeeConfig config;
    private final Collection<ServerInfo> servers;


    public LoadBalancer(PixzaBungeeConfig config, Collection<ServerInfo> servers) {
        this.config = config;
        this.servers = servers;
    }

}
