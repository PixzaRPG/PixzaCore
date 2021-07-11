package io.github.pixzarpg.core.impl.spigot.world.regions;

import io.github.pixzarpg.core.impl.spigot.world.regions.flags.RegionFlag;
import io.github.pixzarpg.core.commons.Boundaries;
import io.github.pixzarpg.core.commons.Vector3;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;

/**
 * An RPG area that has special modifiers
 */
public class WorldRegion {

    private final UUID uuid;
    private final String description;

    private final Boundaries boundaries;

    private final Set<RegionFlag> flags;
    private final Set<WorldRegion> subRegions;


    private WorldRegion(
            UUID uuid,
            String description,
            Boundaries boundaries,
            Set<RegionFlag> flags,
            Set<WorldRegion> subRegions
    ) {
        this.uuid = uuid;
        this.description = description;
        this.boundaries = boundaries;
        this.flags = flags;
        this.subRegions = subRegions;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public String getDescription() {
        return this.description;
    }

    public Boundaries getBoundaries() {
        return this.boundaries;
    }

    public Set<RegionFlag> getFlags() {
        return this.flags;
    }

    public Set<WorldRegion> getSubRegions() {
        return this.subRegions;
    }


    public static class Builder {

        private UUID uuid;
        private String description = "";

        private Boundaries boundaries;

        private Set<RegionFlag> flags = Collections.emptySet();
        private Set<WorldRegion> subRegions = Collections.emptySet();


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

        public Builder setFlags(Set<RegionFlag> flags) {
            this.flags = flags;
            return this;
        }

        public Builder setSubRegions(Set<WorldRegion> subRegions) {
            this.subRegions = subRegions;
            return this;
        }

        public WorldRegion build() {
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
