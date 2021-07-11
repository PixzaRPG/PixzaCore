package io.github.pixzarpg.core.api.world.regions;

import io.github.pixzarpg.core.api.world.regions.flags.APIRegionFlag;
import io.github.pixzarpg.core.commons.Boundaries;

import java.util.Set;
import java.util.UUID;

public interface APIWorldRegion {

    UUID getUuid();
    String getDescription();

    Boundaries getBoundaries();

    Set<APIRegionFlag> getFlags();
    Set<APIWorldRegion> getSubRegions();

}
