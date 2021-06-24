package io.github.pixzarpg.core.datapacks.versions.v1.parsers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.github.pixzarpg.core.datapacks.DataPackFileParser;
import io.github.pixzarpg.core.datapacks.api.DataPackManifestFile;

import java.util.UUID;

public class V1DataPackManifestFileParser implements DataPackFileParser<DataPackManifestFile> {

    @Override
    public DataPackManifestFile parse(JsonObject data) {
        DataPackManifestFile manifestFile = new DataPackManifestFile();

        // Version of the parser to use
        manifestFile.setVersion(data.get("version").getAsInt());

        // General info
        JsonObject manifestInfo = data.getAsJsonObject("info");
        manifestFile.setName(manifestInfo.get("name").getAsString());
        manifestFile.setDescription(manifestInfo.get("description").getAsString());
        manifestFile.setAuthor(manifestInfo.get("author").getAsString());
        manifestFile.setUuid(UUID.fromString(manifestInfo.get("uuid").getAsString()));

        // dependencies
        JsonArray jsonDependencies = data.get("dependencies").getAsJsonArray();
        DataPackManifestFile.Dependency[] dependencies = new DataPackManifestFile.Dependency[jsonDependencies.size()];
        for (int i = 0; i < jsonDependencies.size(); i++) {
            JsonObject jsonDependency = jsonDependencies.get(i).getAsJsonObject();
            dependencies[i] = new DataPackManifestFile.Dependency(
                    UUID.fromString(jsonDependency.get("uuid").getAsString()),
                    jsonDependency.get("version").getAsInt()
            );
        }
        manifestFile.setDependencies(dependencies);

        return manifestFile;
    }

}
