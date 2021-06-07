package io.github.pixzarpg.core.impl.datapacks;

import io.github.pixzarpg.core.api.APIRPGManager;
import io.github.pixzarpg.core.api.datapacks.APIDataPackManager;
import io.github.pixzarpg.core.impl.utils.TextUtils;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Arrays;

public class DataPackManager implements APIDataPackManager {

    private final static String LOG_PREFIX = "DataPacks";

    private final DataPackParser loader;
    private final APIRPGManager<JavaPlugin> manager;


    public DataPackManager(APIRPGManager<JavaPlugin> manager) {
        this.manager = manager;
        this.loader = new DataPackParser(this);
    }

    public APIRPGManager<JavaPlugin> getManager() {
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
