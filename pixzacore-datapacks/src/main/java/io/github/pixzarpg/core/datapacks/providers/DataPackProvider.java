package io.github.pixzarpg.core.datapacks.providers;

import com.google.gson.JsonObject;

import java.io.IOException;

public interface DataPackProvider {

    /**
     * Retrieve a name associated with the data pack
     * @return
     */
    String getDataPackName();

    JsonObject getFile(String path) throws IOException;

    String[] getFiles(String rootDirPath, boolean recursive) throws IOException;

}
