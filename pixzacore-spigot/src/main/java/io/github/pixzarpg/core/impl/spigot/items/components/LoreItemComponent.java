package io.github.pixzarpg.core.impl.spigot.items.components;

import io.github.pixzarpg.core.datapacks.api.items.ItemComponentType;
import io.github.pixzarpg.core.impl.spigot.items.RPGItem;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class LoreItemComponent extends ItemComponent {

    private final List<String> lines;


    public LoreItemComponent(io.github.pixzarpg.core.datapacks.api.items.components.data.LoreItemComponent component) {
        this.lines = component.getLines();
    }

    @Override
    public ItemComponentType getType() {
        return ItemComponentType.LORE;
    }

    public List<String> getLines() {
        return this.lines;
    }

    @Override
    public void onItemCreated(RPGItem item) {
        ItemMeta meta = item.getItemStack().getItemMeta();
        meta.setLore(this.lines);
        item.getItemStack().setItemMeta(meta);
    }

}
