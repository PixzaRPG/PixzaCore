package io.github.pixzarpg.core.impl.spigot.items;

import io.github.pixzarpg.core.impl.spigot.items.components.ItemComponent;
import org.bukkit.inventory.ItemStack;

public class RPGItem {

    // The key stored in the persistent storage of the item that stores the item type uuid
    public static final String RPG_ITEM_TYPE_KEY = "RPG_ITEM_TYPE";

    private final ItemStack itemStack;
    private final RPGItemType itemType;


    /**
     * Called when creating a new RPGItem
     * @param itemType
     */
    public RPGItem(RPGItemType itemType) {
        this.itemType = itemType;
        this.itemStack = new ItemStack(itemType.getMaterial());

        for (ItemComponent component : itemType.getComponents()) {
            component.onItemCreated(this);
        }
    }

    /**
     * Called when loading a pre-existing RPGItem
     * @param itemType
     * @param itemStack
     */
    public RPGItem(RPGItemType itemType, ItemStack itemStack) {
        this.itemType = itemType;
        this.itemStack = itemStack;
    }

    public ItemStack getItemStack() {
        return this.itemStack;
    }

    public RPGItemType getItemType() {
        return this.itemType;
    }

}
