package io.github.pixzarpg.core.impl.spigot.items;

import io.github.pixzarpg.core.impl.spigot.items.components.ItemComponent;
import org.bukkit.inventory.ItemStack;

public class RPGItem {

    private final ItemStack itemStack;
    private final RPGItemType itemType;


    public RPGItem(RPGItemType itemType, ItemStack itemStack) {
        this.itemType = itemType;
        this.itemStack = itemStack;

        for (ItemComponent component : itemType.getComponents()) {
            component.onItemCreated(this);
        }
    }

    public ItemStack getItemStack() {
        return this.itemStack;
    }

    public RPGItemType getItemType() {
        return this.itemType;
    }

}
