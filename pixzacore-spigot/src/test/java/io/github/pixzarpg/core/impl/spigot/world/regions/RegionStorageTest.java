package io.github.pixzarpg.core.impl.spigot.world.regions;

import io.github.pixzarpg.core.api.world.regions.APIWorldRegion;
import io.github.pixzarpg.core.commons.Vector3;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegionStorageTest {

    @Test
    public void shouldFetchRegionsIfRegionFound() {

        APIWorldRegion targetWorldRegion = new WorldRegion.Builder()
                .setUuid(UUID.randomUUID())
                .setBoundaries(new Vector3(0, 0, 0), new Vector3(10, 10, 10))
                .build();

        RegionStorage storage = new RegionStorage();
        storage.register(targetWorldRegion);

        Set<APIWorldRegion> regions = storage.getRegions(new Vector3(5, 5, 5));

        assertEquals(1, regions.size());
        assertEquals(targetWorldRegion, regions.stream().findAny().get());
    }

    @Test
    public void shouldNotFetchRegionsIfNoRegionFound() {
        RegionStorage storage = new RegionStorage();
        storage.register(
                new WorldRegion.Builder()
                    .setUuid(UUID.randomUUID())
                .setBoundaries(new Vector3(0, 0, 0), new Vector3(10, 10, 10))
                .build());

        assertEquals(0, storage.getRegions(new Vector3(11, 9, 11)).size());
    }

}
