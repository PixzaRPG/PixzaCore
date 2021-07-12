package io.github.pixzarpg.core.impl.spigot.nms.versions.v1_16_5_R01.entities;

import io.github.pixzarpg.core.impl.spigot.RPGManager;
import net.minecraft.server.v1_16_R3.EntityZombie;
import net.minecraft.server.v1_16_R3.World;
import org.bukkit.entity.LivingEntity;

public class NMSRPGZombieEntityV1_16_5_R01 extends EntityZombie {

    private final RPGManager rpgManager;


    public NMSRPGZombieEntityV1_16_5_R01(RPGManager rpgManager, World world) {
        super(world);
        this.rpgManager = rpgManager;
    }

    @Override
    protected void bN() {
        super.bN();
        this.rpgManager.getEntityManager().remove((LivingEntity)this.getBukkitEntity());
    }

    @Override
    public void die() {
        super.die();
        this.rpgManager.getEntityManager().remove((LivingEntity)this.getBukkitEntity());
    }

}
