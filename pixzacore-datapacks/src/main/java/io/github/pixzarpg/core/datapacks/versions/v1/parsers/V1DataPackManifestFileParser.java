package io.github.pixzarpg.core.datapacks.versions.v1.parsers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.github.pixzarpg.core.datapacks.DataPackFileParser;
import io.github.pixzarpg.core.datapacks.api.DataPackManifestObject;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class V1DataPackManifestFileParser implements DataPackFileParser<DataPackManifestObject> {

    public static final DataPackFileParser<DataPackManifestObject> INSTANCE = new V1DataPackManifestFileParser();


    @Override
    public DataPackManifestObject parse(JsonObject data) {
        // dependencies
        JsonArray jsonDependencies = data.get("dependencies").getAsJsonArray();
        Set<DataPackManifestObject.Dependency> dependencies = new HashSet<>(jsonDependencies.size());
        for (int i = 0; i < jsonDependencies.size(); i++) {
            JsonObject jsonDependency = jsonDependencies.get(i).getAsJsonObject();
            dependencies.add(new DataPackManifestObject.Dependency(
                    UUID.fromString(jsonDependency.get("uuid").getAsString()),
                    jsonDependency.get("version").getAsInt()));
        }

        JsonObject manifestInfo = data.getAsJsonObject("info");
        return new DataPackManifestObject.Builder()
                .setUuid(UUID.fromString(manifestInfo.get("uuid").getAsString()))
                .setManifestVersion(data.get("version").getAsInt())
                .setDescription(manifestInfo.get("description").getAsString())
                .setName(manifestInfo.get("name").getAsString())
                .setVersion(manifestInfo.get("version").getAsInt())
                .setAuthor(manifestInfo.get("author").getAsString())
                .setDependencies(dependencies)
                .build();
    }

}
