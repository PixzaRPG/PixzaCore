package io.github.pixzarpg.core.impl.spigot.database.impl.player.entity;

import io.github.pixzarpg.core.impl.spigot.database.api.player.entity.APIPlayerEntityData;

import java.util.UUID;

public class PlayerEntityData implements APIPlayerEntityData {

    private final UUID uuid;
    private byte[] serializedInventoryData = new byte[0];

    private double health;
    private int mana;

    private double x;
    private double y;
    private double z;


    private PlayerEntityData(UUID uuid, byte[] serializedInventoryData, double health, int mana, double x, double y, double z) {
        this.uuid = uuid;
        this.serializedInventoryData = serializedInventoryData;
        this.health = health;
        this.mana = mana;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public UUID getUuid() {
        return this.uuid;
    }

    @Override
    public byte[] getInventoryData() {
        return this.serializedInventoryData;
    }

    @Override
    public double getHealth() {
        return this.health;
    }

    @Override
    public int getMana() {
        return this.mana;
    }

    @Override
    public double getX() {
        return this.x;
    }

    @Override
    public double getY() {
        return this.y;
    }

    @Override
    public double getZ() {
        return this.z;
    }


    public static class Builder {

        private UUID uuid;
        private byte[] serializedInventoryData = new byte[0];

        private double health;
        private int mana;

        private double x;
        private double y;
        private double z;


        public Builder setUuid(UUID uuid) {
            this.uuid = uuid;
            return this;
        }

        public Builder setSerializedInventoryData(byte[] data) {
            this.serializedInventoryData = data;
            return this;
        }

        public Builder setHealth(double health) {
            this.health = health;
            return this;
        }

        public Builder setMana(int mana) {
            this.mana = mana;
            return this;
        }

        public Builder setPosition(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
            return this;
        }

        public PlayerEntityData build() {
            return new PlayerEntityData(this.uuid, this.serializedInventoryData, this.health, this.mana, this.x, this.y, this.z);
        }

    }

}
