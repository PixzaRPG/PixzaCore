package io.github.pixzarpg.core.datapacks.versions.v1.parsers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.github.pixzarpg.core.datapacks.DataPackFileParser;
import io.github.pixzarpg.core.datapacks.api.DataPackManifestObject;

import java.util.UUID;

public class V1DataPackManifestFileParser implements DataPackFileParser<DataPackManifestObject> {

    public static final DataPackFileParser<DataPackManifestObject> INSTANCE = new V1DataPackManifestFileParser();


    @Override
    public DataPackManifestObject parse(JsonObject data) {
        DataPackManifestObject manifestFile = new DataPackManifestObject();

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
        DataPackManifestObject.Dependency[] dependencies = new DataPackManifestObject.Dependency[jsonDependencies.size()];
        for (int i = 0; i < jsonDependencies.size(); i++) {
            JsonObject jsonDependency = jsonDependencies.get(i).getAsJsonObject();
            dependencies[i] = new DataPackManifestObject.Dependency(
                    UUID.fromString(jsonDependency.get("uuid").getAsString()),
                    jsonDependency.get("version").getAsInt()
            );
        }
        manifestFile.setDependencies(dependencies);

        return manifestFile;
    }

}
