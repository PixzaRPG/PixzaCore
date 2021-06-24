package io.github.pixzarpg.core.datapacks.api;

import java.util.UUID;

public class DataPackManifestFile {

    private UUID uuid;
    private int version;

    private String name;
    private String description;
    private String author;

    private Dependency[] dependencies = new Dependency[0];


    public UUID getUuid() {
        return this.uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public int getVersion() {
        return this.version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Dependency[] getDependencies() {
        return this.dependencies;
    }

    public void setDependencies(Dependency[] dependencies) {
        this.dependencies = dependencies;
    }


    public static class Dependency {

        private final UUID uuid;
        private final int version;


        public Dependency(UUID uuid, int version) {
            this.uuid = uuid;
            this.version = version;
        }

        public UUID getUuid() {
            return this.uuid;
        }

        public int getVersion() {
            return this.version;
        }

    }

}
