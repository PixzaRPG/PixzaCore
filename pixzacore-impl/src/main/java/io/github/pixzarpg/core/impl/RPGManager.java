package io.github.pixzarpg.core.impl;

import io.github.pixzarpg.core.api.APIRPGManager;
import io.github.pixzarpg.core.impl.datapacks.DataPackManager;
import org.bukkit.plugin.java.JavaPlugin;

public class RPGManager implements APIRPGManager {

    private final DataPackManager dataPackManager = new DataPackManager(this);

    private final JavaPlugin plugin;


    public RPGManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.dataPackManager.load();
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
