package io.github.pixzarpg.core.impl.spigot.datapacks;

import com.google.gson.JsonParseException;
import io.github.pixzarpg.core.impl.spigot.RPGManager;
import io.github.pixzarpg.core.datapacks.providers.FolderDataPackProvider;
import io.github.pixzarpg.core.impl.spigot.utils.TextUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;

public class DataPackManager {

    private final static String LOG_PREFIX = "DataPacks";

    private final RPGManager manager;
    private final Map<UUID, DataPack> dataPacks = new HashMap<>();

    private DataPackRegistry registry;


    public DataPackManager(RPGManager manager) {
        this.manager = manager;
    }

    public RPGManager getRPGManager() {
        return this.manager;
    }

    public void load() {
        this.manager.getPlugin().getLogger()
                .info(TextUtils.generateLoggerMessage(LOG_PREFIX, "Loading data packs"));

        this.loadDataPacks();
        this.registry = new DataPackRegistry(this.dataPacks);

        this.registry.registerAll((dataPack, exception) -> this.manager
                .getPlugin()
                .getLogger()
                .log(Level.SEVERE, TextUtils.generateLoggerMessage(LOG_PREFIX, "Failed to load "+ dataPack.getManifest().getUuid()), exception));

        this.manager.getPlugin().getLogger()
                .info(TextUtils.generateLoggerMessage(LOG_PREFIX, "Finished loading data packs"));
    }

    public void unload() {
        this.registry.unregisterAll();
    }

    private void loadDataPacks() {
        File dataPacksFolder = new File(this.manager.getPlugin().getDataFolder().getAbsolutePath() + "/packs");
        dataPacksFolder.mkdirs();

        // Load datapacks
        Arrays.stream(dataPacksFolder.listFiles())
                .filter(File::isDirectory)
                .map(FolderDataPackProvider::new)
                .forEach(provider -> {
                    try {
                        DataPack dataPack = new DataPack(this, provider);
                        this.dataPacks.put(dataPack.getManifest().getUuid(), dataPack);
                    } catch (JsonParseException exception) {
                        this.manager.getPlugin().getLogger().log(Level.SEVERE, "Failed to parse JSON of data pack " + provider.getDataPackName());
                    } catch (IOException exception) {
                        this.manager.getPlugin().getLogger().log(Level.SEVERE, "Failed to load data pack " + provider.getDataPackName(), exception);
                    }
                });
    }

}
