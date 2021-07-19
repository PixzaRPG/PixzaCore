package io.github.pixzarpg.core.impl.spigot.items;

import io.github.pixzarpg.core.impl.spigot.items.components.ItemComponent;
import org.bukkit.Material;

import java.util.Set;
import java.util.UUID;

public class RPGItemType {

    private final UUID uuid;
    private final Material material;
    private final Set<ItemComponent> components;

    private final ItemManager itemManager;


    public RPGItemType(ItemManager manager, UUID uuid, Material material, Set<ItemComponent> components) {
        this.uuid = uuid;
        this.material = material;
        this.components = components;
        this.itemManager = manager;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public Material getMaterial() {
        return this.material;
    }

    public Set<ItemComponent> getComponents() {
        return this.components;
    }

    public ItemManager getItemManager() {
        return this.itemManager;
    }

    public RPGItem create() {
        return new RPGItem(this);
    }

}
