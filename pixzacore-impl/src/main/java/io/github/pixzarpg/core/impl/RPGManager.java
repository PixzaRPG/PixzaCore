package io.github.pixzarpg.core.impl;

import io.github.pixzarpg.core.api.APIRPGManager;
import io.github.pixzarpg.core.impl.datapacks.DataPackManager;
import io.github.pixzarpg.core.impl.world.WorldManager;
import org.bukkit.plugin.java.JavaPlugin;

public class RPGManager implements APIRPGManager {

    private final DataPackManager dataPackManager = new DataPackManager(this);
    private final WorldManager worldManager = new WorldManager(this);

    private final RPGConfig config;
    private final JavaPlugin plugin;


    public RPGManager(RPGConfig config, JavaPlugin plugin) {
        this.config = config;
        this.plugin = plugin;

        this.worldManager.initialize();
        this.dataPackManager.load();
    }

    public RPGConfig getConfig() {
        return this.config;
    }

    public DataPackManager getDataPackManager() {
        return this.dataPackManager;
    }

    public JavaPlugin getPlugin() {
        return this.plugin;
    }

    @Override
    public void close() {

    }

}
