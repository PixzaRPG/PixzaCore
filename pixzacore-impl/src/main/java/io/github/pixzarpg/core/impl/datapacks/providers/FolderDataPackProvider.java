package io.github.pixzarpg.core.impl.datapacks.providers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.github.pixzarpg.core.api.datapacks.providers.APIDataPackProvider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FolderDataPackProvider implements APIDataPackProvider {

    private static final Gson GSON = new Gson();

    private final File parentFolder;

    public FolderDataPackProvider(File parentFolder) {
        this.parentFolder = parentFolder;
    }

    @Override
    public JsonObject getFile(String path) throws IOException {
        File file = Paths.get(this.parentFolder.getAbsolutePath(), path).toFile();
        try (FileReader reader = new FileReader(file)) {
            return GSON.fromJson(reader, JsonObject.class);
        }
    }

    @Override
    public String[] getFiles(String path, boolean recursive) throws IOException {
        return Files.walk(Paths.get(this.parentFolder.getAbsolutePath(), path))
            .filter(filePath -> filePath.toFile().isFile())
            .map(Path::toString)
            .toArray(String[]::new);
    }

}
