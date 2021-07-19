package io.github.pixzarpg.core.impl.spigot.entities;

import org.bukkit.entity.LivingEntity;

public class RPGEntity {

    LivingEntity entity;

    public RPGEntity(LivingEntity entity) {
        this.entity = entity;
    }

    // TODO: This should accept a datapack entity object or something
    public RPGEntity() {
//it's too easy
    }

    public LivingEntity getEntity() {
        return this.entity;
    }

}
