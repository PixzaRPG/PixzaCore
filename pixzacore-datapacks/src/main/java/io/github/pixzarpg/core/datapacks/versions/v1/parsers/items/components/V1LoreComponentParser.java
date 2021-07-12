package io.github.pixzarpg.core.datapacks.versions.v1.parsers.items.components;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.github.pixzarpg.core.datapacks.api.items.components.data.LoreItemComponent;
import io.github.pixzarpg.core.datapacks.api.items.components.ComponentParser;

import java.util.ArrayList;
import java.util.List;

public class V1LoreComponentParser extends ComponentParser<LoreItemComponent> {

    public static final ComponentParser<LoreItemComponent> INSTANCE = new V1LoreComponentParser();


    @Override
    public LoreItemComponent parse(JsonObject data) {
        JsonArray linesJson = data.getAsJsonArray("lines");
        List<String> lines = new ArrayList<>(linesJson.size());
        for (JsonElement element : linesJson) {
            lines.add(element.getAsString());
        }
        return new LoreItemComponent(lines);
    }

}
