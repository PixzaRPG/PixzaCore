package io.github.pixzarpg.core.impl.spigot.events.world.regions;

import io.github.pixzarpg.core.api.world.regions.APIWorldRegion;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerEvent;

public abstract class WorldRegionEvent extends PlayerEvent {

    protected final APIWorldRegion region;

    public WorldRegionEvent(Player who, APIWorldRegion worldRegion) {
        super(who);
        this.region = worldRegion;
    }

    public APIWorldRegion getRegion() {
        return this.region;
    }

}
