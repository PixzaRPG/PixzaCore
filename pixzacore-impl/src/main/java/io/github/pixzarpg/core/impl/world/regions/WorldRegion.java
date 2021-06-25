package io.github.pixzarpg.core.impl.world.regions;

import io.github.pixzarpg.core.api.world.regions.APIWorldRegion;
import io.github.pixzarpg.core.api.world.regions.flags.APIRegionFlag;
import io.github.pixzarpg.core.commons.Boundaries;
import io.github.pixzarpg.core.commons.Vector3;

import java.util.UUID;

/**
 * An RPG area that has special modifiers
 */
public class WorldRegion implements APIWorldRegion {

    private final UUID uuid;
    private final String description;

    private final Boundaries boundaries;

    private final APIRegionFlag[] flags;
    private final APIWorldRegion[] subRegions;


    private WorldRegion(
            UUID uuid,
            String description,
            Boundaries boundaries,
            APIRegionFlag[] flags,
            APIWorldRegion[] subRegions
    ) {
        this.uuid = uuid;
        this.description = description;
        this.boundaries = boundaries;
        this.flags = flags;
        this.subRegions = subRegions;
    }

    @Override
    public UUID getUuid() {
        return this.uuid;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public Boundaries getBoundaries() {
        return this.boundaries;
    }

    @Override
    public APIRegionFlag[] getFlags() {
        return this.flags;
    }

    @Override
    public APIWorldRegion[] getSubRegions() {
        return this.subRegions;
    }


    public static class Builder {

        private UUID uuid;
        private String description = "";

        private Boundaries boundaries;

        private APIRegionFlag[] flags = new APIRegionFlag[0];
        private APIWorldRegion[] subRegions = new APIWorldRegion[0];


        public Builder setUuid(UUID uuid) {
            this.uuid = uuid;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setBoundaries(Vector3 boundaryA, Vector3 boundaryB) {
            this.boundaries = new Boundaries(boundaryA, boundaryB);
            return this;
        }

        public Builder setFlags(APIRegionFlag[] flags) {
            this.flags = flags;
            return this;
        }

        public Builder setSubRegions(APIWorldRegion[] subRegions) {
            this.subRegions = subRegions;
            return this;
        }

        public APIWorldRegion build() {
            return new WorldRegion(
                    this.uuid,
                    this.description,
                    this.boundaries,
                    this.flags,
                    this.subRegions
            );
        }

    }

}
