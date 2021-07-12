package io.github.pixzarpg.core.impl.spigot;

import io.github.pixzarpg.core.impl.spigot.datapacks.DataPackManager;
import io.github.pixzarpg.core.impl.spigot.entities.RPGEntityManager;
import io.github.pixzarpg.core.impl.spigot.items.ItemManager;
import io.github.pixzarpg.core.impl.spigot.world.WorldManager;
import org.bukkit.plugin.java.JavaPlugin;

public class RPGManager {

    private final DataPackManager dataPackManager;
    private final WorldManager worldManager;
    private final ItemManager itemManager;
    private final RPGEntityManager entityManager;

    private final RPGConfig config;
    private final JavaPlugin plugin;


    public RPGManager(RPGConfig config, JavaPlugin plugin) {
        this.config = config;
        this.plugin = plugin;

        this.dataPackManager = new DataPackManager(this);
        this.worldManager = new WorldManager(this);
        this.itemManager = new ItemManager(this);
        this.entityManager = new RPGEntityManager(this);

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

    public RPGEntityManager getEntityManager() {
        return this.entityManager;
    }

    public ItemManager getItemManager() {
        return this.itemManager;
    }

}
