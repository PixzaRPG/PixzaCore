package io.github.pixzarpg.core.api.world.regions;

import io.github.pixzarpg.core.api.world.regions.flags.APIRegionFlag;
import io.github.pixzarpg.core.commons.Boundaries;

import java.util.UUID;

public interface APIWorldRegion {

    UUID getUuid();
    String getDescription();

    Boundaries getBoundaries();

    APIRegionFlag[] getFlags();
    APIWorldRegion[] getSubRegions();

}
