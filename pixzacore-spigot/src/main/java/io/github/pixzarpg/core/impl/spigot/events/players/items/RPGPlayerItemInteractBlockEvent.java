package io.github.pixzarpg.core.impl.spigot.events.players.items;

import io.github.pixzarpg.core.impl.spigot.items.RPGItem;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

/**
 * Called when a player interacts with a block using an RPGItem
 */
public class RPGPlayerItemInteractBlockEvent extends PlayerEvent implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();

    private final RPGItem rpgItem;
    private final Block block;
    private boolean cancelled;

    public RPGPlayerItemInteractBlockEvent(Player who, RPGItem rpgItem, Block block) {
        super(who);
        this.rpgItem = rpgItem;
        this.block = block;
    }

    public RPGItem getRPGItem() {
        return this.rpgItem;
    }

    public Block getBlock() {
        return this.block;
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
