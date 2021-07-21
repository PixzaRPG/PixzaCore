package io.github.pixzarpg.core.impl.spigot;

import io.github.pixzarpg.core.impl.spigot.config.RPGConfig;
import io.github.pixzarpg.core.impl.spigot.database.DatabaseManager;
import io.github.pixzarpg.core.impl.spigot.datapacks.DataPackManager;
import io.github.pixzarpg.core.impl.spigot.entities.RPGEntityManager;
import io.github.pixzarpg.core.impl.spigot.items.ItemManager;
import io.github.pixzarpg.core.impl.spigot.world.WorldManager;
import org.bukkit.plugin.java.JavaPlugin;

public class RPGManager {

    private static RPGManager INSTANCE;

    private final DataPackManager dataPackManager;
    private final WorldManager worldManager;
    private final ItemManager itemManager;
    private final RPGEntityManager entityManager;
    private final DatabaseManager databaseManager;

    private final RPGConfig config;
    private final JavaPlugin plugin;


    protected RPGManager(RPGConfig config, JavaPlugin plugin) {
        INSTANCE = this;
        this.config = config;
        this.plugin = plugin;

        this.databaseManager = new DatabaseManager(this);
        this.worldManager = new WorldManager(this);
        this.itemManager = new ItemManager(this);
        this.entityManager = new RPGEntityManager(this);
        this.dataPackManager = new DataPackManager(this);

        this.worldManager.initialize();
        this.databaseManager.initialize();
        this.dataPackManager.initialize();
    }

    public void close() {
        this.databaseManager.close();
        INSTANCE = null;
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

    public DatabaseManager getDatabaseManager() {
        return this.databaseManager;
    }


    public static RPGManager create(RPGConfig config, JavaPlugin plugin) {
        if (INSTANCE != null) {
            throw new IllegalStateException("Attempted to create RPGManager twice");
        }
        return new RPGManager(config, plugin);
    }

    public static RPGManager getInstance() {
        if (INSTANCE == null) {
            throw new IllegalStateException("Attempted to get instance before instance was created");
        }
        return INSTANCE;
    }

}
