package io.github.pixzarpg.core.api.world;

import io.github.pixzarpg.core.api.world.regions.APIRegionManager;

public interface APIWorldManager<W> {

    APIRegionManager getRegionManager();

    W getWorld();

    /**
     * Shutdown/save any in-use world resources as needed
     */
    void close();

}
