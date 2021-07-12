package io.github.pixzarpg.core.impl.spigot.items;

import io.github.pixzarpg.core.impl.spigot.RPGManager;
import io.github.pixzarpg.core.impl.spigot.items.components.ItemComponent;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import java.util.Set;
import java.util.UUID;

public class RPGItemType {

    private final UUID uuid;
    private final Material material;
    private final Set<ItemComponent> components;

    private final RPGManager rpgManager;


    public RPGItemType(RPGManager manager, UUID uuid, Material material, Set<ItemComponent> components) {
        this.uuid = uuid;
        this.material = material;
        this.components = components;
        this.rpgManager = manager;
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

    public RPGManager getRPGManager() {
        return this.rpgManager;
    }

    public RPGItem constructFor(LivingEntity entity) {
        ItemStack itemStack = new ItemStack(this.material);
        RPGItem item = new RPGItem(entity, itemStack);
        return item;
    }

}
