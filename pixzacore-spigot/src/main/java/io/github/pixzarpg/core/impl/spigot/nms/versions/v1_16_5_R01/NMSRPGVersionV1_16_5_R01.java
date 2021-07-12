package io.github.pixzarpg.core.impl.spigot.nms.versions.v1_16_5_R01;

import io.github.pixzarpg.core.impl.spigot.RPGManager;
import io.github.pixzarpg.core.impl.spigot.nms.versions.NMSRPGVersion;
import io.github.pixzarpg.core.impl.spigot.nms.versions.v1_16_5_R01.entities.NMSRPGZombieEntityV1_16_5_R01;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public class NMSRPGVersionV1_16_5_R01 extends NMSRPGVersion {

    public static final NMSRPGVersion INSTANCE = new NMSRPGVersionV1_16_5_R01();


    @Override
    public Entity createEntity(RPGManager rpgManager, EntityType entityType, World bukkitWorld) {
        net.minecraft.server.v1_16_R3.World world = (net.minecraft.server.v1_16_R3.World)bukkitWorld;
        switch (entityType) {
            case ZOMBIE:
                return new NMSRPGZombieEntityV1_16_5_R01(rpgManager, world).getBukkitEntity();
            default:
                throw new UnsupportedOperationException("This version of PixzaCore cannot spawn " + entityType);
        }
    }

    @Override
    public void spawnEntity(Entity entity, World bukkitWorld) {
        net.minecraft.server.v1_16_R3.World world = (net.minecraft.server.v1_16_R3.World)bukkitWorld;
        world.addEntity(((CraftEntity)entity).getHandle());
    }

}
