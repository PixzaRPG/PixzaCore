package io.github.pixzarpg.core.api;

import io.github.pixzarpg.core.api.datapacks.APIDataPackManager;
import io.github.pixzarpg.core.api.world.APIWorldManager;

public interface APIRPGManager<T> {

    APIWorldManager getWorldManager();

    APIDataPackManager getDataPackManager();

    APIRPGConfig getConfig();

    T getPlugin();

    /**
     * Close the RPG instance and save any unsaved data
     */
    void close();

}
