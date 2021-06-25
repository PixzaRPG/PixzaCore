package io.github.pixzarpg.core.datapacks.versions.v1.parsers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.github.pixzarpg.core.commons.Vector3;
import io.github.pixzarpg.core.datapacks.DataPackFileParser;
import io.github.pixzarpg.core.datapacks.api.DataPackRegionObject;
import io.github.pixzarpg.core.commons.Vector3f;

import java.util.UUID;

public class V1DataPackRegionFileParser implements DataPackFileParser<DataPackRegionObject> {

    public static final DataPackFileParser<DataPackRegionObject> INSTANCE = new V1DataPackRegionFileParser();


    @Override
    public DataPackRegionObject parse(JsonObject data) {

        // Flags
        JsonArray flagsJSON = data.get("flags").getAsJsonArray();
        DataPackRegionObject.Flag[] flags = new DataPackRegionObject.Flag[flagsJSON.size()];
        for (int i = 0; i < flags.length; i++) {
            JsonObject flagJSON = flagsJSON.get(i).getAsJsonObject();
            flags[i] = new DataPackRegionObject.Flag(flagJSON.get("type").getAsString(), flagJSON.get("data").getAsJsonObject());
        }

        // Subregions
        JsonArray subRegionsJSON = data.get("subregions").getAsJsonArray();
        DataPackRegionObject[] subRegions = new DataPackRegionObject[subRegionsJSON.size()];
        for (int i = 0; i < subRegions.length; i++) {
            subRegions[i] = this.parse(subRegionsJSON.get(i).getAsJsonObject());
        }

        // Boundaries
        JsonObject boundaryAObject = data.get("boundaries").getAsJsonArray().get(0).getAsJsonObject();
        Vector3 vectorAObject = new Vector3(
                boundaryAObject.get("x").getAsInt(),
                boundaryAObject.get("y").getAsInt(),
                boundaryAObject.get("z").getAsInt()
        );

        JsonObject boundaryBObject = data.get("boundaries").getAsJsonArray().get(1).getAsJsonObject();
        Vector3 vectorBObject = new Vector3(
                boundaryBObject.get("x").getAsInt(),
                boundaryBObject.get("y").getAsInt(),
                boundaryBObject.get("z").getAsInt()
        );

        return new DataPackRegionObject.Builder()
                .setUuid(UUID.fromString(data.get("uuid").getAsString()))
                .setDescription(data.get("description").getAsString())
                .setBoundaryA(vectorAObject)
                .setBoundaryB(vectorBObject)
                .setFlags(flags)
                .setSubRegions(subRegions)
                .build();
    }

}
