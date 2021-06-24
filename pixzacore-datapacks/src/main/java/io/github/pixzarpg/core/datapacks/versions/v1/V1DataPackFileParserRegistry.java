package io.github.pixzarpg.core.datapacks.versions.v1;

import io.github.pixzarpg.core.datapacks.api.DataPackManifestObject;
import io.github.pixzarpg.core.datapacks.DataPackFileParser;
import io.github.pixzarpg.core.datapacks.DataPackFileParserRegistry;
import io.github.pixzarpg.core.datapacks.api.DataPackRegionObject;
import io.github.pixzarpg.core.datapacks.versions.v1.parsers.V1DataPackManifestFileParser;
import io.github.pixzarpg.core.datapacks.versions.v1.parsers.V1DataPackRegionFileParser;

public class V1DataPackFileParserRegistry extends DataPackFileParserRegistry {

    @Override
    public DataPackFileParser<DataPackManifestObject> getManifestParser() {
        return V1DataPackManifestFileParser.INSTANCE;
    }

    @Override
    public DataPackFileParser<DataPackRegionObject> getRegionParser() {
        return V1DataPackRegionFileParser.INSTANCE;
    }

}
