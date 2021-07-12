package io.github.pixzarpg.core.datapacks.api.items;

import java.util.Arrays;

public enum ItemComponentType {

    LORE("lore");

    private final String id;

    ItemComponentType(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public static ItemComponentType getById(String id) {
        return Arrays.stream(ItemComponentType.values())
                .filter(type -> type.getId().equals(id))
                .findAny().orElse(null);
    }

}
