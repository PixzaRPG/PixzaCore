package io.github.pixzarpg.core.impl.spigot.items.components;

import io.github.pixzarpg.core.impl.spigot.RPGManager;

public class ItemComponentResolver {

    /**
     * Convert a datapack item component into a usable RPG component
     * @param rpgManager RPGManager
     * @param component datapack item component
     * @return RPG component
     */
    public static ItemComponent resolve(RPGManager rpgManager, io.github.pixzarpg.core.datapacks.api.items.components.data.ItemComponent component) {
        switch (component.getType()) {
            case LORE:
                return new LoreItemComponent(rpgManager, (io.github.pixzarpg.core.datapacks.api.items.components.data.LoreItemComponent)component);
            default:
                return null;
        }
    }

}
