package io.github.pixzarpg.core.datapacks.api;

import java.util.UUID;

public class DataPackManifestFile {

    private UUID uuid;
    private int version;

    private String name;
    private String description;
    private String author;

    private Dependency[] dependencies = new Dependency[0];


    public static class Dependency {

        private UUID uuid;
        private int version;

    }

}
