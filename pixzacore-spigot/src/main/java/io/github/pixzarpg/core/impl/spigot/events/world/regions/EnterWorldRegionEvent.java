package io.github.pixzarpg.core.impl.spigot.events.world.regions;

import io.github.pixzarpg.core.impl.spigot.world.regions.WorldRegion;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

/**
 * Called when a player attempts to move into a region
 */
public class EnterWorldRegionEvent extends WorldRegionEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private boolean cancelled;


    public EnterWorldRegionEvent(Player who, WorldRegion worldRegion) {
        super(who, worldRegion);
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

}
