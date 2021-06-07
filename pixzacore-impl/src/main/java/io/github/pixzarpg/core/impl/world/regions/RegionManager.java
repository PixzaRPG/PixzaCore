package io.github.pixzarpg.core.impl.world.regions;

import io.github.pixzarpg.core.api.world.APIWorldManager;
import io.github.pixzarpg.core.api.world.regions.APIRegionManager;
import io.github.pixzarpg.core.api.world.regions.APIWorldRegion;

public class RegionManager implements APIRegionManager {

    private final APIWorldManager worldManager;


    public RegionManager(APIWorldManager worldManager) {
        this.worldManager = worldManager;
    }

    @Override
    public APIWorldRegion getRegion(int x, int y, int z) {
        return null;
    }

    @Override
    public void registerRegion(APIWorldRegion region) {

    }

}
