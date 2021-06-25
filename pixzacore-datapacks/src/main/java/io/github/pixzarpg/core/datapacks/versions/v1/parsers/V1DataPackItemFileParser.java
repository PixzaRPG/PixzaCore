package io.github.pixzarpg.core.datapacks.versions.v1.parsers;

import com.google.gson.JsonObject;
import io.github.pixzarpg.core.datapacks.DataPackFileParser;
import io.github.pixzarpg.core.datapacks.api.DataPackItemObject;

public class V1DataPackItemFileParser implements DataPackFileParser<DataPackItemObject> {

    public static final DataPackFileParser<DataPackItemObject> INSTANCE = new V1DataPackItemFileParser();


    @Override
    public DataPackItemObject parse(JsonObject data) {
        DataPackItemObject itemObject = new DataPackItemObject();

        return itemObject;
    }

}
