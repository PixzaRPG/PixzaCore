package io.github.pixzarpg.core.datapacks.api;

import com.google.gson.JsonObject;
import io.github.pixzarpg.core.commons.Vector3;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;

public class DataPackRegionObject {

    private final UUID uuid;
    private final String description;

    private final Vector3 boundaryA;
    private final Vector3 boundaryB;

    private final Set<Flag> flags;

    private final Set<DataPackRegionObject> subRegions;


    protected DataPackRegionObject(
        UUID uuid,
        String description,
        Vector3 boundaryA,
        Vector3 boundaryB,
        Set<Flag> flags,
        Set<DataPackRegionObject> subRegions
    ) {
        this.uuid = uuid;
        this.description = description;
        this.boundaryA = boundaryA;
        this.boundaryB = boundaryB;
        this.flags = flags;
        this.subRegions = subRegions;
    }


    public UUID getUuid() {
        return this.uuid;
    }

    public String getDescription() {
        return this.description;
    }

    public Vector3 getBoundaryA() {
        return this.boundaryA;
    }

    public Vector3 getBoundaryB() {
        return this.boundaryB;
    }

    public Set<Flag> getFlags() {
        return this.flags;
    }

    public Set<DataPackRegionObject> getSubRegions() {
        return this.subRegions;
    }


    public static class Flag {

        private final String type;
        private final JsonObject data;


        public Flag(String type, JsonObject data) {
            this.type = type;
            this.data = data;
        }

        public String getType() {
            return this.type;
        }

        public JsonObject getData() {
            return this.data;
        }

    }

    public static class Builder {

        private UUID uuid;
        private String description;

        private Vector3 boundaryA;
        private Vector3 boundaryB;

        private Set<Flag> flags = Collections.emptySet();

        private Set<DataPackRegionObject> subRegions = Collections.emptySet();


        public Builder setUuid(UUID uuid) {
            this.uuid = uuid;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setBoundaryA(Vector3 boundaryA) {
            this.boundaryA = boundaryA;
            return this;
        }

        public Builder setBoundaryB(Vector3 boundaryB) {
            this.boundaryB = boundaryB;
            return this;
        }

        public Builder setFlags(Set<Flag> flags) {
            this.flags = flags;
            return this;
        }

        public Builder setSubRegions(Set<DataPackRegionObject> subRegions) {
            this.subRegions = subRegions;
            return this;
        }

        public DataPackRegionObject build() {
            return new DataPackRegionObject(
                    this.uuid,
                    this.description,
                    this.boundaryA,
                    this.boundaryB,
                    this.flags,
                    this.subRegions
            );
        }

    }

}
