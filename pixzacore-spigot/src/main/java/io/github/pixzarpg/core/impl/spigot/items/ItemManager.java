package io.github.pixzarpg.core.impl.spigot.items;

import io.github.pixzarpg.core.impl.spigot.RPGManager;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ItemManager implements Listener {

    private final RPGManager rpgManager;

    private final Map<UUID, RPGItemType> data = new HashMap<>();


    public ItemManager(RPGManager rpgManager) {
        this.rpgManager = rpgManager;
        Bukkit.getServer().getPluginManager().registerEvents(this, this.rpgManager.getPlugin());
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
