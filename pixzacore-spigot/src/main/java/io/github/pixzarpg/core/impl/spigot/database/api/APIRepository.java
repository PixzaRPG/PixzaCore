package io.github.pixzarpg.core.impl.spigot.database.api;

import io.github.pixzarpg.core.impl.spigot.exceptions.datapacks.RepositoryException;

public interface APIRepository {

    /**
     * Called once all database providers have been loaded
     * @throws RepositoryException if an error occurred during initializing this repository
     */
    void initialize() throws RepositoryException;

}
