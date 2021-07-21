package io.github.pixzarpg.core.impl.spigot.entities;

import io.github.pixzarpg.core.impl.spigot.RPGManager;
import io.github.pixzarpg.core.impl.spigot.database.api.player.entity.APIPlayerEntityData;
import io.github.pixzarpg.core.impl.spigot.database.api.player.entity.APIPlayerEntityDataRepository;
import io.github.pixzarpg.core.impl.spigot.database.impl.player.entity.PlayerEntityData;
import io.github.pixzarpg.core.impl.spigot.exceptions.datapacks.RepositoryException;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.*;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class RPGPlayer extends RPGEntity {

    private final Player player;
    private boolean loaded;

    public RPGPlayer(Player player) {
        super(player);
        this.player = player;
    }

    public Player getPlayer() {
        return this.player;
    }

    public boolean isLoaded() {
        return this.loaded;
    }

    /**
     * Save the player's data to the database
     * @throws RepositoryException if a repository error occurred
     * @throws IOException if serializing the {@link APIPlayerEntityData} failed
     */
    public void save() throws RepositoryException, IOException {
        if (!this.isLoaded()) {
            throw new IllegalStateException("Tried to save when player data was not loaded.");
        }

        APIPlayerEntityData entityData = this.serializePlayerEntityData();
        this.save(entityData);
    }

    /**
     * Saves the player's data to the database
     * @param entityData {@link APIPlayerEntityData} to save to the database
     * @throws RepositoryException if a repository error occurred
     */
    public void save(APIPlayerEntityData entityData) throws RepositoryException {
        if (!this.isLoaded()) {
            throw new IllegalStateException("Tried to save when player data was not loaded.");
        }

        APIPlayerEntityDataRepository repository = RPGManager.getInstance()
                .getDatabaseManager()
                .getPlayerEntityDataRepository();

        boolean hasExistingData = repository.getEntityData(this.getPlayer().getUniqueId()) != null;
        if (hasExistingData) {
            repository.updateEntityData(entityData);
        } else {
            repository.createEntityData(entityData);
        }
    }

    /**
     * Request a save of the player data to the database
     * This should be called on the main thread.
     * @throws IOException if failed to serialize entity data
     * @return {@link CompletableFuture} that gets completed on save
     */
    public CompletableFuture<Void> requestAsyncSave() throws IOException {
        APIPlayerEntityData entityData = this.serializePlayerEntityData();

        CompletableFuture<Void> completableFuture = new CompletableFuture<>();
        Bukkit.getServer().getScheduler().runTaskAsynchronously(RPGManager.getInstance().getPlugin(), () -> {
            try {
                this.save(entityData);
            } catch (RepositoryException exception) {
                completableFuture.completeExceptionally(exception);
            }
            completableFuture.complete(null);
        });
        return completableFuture;
    }

    /**
     * Construct {@link APIPlayerEntityData} object for player
     * @return {@link APIPlayerEntityData}
     * @throws IOException
     */
    private APIPlayerEntityData serializePlayerEntityData() throws IOException {
        return new PlayerEntityData.Builder()
                .setUuid(this.getPlayer().getUniqueId())
                .setSerializedInventoryData(this.serializeInventory())
                .setHealth(this.getPlayer().getHealth())
                .setMana(0)
                .setPosition(this.getPlayer().getLocation().getX(), this.getPlayer().getLocation().getY(), this.getPlayer().getLocation().getZ())
                .build();
    }

    /**
     * Serialize the player's inventory into bytes
     * @return byte array
     * @throws IOException if serialization fails
     */
    private byte[] serializeInventory() throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (ObjectOutputStream objectOutputStream = new BukkitObjectOutputStream(outputStream)) {
            for (int inventoryIndex = 0; inventoryIndex < this.getPlayer().getInventory().getSize(); inventoryIndex++) {
                ItemStack itemStack = this.getPlayer().getInventory().getItem(inventoryIndex);
                if (itemStack == null) {
                    itemStack = new ItemStack(Material.AIR);
                }
                Map<String, Object> serializedItem = itemStack.serialize();
                objectOutputStream.writeObject(serializedItem);
            }
            objectOutputStream.flush();
        }
        return outputStream.toByteArray();
    }

    /**
     * Fetch the player's data and load it
     * @return {@link CompletableFuture} that gets completed when player data is loaded. The resolved boolean is whether or not player data existed for the player
     */
    public CompletableFuture<Boolean> loadAsync() {
        CompletableFuture<Boolean> completableFuture = new CompletableFuture<>();
        Bukkit.getServer().getScheduler().runTaskAsynchronously(RPGManager.getInstance().getPlugin(), () -> {
            APIPlayerEntityData data;
            try {
                data = RPGManager.getInstance()
                        .getDatabaseManager()
                        .getPlayerEntityDataRepository()
                        .getEntityData(this.getPlayer().getUniqueId());
            } catch (RepositoryException exception) {
                Bukkit.getServer().getScheduler().runTask(RPGManager.getInstance().getPlugin(), () -> completableFuture.completeExceptionally(exception));
                return;
            }

            // Load data on main thread
            Bukkit.getServer().getScheduler().runTask(RPGManager.getInstance().getPlugin(), () -> {
                if (data == null) {
                    // No existing data
                    this.loaded = true;
                    completableFuture.complete(false);
                } else {
                    // Has existing data
                    this.getPlayer().setHealth(data.getHealth());
                    this.getPlayer().teleport(
                            new Location(RPGManager.getInstance().getWorldManager().getWorld(), data.getX(), data.getY(), data.getZ())
                    );

                    try {
                        this.deserializeInventory(data.getInventoryData());
                    } catch (IOException exception) {
                        completableFuture.completeExceptionally(exception);
                        return;
                    }
                    this.loaded = true;
                    completableFuture.complete(true);
                }
            });
        });

        return completableFuture;
    }

    /**
     * Deserialize a array of bytes back into it's inventory format
     * @param data the byte array
     * @throws IOException if deserialization fails
     */
    private void deserializeInventory(byte[] data) throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
        try (ObjectInputStream objectInputStream = new BukkitObjectInputStream(inputStream)) {
            for (int inventoryIndex = 0; inventoryIndex < this.getPlayer().getInventory().getSize(); inventoryIndex++) {
                Map<String, Object> serializedItem = (Map<String, Object>)objectInputStream.readObject();
                ItemStack itemStack = ItemStack.deserialize(serializedItem);
                this.getPlayer().getInventory().setItem(inventoryIndex, itemStack);
            }
        } catch (ClassNotFoundException exception) {
            throw new IOException("Could not find class for deserialized ItemStack when deserializing inventory for " + this.getPlayer().getUniqueId());
        }
    }

}
