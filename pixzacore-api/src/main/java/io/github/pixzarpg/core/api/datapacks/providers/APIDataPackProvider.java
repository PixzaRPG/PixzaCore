package io.github.pixzarpg.core.api.datapacks.providers;

import com.google.gson.JsonObject;

import java.io.IOException;

/**
 * Responsible for providing access to files in a data pack
 */
public interface APIDataPackProvider {

    /**
     * Retrieve the JSON contents of a file
     * @param path absolute path to the file
     * @return the json object
     * @throws IOException
     */
    JsonObject getFile(String path) throws IOException;

    /**
     * Retrieve all non-directory files within a path
     * @param path absolute path to the directory
     * @param recursive If it should go through each folder and retrieve the file paths
     * @return absolute file paths
     * @throws IOException
     */
    String[] getFiles(String path, boolean recursive) throws IOException;

}
