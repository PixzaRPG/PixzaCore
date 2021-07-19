package io.github.pixzarpg.core.impl.spigot.events.players.items;

import io.github.pixzarpg.core.impl.spigot.entities.RPGPlayer;
import io.github.pixzarpg.core.impl.spigot.events.players.RPGPlayerEvent;
import io.github.pixzarpg.core.impl.spigot.items.RPGItem;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public class RPGPlayerItemInteractEntityEvent extends RPGPlayerEvent implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();

    private final RPGItem rpgItem;
    private final Entity entity;
    private boolean cancelled;

    public RPGPlayerItemInteractEntityEvent(RPGPlayer who, RPGItem rpgItem, Entity entity) {
        super(who);
        this.rpgItem = rpgItem;
        this.entity = entity;
    }

    public RPGItem getRPGItem() {
        return this.rpgItem;
    }

    public Entity getEntity() {
        return this.entity;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

}
