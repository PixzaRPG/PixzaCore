package io.github.pixzarpg.core.impl.world.regions;

import io.github.pixzarpg.core.api.world.regions.APIWorldRegion;
import io.github.pixzarpg.core.commons.Vector3;
import io.github.pixzarpg.core.impl.world.WorldManager;

import java.util.Set;

public class RegionManager {

    private final WorldManager worldManager;
    private final RegionStorage storage = new RegionStorage();


    public RegionManager(WorldManager worldManager) {
        this.worldManager = worldManager;
    }

    public Set<APIWorldRegion> getRegion(int x, int y, int z) {
        return this.getRegions(new Vector3(x, y, z));
    }

    public Set<APIWorldRegion> getRegions(Vector3 vector3) {
        return this.storage.getRegions(vector3);
    }

    public void registerRegion(WorldRegion region) {
        this.storage.register(region);
    }

}
