package io.github.pixzarpg.core.datapacks;

import io.github.pixzarpg.core.datapacks.api.DataPackItemObject;
import io.github.pixzarpg.core.datapacks.api.DataPackManifestObject;
import io.github.pixzarpg.core.datapacks.api.DataPackRegionObject;
import io.github.pixzarpg.core.datapacks.versions.v1.V1DataPackFileParserRegistry;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class DataPackFileParserRegistry {

    private static final Map<Integer, DataPackFileParserRegistry> REGISTRY = Collections.unmodifiableMap(new HashMap<Integer, DataPackFileParserRegistry>(){
        {
            put(1, new V1DataPackFileParserRegistry());
        }
    });


    public abstract DataPackFileParser<DataPackManifestObject> getManifestParser();
    public abstract DataPackFileParser<DataPackRegionObject> getRegionParser();
    public abstract DataPackFileParser<DataPackItemObject> getItemParser();

    public static DataPackFileParserRegistry getRegistry(int version) {
        return REGISTRY.getOrDefault(version, null);
    }

}
