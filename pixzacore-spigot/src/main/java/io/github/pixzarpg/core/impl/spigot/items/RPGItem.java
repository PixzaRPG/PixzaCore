package io.github.pixzarpg.core.impl.spigot.items;

import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

public class RPGItem {

    private final ItemStack itemStack;
    private final LivingEntity owner;


    public RPGItem(LivingEntity owner, ItemStack itemStack) {
        this.owner = owner;
        this.itemStack = itemStack;
    }

    public LivingEntity getOwner() {
        return this.owner;
    }

    public ItemStack getItemStack() {
        return this.itemStack;
    }

}
