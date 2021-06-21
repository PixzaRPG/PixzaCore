package io.github.pixzarpg.core.datapacks.versions.v1;

import io.github.pixzarpg.core.datapacks.api.DataPackManifestFile;
import io.github.pixzarpg.core.datapacks.DataPackFileParser;
import io.github.pixzarpg.core.datapacks.DataPackFileParserRegistry;
import io.github.pixzarpg.core.datapacks.versions.v1.parsers.V1DataPackManifestFileParser;

public class V1DataPackFileParserRegistry extends DataPackFileParserRegistry {

    private static final DataPackFileParser<DataPackManifestFile> MANIFEST_PARSER = new V1DataPackManifestFileParser();


    @Override
    public DataPackFileParser<DataPackManifestFile> getManifestParser() {
        return MANIFEST_PARSER;
    }

}
