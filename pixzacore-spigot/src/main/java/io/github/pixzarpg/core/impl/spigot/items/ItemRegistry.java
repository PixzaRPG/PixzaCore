package io.github.pixzarpg.core.impl.spigot.items;

import io.github.pixzarpg.core.impl.spigot.RPGManager;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ItemRegistry {

    private final RPGManager rpgManager;

    private final Map<UUID, RPGItemType> data = new HashMap<>();


    public ItemRegistry(RPGManager rpgManager) {
        this.rpgManager = rpgManager;
    }

    public RPGManager getRPGManager() {
        return this.rpgManager;
    }

    public void register(RPGItemType itemType) {
        this.data.put(itemType.getUuid(), itemType);
    }

    public RPGItemType getType(UUID itemUuid) {
        return this.data.get(itemUuid);
    }

}
