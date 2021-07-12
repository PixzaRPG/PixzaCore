package io.github.pixzarpg.core.impl.spigot.items.components;

import io.github.pixzarpg.core.datapacks.api.items.ItemComponentType;
import io.github.pixzarpg.core.impl.spigot.items.RPGItem;
import org.bukkit.entity.Entity;

public abstract class ItemComponent {

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

}
