package io.github.pixzarpg.core.impl.spigot.database.api.player.entity;

import java.util.UUID;

public interface APIPlayerEntityData {

    /**
     * Retrieve the {@link UUID} of the user
     * @return the uuid
     */
    UUID getUuid();

    /**
     * Get the serialized inventory data for the player
     * @return bytes
     */
    byte[] getInventoryData();

    /**
     * Amount of health the player has
     * @return current amount of health
     */
    double getHealth();

    /**
     * Amount of mana the player has
     * @return current amount of mana
     */
    int getMana();

    /**
     * x coordinate of the player
     * @return x coordinate
     */
    double getX();

    /**
     * y coordinate of the player
     * @return y coordinate
     */
    double getY();

    /**
     * z coordinate of the player
     * @return z coordinate
     */
    double getZ();

}
