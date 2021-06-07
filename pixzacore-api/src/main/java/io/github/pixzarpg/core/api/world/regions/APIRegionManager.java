package io.github.pixzarpg.core.api.world.regions;

public interface APIRegionManager {

    APIWorldRegion getRegion(int x, int y, int z);

    void registerRegion(APIWorldRegion region);

}
