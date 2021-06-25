package io.github.pixzarpg.core.datapacks.api;

import java.util.UUID;

public class DataPackManifestObject {

    private final UUID uuid;
    private final int manifestVersion;

    private final String name;
    private final String description;
    private final String author;
    private final int version;

    private final Dependency[] dependencies;


    protected DataPackManifestObject(
            UUID uuid,
            int manifestVersion,
            String name,
            String description,
            String author,
            int version,
            Dependency[] dependencies
    ) {
        this.uuid = uuid;
        this.manifestVersion = manifestVersion;
        this.name = name;
        this.description = description;
        this.author = author;
        this.version = version;
        this.dependencies = dependencies;
    }


    public UUID getUuid() {
        return this.uuid;
    }

    public int getManifestVersion() {
        return this.manifestVersion;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String getAuthor() {
        return this.author;
    }

    public int getVersion() {
        return this.version;
    }

    public Dependency[] getDependencies() {
        return this.dependencies;
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


    public static class Builder {

        private UUID uuid;
        private int manifestVersion;

        private String name;
        private String description;
        private String author;
        private int version;

        private Dependency[] dependencies = new Dependency[0];


        public Builder setUuid(UUID uuid) {
            this.uuid = uuid;
            return this;
        }

        public Builder setManifestVersion(int manifestVersion) {
            this.manifestVersion = manifestVersion;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setAuthor(String author) {
            this.author = author;
            return this;
        }

        public Builder setVersion(int version) {
            this.version = version;
            return this;
        }

        public Builder setDependencies(Dependency[] dependencies) {
            this.dependencies = dependencies;
            return this;
        }

        public DataPackManifestObject build() {
            return new DataPackManifestObject(
                    this.uuid,
                    this.manifestVersion,
                    this.name,
                    this.description,
                    this.author,
                    this.version,
                    this.dependencies
            );
        }

    }

}
