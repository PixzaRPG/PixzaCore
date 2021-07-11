package io.github.pixzarpg.core.impl.spigot.events.world.regions;

import io.github.pixzarpg.core.impl.spigot.world.regions.WorldRegion;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerEvent;

public abstract class WorldRegionEvent extends PlayerEvent {

    protected final WorldRegion region;

    public WorldRegionEvent(Player who, WorldRegion worldRegion) {
        super(who);
        this.region = worldRegion;
    }

    public WorldRegion getRegion() {
        return this.region;
    }

}
