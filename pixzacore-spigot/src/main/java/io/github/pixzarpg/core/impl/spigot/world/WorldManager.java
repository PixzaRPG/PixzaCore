package io.github.pixzarpg.core.impl.spigot.world;

import io.github.pixzarpg.core.impl.spigot.config.RPGConfig;
import io.github.pixzarpg.core.impl.spigot.RPGManager;
import io.github.pixzarpg.core.impl.spigot.utils.TextUtils;
import io.github.pixzarpg.core.impl.spigot.world.regions.RegionManager;
import org.bukkit.Bukkit;
import org.bukkit.World;

public class WorldManager {

    private final static String LOG_PREFIX = "WorldManager";

    private final RPGManager manager;
    private final RegionManager regionManager;

    private World rpgWorld;


    public WorldManager(RPGManager manager) {
        this.manager = manager;
        this.regionManager = new RegionManager(this);
    }

    public void initialize() {
        RPGConfig config = this.getRPGManager().getConfig();

        this.rpgWorld = this.getRPGManager().getPlugin().getServer()
                .getWorld(config.getWorldName());
        if (this.rpgWorld == null) {
            this.rpgWorld = Bukkit.getWorlds().get(0);
            this.getRPGManager().getPlugin().getLogger()
                .warning(TextUtils.generateLoggerMessage(LOG_PREFIX, "Could not find a world by the name of \"" + config.getWorldName() + "\". Defaulting to world \"" + this.rpgWorld.getName() + "\""));
        }

        this.rpgWorld.setAutoSave(false);    // We don't want modifications to the world to persist.

    }

    public RPGManager getRPGManager() {
        return this.manager;
    }
    
    public RegionManager getRegionManager() {
        return this.regionManager;
    }
    
    public World getWorld() {
        return this.rpgWorld;
    }

}
