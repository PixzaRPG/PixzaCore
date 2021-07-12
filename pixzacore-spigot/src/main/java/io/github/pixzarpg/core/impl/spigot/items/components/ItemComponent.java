package io.github.pixzarpg.core.impl.spigot.items.components;

import io.github.pixzarpg.core.datapacks.api.items.ItemComponentType;
import io.github.pixzarpg.core.impl.spigot.RPGManager;
import io.github.pixzarpg.core.impl.spigot.items.RPGItem;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public abstract class ItemComponent {

    private final RPGManager rpgManager;


    public ItemComponent(RPGManager rpgManager) {
        this.rpgManager = rpgManager;
    }

    public abstract ItemComponentType getType();

    public void onItemCreated(RPGItem item) {}

    public void onItemUsage(RPGItem item) {}

    /**
     * Called when the owner interacts (right click) with a entity
     * @param target The entity interacted with
     * @param item The item used
     */
    public void onItemEntityInteract(Entity target, RPGItem item) {}

    public void onItemAttack(Entity target, RPGItem item) {}

    /**
     * Whether or not the owner can drop the item or move it into another inventory
     * @return if the owner can
     */
    public boolean canLose() {
        return true;
    }

    public RPGManager getRPGManager() {
        return this.rpgManager;
    }

    protected boolean wasAppliedAlready(ItemStack itemStack) {
        return itemStack.getItemMeta().getPersistentDataContainer().has(this.generateKey("loaded"), PersistentDataType.BYTE);
    }

    protected NamespacedKey generateKey(String name) {
        return new NamespacedKey(this.getRPGManager().getPlugin(), this.getType().getId() + "_" + name);
    }

}
