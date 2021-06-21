package io.github.pixzarpg.core.datapacks;

import io.github.pixzarpg.core.datapacks.api.DataPackManifestFile;
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


    public abstract DataPackFileParser<DataPackManifestFile> getManifestParser();

    public static DataPackFileParserRegistry getRegistry(int version) {
        return REGISTRY.getOrDefault(version, null);
    }

}
