package io.github.pixzarpg.core.api.datapacks.providers;

import com.google.gson.JsonObject;

/**
 * Responsible for providing access to files in a data pack
 */
public interface APIDataPackProvider {

    JsonObject getFile(String path);

}
