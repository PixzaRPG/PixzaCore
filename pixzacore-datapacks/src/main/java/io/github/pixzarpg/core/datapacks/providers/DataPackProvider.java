package io.github.pixzarpg.core.datapacks.providers;

import com.google.gson.JsonObject;

import java.io.IOException;

public interface DataPackProvider {

    JsonObject getFile(String path) throws IOException;

    String[] getFiles(String rootDirPath, boolean recursive) throws IOException;

}
