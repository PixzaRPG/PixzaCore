package io.github.pixzarpg.core.datapacks;

import com.google.gson.JsonObject;

public interface DataPackFileParser<T> {

    T parse(JsonObject data);

}
