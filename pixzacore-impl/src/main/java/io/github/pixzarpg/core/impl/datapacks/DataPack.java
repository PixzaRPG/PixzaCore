package io.github.pixzarpg.core.impl.datapacks;

import com.google.gson.JsonObject;
import io.github.pixzarpg.core.api.datapacks.APIDataPack;
import io.github.pixzarpg.core.api.world.regions.APIWorldRegion;
import io.github.pixzarpg.core.datapacks.DataPackFileParserRegistry;
import io.github.pixzarpg.core.datapacks.api.DataPackManifestObject;
import io.github.pixzarpg.core.datapacks.api.DataPackRegionObject;
import io.github.pixzarpg.core.datapacks.providers.DataPackProvider;
import io.github.pixzarpg.core.impl.world.regions.WorldRegion;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class DataPack implements APIDataPack {

    private final DataPackManager manager;
    private final DataPackProvider provider;

    private final DataPackManifestObject manifest;
    private final DataPackFileParserRegistry registry;

    private final Set<APIWorldRegion> worldRegions = new HashSet<>();


    public DataPack(DataPackManager manager, DataPackProvider provider) throws IOException {
        this.manager = manager;
        this.provider = provider;

        // Determine the version of the parser to use
        int dataPackVersion;
        JsonObject manifestObject = provider.getFile("manifest.json");
        dataPackVersion = manifestObject.get("version").getAsInt();
        this.registry = DataPackFileParserRegistry.getRegistry(dataPackVersion);

        // Parse ONLY the manifest for the dependencies
        this.manifest = this.registry.getManifestParser().parse(provider.getFile("manifest.json"));
    }

    /**
     * Call this to read the contents of this data pack and register it to the server
     * @throws IOException
     */
    @Override
    public void register() throws IOException {
        this.registerRegions();
    }

    /**
     * Remove the contents of this data pack from the server
     */
    @Override
    public void unregister() {
        this.unregisterRegions();
    }

    public DataPackManifestObject getManifest() {
        return this.manifest;
    }


    private void registerRegions() throws IOException {
        String[] regionFiles = this.provider.getFiles("/regions", true);
        for (String regionFilePath : regionFiles) {
            DataPackRegionObject regionObject = this.registry.getRegionParser().parse(this.provider.getFile(regionFilePath));
            this.registerAndRetrieveSubRegion(regionObject);
        }
    }

    /**
     * Recursive method that registers parent region as well as all sub regions within the DataPackRegionObject
     * @param regionObject
     * @return
     */
    private APIWorldRegion registerAndRetrieveSubRegion(DataPackRegionObject regionObject) {
        APIWorldRegion[] subRegions = new APIWorldRegion[regionObject.getSubRegions().length];
        for (int i = 0; i < subRegions.length; i++) {
            subRegions[i] = this.registerAndRetrieveSubRegion(regionObject.getSubRegions()[i]);
        }

        // TODO: parse flags
        APIWorldRegion region = new WorldRegion.Builder()
                .setUuid(regionObject.getUuid())
                .setDescription(regionObject.getDescription())
                .setBoundaries(regionObject.getBoundaryA(), regionObject.getBoundaryB())
                .setSubRegions(subRegions)
                .build();

        this.worldRegions.add(region);
        this.manager.getRPGManager().getWorldManager().getRegionManager().registerRegion(region);
        return region;
    }

    private void unregisterRegions() {
        this.worldRegions.forEach(region ->
                this.manager.getRPGManager()
                        .getWorldManager()
                        .getRegionManager()
                        .unregisterRegion(region));
    }

}
