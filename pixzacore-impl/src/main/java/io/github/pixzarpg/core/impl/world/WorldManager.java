package io.github.pixzarpg.core.impl.world;

import io.github.pixzarpg.core.api.world.APIWorldManager;
import io.github.pixzarpg.core.impl.RPGManager;
import io.github.pixzarpg.core.impl.utils.TextUtils;
import org.bukkit.World;

public class WorldManager implements APIWorldManager {

    private final static String LOG_PREFIX = "WorldManager";

    private final RPGManager manager;

    private World rpgWorld;

    public WorldManager(RPGManager manager) {
        this.manager = manager;
    }

    public void initialize() {

        this.rpgWorld = this.manager.getPlugin().getServer()
                .getWorld(this.manager.getConfig().getWorldName());
        if (this.rpgWorld == null) {
            this.manager.getPlugin().getLogger()
                .severe(TextUtils.generateLoggerMessage(LOG_PREFIX, "Could not find a world by the name of \"" + this.manager.getConfig().getWorldName() + "\""));
            return;
        }

        this.rpgWorld.setAutoSave(false);    // We don't want modifications to the world to persist.

    }

    @Override
    public void close() {

    }
}
