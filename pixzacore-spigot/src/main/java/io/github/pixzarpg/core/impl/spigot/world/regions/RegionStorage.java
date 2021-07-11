package io.github.pixzarpg.core.impl.spigot.world.regions;

import io.github.pixzarpg.core.commons.Vector3;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class RegionStorage {

    // Every x blocks deserves it's own key in our region maps.
    private final static int KEY_INCREMENT = 100;

    /*
        In order to reduce the amount of iterations needed to fetch a region, we store a region for each x/z coordinate that is a multiple of KEY_INCREMENT.
        When fetching, we don't need to iterate through all of the regions and can instead calculate the x/z coordinate that is a multiple of KEY_INCREMENT.
     */
    private final Map<Integer, Set<WorldRegion>> xRegionMap = new HashMap<>();
    private final Map<Integer, Set<WorldRegion>> zRegionMap = new HashMap<>();

    public Set<WorldRegion> getRegions(Vector3 vector3) {
        int xGroupKey = vector3.getX() - (vector3.getX() % KEY_INCREMENT);
        int zGroupKey = vector3.getZ() - (vector3.getZ() % KEY_INCREMENT);
        if (this.xRegionMap.containsKey(xGroupKey) && this.zRegionMap.containsKey(zGroupKey)) {
            return this.xRegionMap.get(xGroupKey)
                    .stream()
                    .filter(region -> this.zRegionMap.get(zGroupKey).contains(region))
                    .filter(region -> region.getBoundaries().isWithinBoundaries(vector3))
                    .collect(Collectors.toSet());
        }
        return Collections.emptySet();
    }

    public void register(WorldRegion region) {
        this.forEachRegionKeyInRegion(
                region,
                currentList -> {
                    Set<WorldRegion> regions = currentList != null ? currentList : new HashSet<>();
                    regions.add(region);
                    return regions;
                }
        );
    }

    public void unregister(WorldRegion region) {
        this.forEachRegionKeyInRegion(
                region,
                currentList -> {
                    if (currentList != null) {
                        currentList.remove(region);
                        return currentList;
                    }
                    return null;
                }
        );
    }

    private void forEachRegionKeyInRegion(WorldRegion region, Function<Set<WorldRegion>, Set<WorldRegion>> regionKeyHandler) {
        for (int xKey = getKey(region.getBoundaries().getMinBoundary().getX()); xKey <= getKey(region.getBoundaries().getMaxBoundary().getX()); xKey++) {
            this.xRegionMap.compute(xKey, (key, currentList) -> regionKeyHandler.apply(currentList));
        }
        for (int zKey = getKey(region.getBoundaries().getMinBoundary().getZ()); zKey <= getKey(region.getBoundaries().getMaxBoundary().getZ()); zKey++) {
            this.zRegionMap.compute(zKey, (key, currentList) -> regionKeyHandler.apply(currentList));
        }
    }

    /**
     * Convert a coordinate to a multiple of KEY_INCREMENT.
     * @param value
     * @return
     */
    private static int getKey(int value) {
        return value - (value % KEY_INCREMENT);
    }


}
