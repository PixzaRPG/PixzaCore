package io.github.pixzarpg.core.datapacks.api.items.components;

import com.google.gson.JsonObject;
import io.github.pixzarpg.core.datapacks.api.items.components.data.ItemComponent;

public abstract class ComponentParser<C extends ItemComponent> {

    protected ComponentParser() {}

    /**
     * Parses a json object retrieved from an item's component json file
     * @param data the json object
     * @return the component object representation of it
     */
    public abstract C parse(JsonObject data);

}
