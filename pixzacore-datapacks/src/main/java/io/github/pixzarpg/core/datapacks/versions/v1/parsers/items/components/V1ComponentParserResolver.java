package io.github.pixzarpg.core.datapacks.versions.v1.parsers.items.components;

import io.github.pixzarpg.core.datapacks.api.items.ItemComponentType;
import io.github.pixzarpg.core.datapacks.api.items.components.ComponentParser;
import io.github.pixzarpg.core.datapacks.api.items.components.data.ItemComponent;

public class V1ComponentParserResolver {

    public static ComponentParser<? extends ItemComponent> resolve(ItemComponentType type) {
        switch (type) {
            case LORE:
                return V1LoreComponentParser.INSTANCE;
            default:
                return null;
        }
    }

}
