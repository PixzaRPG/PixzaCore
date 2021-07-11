package io.github.pixzarpg.core.impl.spigot.world.regions;

import io.github.pixzarpg.core.api.world.regions.APIWorldRegion;
import io.github.pixzarpg.core.commons.Vector3;
import io.github.pixzarpg.core.impl.spigot.events.world.regions.EnterWorldRegionEvent;
import io.github.pixzarpg.core.impl.spigot.events.world.regions.LeaveWorldRegionEvent;
import io.github.pixzarpg.core.impl.spigot.world.WorldManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.*;
import java.util.stream.Collectors;

public class RegionManager implements Listener {

    private final WorldManager worldManager;
    private final RegionStorage storage = new RegionStorage();

    private final Map<UUID, Set<APIWorldRegion>> regions = new HashMap<>();


    public RegionManager(WorldManager worldManager) {
        this.worldManager = worldManager;
        Bukkit.getServer().getPluginManager().registerEvents(this, worldManager.getRPGManager().getPlugin());
    }

    public Set<APIWorldRegion> getRegions(int x, int y, int z) {
        return this.getRegions(new Vector3(x, y, z));
    }

    public Set<APIWorldRegion> getRegions(Location location) {
        return this.getRegions(new Vector3(location.getBlockX(), location.getBlockY(), location.getBlockZ()));
    }

    public Set<APIWorldRegion> getRegions(Vector3 vector3) {
        return this.storage.getRegions(vector3);
    }

    public void registerRegion(APIWorldRegion region) {
        this.storage.register(region);
    }

    public void unregisterRegion(APIWorldRegion region) {
        this.storage.unregister(region);
    }

    @EventHandler
    public void currentRegionCheck(PlayerMoveEvent event) {
        Set<APIWorldRegion> currentRegions = this.regions.getOrDefault(event.getPlayer().getUniqueId(), new HashSet<>());
        Set<APIWorldRegion> futureRegions = this.getRegions(event.getTo());

        // What regions did we leave?
        for (APIWorldRegion currentRegion : currentRegions) {
            if (!futureRegions.contains(currentRegion)) {
                // Player left the region
                LeaveWorldRegionEvent leaveWorldRegionEvent = new LeaveWorldRegionEvent(event.getPlayer(), currentRegion);
                Bukkit.getPluginManager().callEvent(leaveWorldRegionEvent);
                if (leaveWorldRegionEvent.isCancelled()) {
                    event.setCancelled(true);
                    return;
                }
            }
        }

        // What regions did we enter?
        Set<APIWorldRegion> newRegions = futureRegions
                .stream()
                .filter(region -> !currentRegions.contains(region))
                .collect(Collectors.toSet());
        for (APIWorldRegion newRegion : newRegions) {
            EnterWorldRegionEvent enterWorldRegionEvent = new EnterWorldRegionEvent(event.getPlayer(), newRegion);
            Bukkit.getPluginManager().callEvent(enterWorldRegionEvent);
            if (enterWorldRegionEvent.isCancelled()) {
                event.setCancelled(true);
                return;
            }
        }

        // We can move into this region. Cache it
        this.regions.put(event.getPlayer().getUniqueId(), futureRegions);
    }

}
