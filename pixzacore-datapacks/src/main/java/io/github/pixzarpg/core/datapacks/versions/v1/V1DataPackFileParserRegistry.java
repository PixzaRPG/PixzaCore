package io.github.pixzarpg.core.datapacks.versions.v1;

import io.github.pixzarpg.core.datapacks.api.items.DataPackItemObject;
import io.github.pixzarpg.core.datapacks.api.DataPackManifestObject;
import io.github.pixzarpg.core.datapacks.DataPackFileParser;
import io.github.pixzarpg.core.datapacks.DataPackFileParserRegistry;
import io.github.pixzarpg.core.datapacks.api.regions.DataPackRegionObject;
import io.github.pixzarpg.core.datapacks.versions.v1.parsers.items.V1DataPackItemFileParser;
import io.github.pixzarpg.core.datapacks.versions.v1.parsers.V1DataPackManifestFileParser;
import io.github.pixzarpg.core.datapacks.versions.v1.parsers.regions.V1DataPackRegionFileParser;

public class V1DataPackFileParserRegistry extends DataPackFileParserRegistry {

    @Override
    public DataPackFileParser<DataPackManifestObject> getManifestParser() {
        return V1DataPackManifestFileParser.INSTANCE;
    }

    @Override
    public DataPackFileParser<DataPackRegionObject> getRegionParser() {
        return V1DataPackRegionFileParser.INSTANCE;
    }

    @Override
    public DataPackFileParser<DataPackItemObject> getItemParser() {
        return V1DataPackItemFileParser.INSTANCE;
    }

}
