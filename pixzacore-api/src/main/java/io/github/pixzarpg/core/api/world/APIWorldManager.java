package io.github.pixzarpg.core.api.world;

import io.github.pixzarpg.core.api.world.regions.APIRegionManager;

public interface APIWorldManager {

    APIRegionManager getRegionManager();

    /**
     * Shutdown/save any in-use world resources as needed
     */
    void close();

}
