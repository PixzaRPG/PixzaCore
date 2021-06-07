package io.github.pixzarpg.core.impl;

import io.github.pixzarpg.core.api.APIRPGManager;
import io.github.pixzarpg.core.api.datapacks.APIDataPackManager;
import io.github.pixzarpg.core.api.world.APIWorldManager;
import io.github.pixzarpg.core.impl.datapacks.DataPackManager;
import io.github.pixzarpg.core.impl.world.WorldManager;
import org.bukkit.plugin.java.JavaPlugin;

public class RPGManager implements APIRPGManager<JavaPlugin> {

    private final APIDataPackManager dataPackManager = new DataPackManager(this);
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

    @Override
    public JavaPlugin getPlugin() {
        return this.plugin;
    }

    @Override
    public APIWorldManager getWorldManager() {
        return this.worldManager;
    }

    @Override
    public APIDataPackManager getDataPackManager() {
        return this.dataPackManager;
    }

    @Override
    public void close() {

    }

}
