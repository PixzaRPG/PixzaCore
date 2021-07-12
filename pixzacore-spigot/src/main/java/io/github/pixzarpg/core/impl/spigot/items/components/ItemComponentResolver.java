package io.github.pixzarpg.core.impl.spigot.items.components;

public class ItemComponentResolver {

    /**
     * Convert a datapack item component into a usable RPG component
     * @param component datapack item component
     * @return RPG component
     */
    public static ItemComponent resolve(io.github.pixzarpg.core.datapacks.api.items.components.data.ItemComponent component) {
        switch (component.getType()) {
            case LORE:
                return new LoreItemComponent((io.github.pixzarpg.core.datapacks.api.items.components.data.LoreItemComponent)component);
            default:
                return null;
        }
    }

}
