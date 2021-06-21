package io.github.pixzarpg.core.impl.datapacks;

import com.google.gson.JsonObject;
import io.github.pixzarpg.core.datapacks.DataPackFileParserRegistry;
import io.github.pixzarpg.core.datapacks.providers.DataPackProvider;

import java.io.IOException;
import java.util.logging.Level;

public class DataPack {

    private final DataPackManager manager;
    private final DataPackProvider provider;


    public DataPack(DataPackManager manager, DataPackProvider provider) {
        this.manager = manager;
        this.provider = provider;

        int dataPackVersion;
        try {
            JsonObject manifestObject = provider.getFile("manifest.json");
            dataPackVersion = manifestObject.get("version").getAsInt();
        } catch (IOException exception) {
            this.manager.getRPGManager().getPlugin().getLogger().log(Level.SEVERE, "Failed to parse data pack manifest.json", exception);
            return;
        }

        DataPackFileParserRegistry dataPackParserRegistry = DataPackFileParserRegistry.getRegistry(dataPackVersion);
    }

}
