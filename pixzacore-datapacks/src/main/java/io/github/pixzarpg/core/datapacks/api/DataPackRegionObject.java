package io.github.pixzarpg.core.datapacks.api;

import com.google.gson.JsonObject;
import io.github.pixzarpg.core.commons.Vector3;

import java.util.UUID;

public class DataPackRegionObject {

    private UUID uuid;
    private String description;

    private Vector3 boundaryA;
    private Vector3 boundaryB;

    private Flag[] flags = new Flag[0];

    private DataPackRegionObject[] subRegions = new DataPackRegionObject[0];


    public UUID getUuid() {
        return this.uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Vector3 getBoundaryA() {
        return this.boundaryA;
    }

    public void setBoundaryA(Vector3 boundaryA) {
        this.boundaryA = boundaryA;
    }

    public Vector3 getBoundaryB() {
        return this.boundaryB;
    }

    public void setBoundaryB(Vector3 boundaryB) {
        this.boundaryB = boundaryB;
    }

    public Flag[] getFlags() {
        return this.flags;
    }

    public void setFlags(Flag[] flags) {
        this.flags = flags;
    }

    public DataPackRegionObject[] getSubRegions() {
        return this.subRegions;
    }

    public void setSubRegions(DataPackRegionObject[] subRegions) {
        this.subRegions = subRegions;
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

}
