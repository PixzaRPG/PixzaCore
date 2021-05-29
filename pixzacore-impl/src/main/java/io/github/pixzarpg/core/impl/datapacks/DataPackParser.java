package io.github.pixzarpg.core.impl.datapacks;

import io.github.pixzarpg.core.api.datapacks.APIDataPack;
import io.github.pixzarpg.core.api.datapacks.APIDataPackParser;

import java.io.File;

public class DataPackParser implements APIDataPackParser<File> {

    private final DataPackManager dataPackManager;


    public DataPackParser(DataPackManager dataPackManager) {
        this.dataPackManager = dataPackManager;
    }

    @Override
    public APIDataPack parse(File input) {
        return null;
    }

}
