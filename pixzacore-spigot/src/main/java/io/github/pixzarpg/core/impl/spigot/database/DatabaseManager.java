package io.github.pixzarpg.core.impl.spigot.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.github.pixzarpg.core.impl.spigot.RPGManager;
import io.github.pixzarpg.core.impl.spigot.config.database.SQLDatabaseConfig;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseManager {

    private final RPGManager rpgManager;

    private HikariDataSource sqlDataSource = null;

    public DatabaseManager(RPGManager rpgManager) {
        this.rpgManager = rpgManager;
    }

    public RPGManager getRPGManager() {
        return this.rpgManager;
    }

    /**
     * Create all database connections
     */
    public void initialize() {
        this.getRPGManager().getPlugin().getLogger().info("Initializing database connections...");
        this.initializeSQLPool();
        this.getRPGManager().getPlugin().getLogger().info("Finished loading database connections");
    }

    /**
     * Called on RPG shutdown to close all available database connections
     */
    public void close() {
        this.getRPGManager().getPlugin().getLogger().info("Closing database connections...");
        if (this.sqlDataSource != null) {
            this.getRPGManager().getPlugin().getLogger().info("> Closing SQL database connection");
            this.sqlDataSource.close();
            this.getRPGManager().getPlugin().getLogger().info("> Closed SQL database connection");
        }
        this.getRPGManager().getPlugin().getLogger().info("Finished closing database connections");
    }

    private void initializeSQLPool() {
        this.getRPGManager().getPlugin().getLogger().info("> Loading SQL database connection");
        SQLDatabaseConfig sqlDatabaseConfig = this.getRPGManager().getConfig().getSQLConfig();
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl("jdbc:mysql://" + sqlDatabaseConfig.getIp() + ":" + sqlDatabaseConfig.getPort() + "/" + sqlDatabaseConfig.getDatabase());
        hikariConfig.setUsername(sqlDatabaseConfig.getUsername());
        hikariConfig.setPassword(sqlDatabaseConfig.getPassword());
        this.sqlDataSource = new HikariDataSource(hikariConfig);
        this.getRPGManager().getPlugin().getLogger().info("> Connected to SQL database");
    }

    public Connection getSQLConnection() throws SQLException {
        return this.sqlDataSource.getConnection();
    }

}
