package io.github.pixzarpg.core.impl.spigot.database.api.player.entity;

import io.github.pixzarpg.core.impl.spigot.database.api.APIRepository;
import io.github.pixzarpg.core.impl.spigot.exceptions.datapacks.RepositoryException;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public interface APIPlayerEntityDataRepository extends APIRepository {

    /**
     * Retrieve {@link APIPlayerEntityData} from the database that matches the UUID
     * @param uuid The {@link UUID} of the player
     * @return {@link APIPlayerEntityData} or null if no entity data was found.
     */
    APIPlayerEntityData getEntityData(UUID uuid) throws RepositoryException;

    /**
     * Create a entry in the database for the {@link APIPlayerEntityData} provided if it doesn't exist
     * @param data Entity data
     */
    void createEntityData(APIPlayerEntityData data) throws RepositoryException;

    /**
     * Delete an entry from the database with the specified {@link UUID}
     * @param uuid The {@link UUID} of the player
     */
    void deleteEntityData(UUID uuid) throws RepositoryException;

    /**
     * Upload the existing {@link APIPlayerEntityData} provided in the database
     * @param data Entity data
     */
    void updateEntityData(APIPlayerEntityData data) throws RepositoryException;

    /**
     * Retrieve existing {@link APIPlayerEntityData} that meets the predicate
     * @param predicate
     * @return
     */
    List<APIPlayerEntityData> query(Predicate<APIPlayerEntityData> predicate) throws RepositoryException;

}
