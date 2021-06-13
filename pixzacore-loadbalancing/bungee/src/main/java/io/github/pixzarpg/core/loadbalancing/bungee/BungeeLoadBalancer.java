package io.github.pixzarpg.core.loadbalancing.bungee;

import io.github.pixzarpg.core.loadbalancing.bungee.load.ServerLoadBalancer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import net.md_5.bungee.event.EventHandler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Level;

public class BungeeLoadBalancer extends Plugin implements Listener {

    private BungeeLoadBalancerConfig config;
    private ServerLoadBalancer loadBalancer;


    @Override
    public void onEnable() {
        try {
            this.configSetup();
        } catch (IOException exception) {
            this.getLogger().log(
                    Level.SEVERE,
                    "Failed to setup configuration for PixzaBngee. Bungee shutting down.",
                    exception);
            this.getProxy().stop();
            return;
        }

        this.loadBalancer = new ServerLoadBalancer(this);
        this.getProxy().getPluginManager().registerListener(this, this);

        this.loadBalancer.start();
    }

    public BungeeLoadBalancerConfig getConfig() {
        return this.config;
    }

    @Override
    public void onDisable() {
        this.loadBalancer.close();
    }

    @EventHandler
    public void onPlayerConnect(ServerConnectEvent event) {
        if (event.getReason() != ServerConnectEvent.Reason.JOIN_PROXY) {
            event.setCancelled(true);
            return;
        }

        ServerInfo recommendServer = this.loadBalancer.getRecommendedServer();
        if (recommendServer != null) {
            event.setTarget(recommendServer);
        } else {
            event.getPlayer().disconnect(new TextComponent("Sorry, there was no available server found. Please try again later."));
            event.setCancelled(true);
        }
    }

    private void configSetup() throws IOException {
        this.getDataFolder().mkdir();

        File configFile = new File(this.getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            Files.copy(this.getResourceAsStream("config.yml"), configFile.toPath());
        }
        this.config = new BungeeLoadBalancerConfig(ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile));
    }

}