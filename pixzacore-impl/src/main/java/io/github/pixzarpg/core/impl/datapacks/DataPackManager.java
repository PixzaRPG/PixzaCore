package io.github.pixzarpg.core.impl.datapacks;

import io.github.pixzarpg.core.api.datapacks.APIDataPackManager;
import io.github.pixzarpg.core.impl.RPGManager;
import io.github.pixzarpg.core.impl.utils.TextUtils;

import java.io.File;
import java.util.Arrays;

public class DataPackManager implements APIDataPackManager {

    private final static String LOG_PREFIX = "DataPacks";

    private final DataPackParser loader;
    private final RPGManager manager;


    public DataPackManager(RPGManager manager) {
        this.manager = manager;
        this.loader = new DataPackParser(this);
    }

    public RPGManager getManager() {
        return this.manager;
    }

    @Override
    public void load() {
        this.manager.getPlugin().getLogger()
                .info(TextUtils.generateLoggerMessage(LOG_PREFIX, "Loading data packs"));

        File dataPacksFolder = new File(this.manager.getPlugin().getDataFolder().getAbsolutePath() + "/packs");
        dataPacksFolder.mkdirs();

        Arrays.stream(dataPacksFolder.listFiles())
                .forEach(this.loader::parse);


        this.manager.getPlugin().getLogger()
                .info(TextUtils.generateLoggerMessage(LOG_PREFIX, "Finished loading data packs"));
    }

}
