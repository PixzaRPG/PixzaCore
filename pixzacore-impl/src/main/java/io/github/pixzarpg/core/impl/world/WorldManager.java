package io.github.pixzarpg.core.impl.world;

import io.github.pixzarpg.core.impl.RPGConfig;
import io.github.pixzarpg.core.impl.RPGManager;
import io.github.pixzarpg.core.impl.utils.TextUtils;
import io.github.pixzarpg.core.impl.world.regions.RegionManager;
import org.bukkit.World;

public class WorldManager {

    private final static String LOG_PREFIX = "WorldManager";

    private final RPGManager manager;
    private final RegionManager regionManager = new RegionManager(this);

    private World rpgWorld;


    public WorldManager(RPGManager manager) {
        this.manager = manager;
    }

    public void initialize() {

        RPGConfig config = (RPGConfig)this.manager.getConfig();

        this.rpgWorld = this.manager.getPlugin().getServer()
                .getWorld(config.getWorldName());
        if (this.rpgWorld == null) {
            this.manager.getPlugin().getLogger()
                .severe(TextUtils.generateLoggerMessage(LOG_PREFIX, "Could not find a world by the name of \"" + config.getWorldName() + "\""));
            return;
        }

        this.rpgWorld.setAutoSave(false);    // We don't want modifications to the world to persist.

    }
    
    public RegionManager getRegionManager() {
        return this.regionManager;
    }
    
    public World getWorld() {
        return this.rpgWorld;
    }

}
