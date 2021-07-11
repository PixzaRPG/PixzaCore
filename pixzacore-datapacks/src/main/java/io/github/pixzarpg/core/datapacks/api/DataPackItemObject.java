package io.github.pixzarpg.core.datapacks.api;

import com.google.gson.JsonObject;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;

public class DataPackItemObject {

    private final UUID uuid;
    private final String minecraftItemId;
    private final Set<ItemComponent> components;


    public DataPackItemObject(UUID uuid, String minecraftItemId, Set<ItemComponent> components) {
        this.uuid = uuid;
        this.minecraftItemId = minecraftItemId;
        this.components = components;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public String getMinecraftItemId() {
        return this.minecraftItemId;
    }

    public Set<ItemComponent> getComponents() {
        return Collections.unmodifiableSet(this.components);
    }


    public static class ItemComponent {

        private final String type;
        private final JsonObject data;


        public ItemComponent(String type, JsonObject data) {
            this.type = type;
            this.data = data;
        }

        public String getType() {
            return this.type;
        }

        public JsonObject getData() {
            return this.data;
        }

    }

}
