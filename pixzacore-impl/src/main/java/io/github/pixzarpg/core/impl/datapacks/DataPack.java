package io.github.pixzarpg.core.impl.datapacks;

import com.google.gson.JsonObject;
import io.github.pixzarpg.core.api.datapacks.APIDataPack;
import io.github.pixzarpg.core.datapacks.DataPackFileParserRegistry;
import io.github.pixzarpg.core.datapacks.api.DataPackManifestObject;
import io.github.pixzarpg.core.datapacks.providers.DataPackProvider;

import java.io.IOException;

public class DataPack implements APIDataPack {

    private final DataPackManager manager;
    private final DataPackProvider provider;

    private final DataPackManifestObject manifest;


    public DataPack(DataPackManager manager, DataPackProvider provider) throws IOException {
        this.manager = manager;
        this.provider = provider;

        // Determine the version of the parser to use
        int dataPackVersion;
        JsonObject manifestObject = provider.getFile("manifest.json");
        dataPackVersion = manifestObject.get("version").getAsInt();
        DataPackFileParserRegistry dataPackParserRegistry = DataPackFileParserRegistry.getRegistry(dataPackVersion);

        // Parse ONLY the manifest for the dependencies
        this.manifest = dataPackParserRegistry.getManifestParser().parse(provider.getFile("manifest.json"));
    }

    /**
     * Call this to read the contents of this data pack and register it to the server
     * @throws IOException
     */
    @Override
    public void register() throws IOException {

    }

    /**
     * Remove the contents of this data pack from the server
     */
    @Override
    public void unregister() {

    }

    public DataPackManifestObject getManifest() {
        return this.manifest;
    }

}
