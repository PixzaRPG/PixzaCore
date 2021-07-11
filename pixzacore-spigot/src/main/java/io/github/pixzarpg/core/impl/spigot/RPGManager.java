package io.github.pixzarpg.core.impl.spigot;

import io.github.pixzarpg.core.impl.spigot.datapacks.DataPackManager;
import io.github.pixzarpg.core.impl.spigot.world.WorldManager;
import org.bukkit.plugin.java.JavaPlugin;

public class RPGManager {

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

    public JavaPlugin getPlugin() {
        return this.plugin;
    }

    public WorldManager getWorldManager() {
        return this.worldManager;
    }

    public DataPackManager getDataPackManager() {
        return this.dataPackManager;
    }

}
