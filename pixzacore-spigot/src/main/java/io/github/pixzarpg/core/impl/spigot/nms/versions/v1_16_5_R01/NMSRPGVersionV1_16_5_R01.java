package io.github.pixzarpg.core.impl.spigot.nms.versions.v1_16_5_R01;

import io.github.pixzarpg.core.impl.spigot.RPGManager;
import io.github.pixzarpg.core.impl.spigot.nms.versions.NMSRPGVersion;
import io.github.pixzarpg.core.impl.spigot.nms.versions.v1_16_5_R01.entities.NMSRPGZombieEntityV1_16_5_R01;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class NMSRPGVersionV1_16_5_R01 extends NMSRPGVersion {

    public static final NMSRPGVersion INSTANCE = new NMSRPGVersionV1_16_5_R01();


    @Override
    public Entity createEntity(RPGManager rpgManager, EntityType entityType, World bukkitWorld) {
        CraftWorld world = (CraftWorld)bukkitWorld;
        switch (entityType) {
            case ZOMBIE:
                return new NMSRPGZombieEntityV1_16_5_R01(rpgManager, world.getHandle()).getBukkitEntity();
            default:
                throw new UnsupportedOperationException("This version of PixzaCore cannot spawn " + entityType);
        }
    }

    @Override
    public void spawnEntity(Entity entity, Location location) {
        CraftEntity rawEntity = (CraftEntity)entity;
        CraftWorld world = (CraftWorld)location.getWorld();

        rawEntity.getHandle().setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        world.addEntity(rawEntity.getHandle(), CreatureSpawnEvent.SpawnReason.CUSTOM);
    }

}
