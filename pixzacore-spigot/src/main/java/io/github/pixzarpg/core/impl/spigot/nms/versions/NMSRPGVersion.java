package io.github.pixzarpg.core.impl.spigot.nms.versions;

import io.github.pixzarpg.core.impl.spigot.RPGManager;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public abstract class NMSRPGVersion {

    protected NMSRPGVersion() {}

    public abstract Entity createEntity(RPGManager rpgManager, EntityType entityType, World bukkitWorld);
    public abstract void spawnEntity(Entity entity, Location location);

}
