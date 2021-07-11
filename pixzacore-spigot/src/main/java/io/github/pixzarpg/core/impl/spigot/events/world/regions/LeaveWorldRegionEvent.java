package io.github.pixzarpg.core.impl.spigot.events.world.regions;

import io.github.pixzarpg.core.api.world.regions.APIWorldRegion;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

/**
 * Called when a player attempts to leave a region
 */
public class LeaveWorldRegionEvent extends WorldRegionEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private boolean cancelled;


    public LeaveWorldRegionEvent(Player who, APIWorldRegion worldRegion) {
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
