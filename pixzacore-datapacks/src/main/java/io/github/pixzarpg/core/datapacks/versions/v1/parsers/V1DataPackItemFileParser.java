package io.github.pixzarpg.core.datapacks.versions.v1.parsers;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.github.pixzarpg.core.datapacks.DataPackFileParser;
import io.github.pixzarpg.core.datapacks.api.DataPackItemObject;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class V1DataPackItemFileParser implements DataPackFileParser<DataPackItemObject> {

    public static final DataPackFileParser<DataPackItemObject> INSTANCE = new V1DataPackItemFileParser();


    @Override
    public DataPackItemObject parse(JsonObject data) {
        Set<DataPackItemObject.ItemComponent> components = new HashSet<>();
        JsonArray jsonComponentsArray = data.getAsJsonArray("components");
        for (JsonElement element : jsonComponentsArray) {
            JsonObject object = element.getAsJsonObject();
            components.add(new DataPackItemObject.ItemComponent(object.get("type").getAsString(), object.getAsJsonObject("data")));
        }

        return new DataPackItemObject(
                UUID.fromString(data.get("uuid").getAsString()),
                data.get("item").getAsString(),
                components
        );
    }

}
