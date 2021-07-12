package io.github.pixzarpg.core.datapacks.api.items.components.data;

import io.github.pixzarpg.core.datapacks.api.items.ItemComponentType;

import java.util.List;

public class LoreItemComponent implements ItemComponent {

    private final List<String> lines;

    public LoreItemComponent(List<String> lines) {
        this.lines = lines;
    }

    @Override
    public ItemComponentType getType() {
        return ItemComponentType.LORE;
    }

    public List<String> getLines() {
        return this.lines;
    }

}
