package io.github.pixzarpg.core.impl.datapacks;

import io.github.pixzarpg.core.impl.RPGManager;
import io.github.pixzarpg.core.datapacks.providers.FolderDataPackProvider;
import io.github.pixzarpg.core.impl.utils.TextUtils;

import java.io.File;
import java.util.Arrays;

public class DataPackManager {

    private final static String LOG_PREFIX = "DataPacks";

    private final RPGManager manager;
    private DataPack[] dataPacks = new DataPack[0];


    public DataPackManager(RPGManager manager) {
        this.manager = manager;
    }

    public RPGManager getRPGManager() {
        return this.manager;
    }

    public void load() {
        this.manager.getPlugin().getLogger()
                .info(TextUtils.generateLoggerMessage(LOG_PREFIX, "Loading data packs"));

        File dataPacksFolder = new File(this.manager.getPlugin().getDataFolder().getAbsolutePath() + "/packs");
        dataPacksFolder.mkdirs();

        this.dataPacks = Arrays.stream(dataPacksFolder.listFiles())
                .filter(File::isDirectory)
                .map(FolderDataPackProvider::new)
                .map(provider -> new DataPack(this, provider))
                .toArray(DataPack[]::new);

        this.manager.getPlugin().getLogger()
                .info(TextUtils.generateLoggerMessage(LOG_PREFIX, "Finished loading data packs"));
    }

}
