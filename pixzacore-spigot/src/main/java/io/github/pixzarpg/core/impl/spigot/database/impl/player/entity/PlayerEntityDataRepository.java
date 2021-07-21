package io.github.pixzarpg.core.impl.spigot.database.impl.player.entity;

import io.github.pixzarpg.core.impl.spigot.database.DatabaseManager;
import io.github.pixzarpg.core.impl.spigot.database.SQLDatabaseQuery;
import io.github.pixzarpg.core.impl.spigot.database.api.player.entity.APIPlayerEntityData;
import io.github.pixzarpg.core.impl.spigot.database.api.player.entity.APIPlayerEntityDataRepository;
import io.github.pixzarpg.core.impl.spigot.exceptions.datapacks.RepositoryException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

/**
 * Access point to retrieve entity data
 */

public class PlayerEntityDataRepository implements APIPlayerEntityDataRepository {

    private final static String CREATE_TABLE_STMT = "CREATE TABLE IF NOT EXISTS player_entity_data (" +
                                                        "uuid VARCHAR(36) NOT NULL," +
                                                        "inventory_data BLOB NOT NULL," +  // 1 MB
                                                        "health DOUBLE NOT NULL," +
                                                        "mana INT NOT NULL," +
                                                        "x DOUBLE NOT NULL," +
                                                        "y DOUBLE NOT NULL," +
                                                        "z DOUBLE NOT NULL," +
                                                        "PRIMARY KEY(uuid)" +
                                                    ");";
    private final static String GET_ENTITY_BY_UUID_STMT = "SELECT inventory_data, health, mana, x, y, z FROM player_entity_data WHERE uuid=?";
    private final static String GET_ENTITIES_STMT = "SELECT uuid, inventory_data, health, mana, x, y, z FROM player_entity_data WHERE ";
    private final static String CREATE_ENTITY_STMT = "INSERT INTO player_entity_data (uuid, inventory_data, health, mana, x, y, z) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private final static String DELETE_ENTITY_STMT = "DELETE FROM player_entity_data WHERE uuid=?";
    private final static String UPDATE_ENTITY_STMT = "UPDATE player_entity_data SET inventory_data=?, health=?, mana=?, x=?, y=?, z=? WHERE uuid=?";

    private final DatabaseManager databaseManager;


    public PlayerEntityDataRepository(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    @Override
    public void initialize() throws RepositoryException {
        // Create the table required
        try (Connection connection = this.databaseManager.getSQLConnection()) {
            try (Statement statement = connection.createStatement()) {
                statement.execute(CREATE_TABLE_STMT);
            }
        } catch (SQLException exception) {
            throw new RepositoryException("Failed to initialize repository table.", exception);
        }
    }

    @Override
    public APIPlayerEntityData getEntityData(UUID uuid) throws RepositoryException {
        try (Connection connection = this.databaseManager.getSQLConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(GET_ENTITY_BY_UUID_STMT)) {
                statement.setString(1, uuid.toString());
                try (ResultSet results = statement.executeQuery()) {
                    if (!results.next()) {
                        return null;
                    }

                    return new PlayerEntityData.Builder()
                            .setUuid(uuid)
                            .setSerializedInventoryData(results.getBytes("inventory_data"))
                            .setHealth(results.getFloat("health"))
                            .setMana(results.getInt("mana"))
                            .setPosition(results.getFloat("x"), results.getFloat("y"), results.getFloat("z"))
                            .build();
                }
            }
        } catch (SQLException exception) {
            throw new RepositoryException("Failed to retrieve entity data for UUID " + uuid, exception);
        }
    }

    @Override
    public void createEntityData(APIPlayerEntityData data) throws RepositoryException {
        try (Connection connection = this.databaseManager.getSQLConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(CREATE_ENTITY_STMT)) {
                statement.setString(1, data.getUuid().toString());
                statement.setBytes(2, data.getInventoryData());
                statement.setDouble(3, data.getHealth());
                statement.setInt(4, data.getMana());
                statement.setDouble(5, data.getX());
                statement.setDouble(6, data.getY());
                statement.setDouble(7, data.getZ());
                statement.execute();
            }
        } catch (SQLException exception) {
            throw new RepositoryException("Failed to create entity data", exception);
        }
    }

    @Override
    public void deleteEntityData(UUID uuid) throws RepositoryException {
        try (Connection connection = this.databaseManager.getSQLConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(DELETE_ENTITY_STMT)) {
                statement.setString(1, uuid.toString());
                statement.execute();
            }
        } catch (SQLException exception) {
            throw new RepositoryException("Failed to delete entity data", exception);
        }
    }

    @Override
    public void updateEntityData(APIPlayerEntityData data) throws RepositoryException {
        try (Connection connection = this.databaseManager.getSQLConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(UPDATE_ENTITY_STMT)) {
                statement.setBytes(1, data.getInventoryData());
                statement.setDouble(2, data.getHealth());
                statement.setInt(3, data.getMana());
                statement.setDouble(4, data.getX());
                statement.setDouble(5, data.getY());
                statement.setDouble(6, data.getZ());
                statement.setString(7, data.getUuid().toString());
                statement.executeUpdate();
            }
        } catch (SQLException exception) {
            throw new RepositoryException("Failed to update entity data", exception);
        }
    }

    @Override
    public List<APIPlayerEntityData> query(Predicate<APIPlayerEntityData> predicate) throws RepositoryException {
        if (!(predicate instanceof SQLDatabaseQuery)) {
            throw new IllegalArgumentException("The predicate passed does not support SQL");
        }
        String query = GET_ENTITIES_STMT + ((SQLDatabaseQuery)predicate).toSQLWhereClause();
        try (Connection connection = this.databaseManager.getSQLConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                for (int i = 0; i < ((SQLDatabaseQuery)predicate).getParameters().length; i++) {
                    statement.setObject(i + 1, ((SQLDatabaseQuery)predicate).getParameters()[i]);
                }

                try (ResultSet results = statement.executeQuery()) {
                    List<APIPlayerEntityData> data = new ArrayList<>();
                    while (results.next()) {
                        data.add(
                                new PlayerEntityData.Builder()
                                    .setUuid(UUID.fromString(results.getString("uuid")))
                                    .setSerializedInventoryData(results.getBytes("inventory_data"))
                                    .setHealth(results.getDouble("health"))
                                    .setMana(results.getInt("mana"))
                                    .setPosition(results.getDouble("x"), results.getDouble("y"), results.getDouble("z"))
                                    .build());
                    }
                    return data;
                }
            }
        } catch (SQLException exception) {
            throw new RepositoryException("Failed to query entity data", exception);
        }
    }

}
