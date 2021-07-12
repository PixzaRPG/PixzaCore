package io.github.pixzarpg.core.datapacks.versions.v1.parsers.items;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.github.pixzarpg.core.datapacks.DataPackFileParser;
import io.github.pixzarpg.core.datapacks.api.items.DataPackItemObject;
import io.github.pixzarpg.core.datapacks.api.items.ItemComponentType;
import io.github.pixzarpg.core.datapacks.api.items.components.ComponentParser;
import io.github.pixzarpg.core.datapacks.api.items.components.data.ItemComponent;
import io.github.pixzarpg.core.datapacks.versions.v1.parsers.items.components.V1ComponentParserResolver;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class V1DataPackItemFileParser implements DataPackFileParser<DataPackItemObject> {

    public static final DataPackFileParser<DataPackItemObject> INSTANCE = new V1DataPackItemFileParser();


    @Override
    public DataPackItemObject parse(JsonObject data) {
        Set<ItemComponent> components = new HashSet<>();
        JsonArray jsonComponentsArray = data.getAsJsonArray("components");
        for (JsonElement element : jsonComponentsArray) {
            JsonObject object = element.getAsJsonObject();

            ItemComponentType type = ItemComponentType.getById(object.get("type").getAsString());
            if (type == null) {
                throw new NullPointerException("Invalid component type: " + object.get("type").getAsString());
            }
            ComponentParser<? extends ItemComponent> parser = V1ComponentParserResolver.resolve(type);
            if (parser == null) {
                throw new NullPointerException("Missing component parser for: " + type.getId());
            }
            components.add(parser.parse(object.getAsJsonObject("data")));
        }

        return new DataPackItemObject(
                UUID.fromString(data.get("uuid").getAsString()),
                data.get("item").getAsString(),
                components
        );
    }

}
