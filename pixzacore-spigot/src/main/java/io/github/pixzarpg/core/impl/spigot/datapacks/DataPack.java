package io.github.pixzarpg.core.impl.spigot.datapacks;

import com.google.gson.JsonObject;
import io.github.pixzarpg.core.datapacks.DataPackFileParserRegistry;
import io.github.pixzarpg.core.datapacks.api.DataPackItemObject;
import io.github.pixzarpg.core.datapacks.api.DataPackManifestObject;
import io.github.pixzarpg.core.datapacks.api.DataPackRegionObject;
import io.github.pixzarpg.core.datapacks.providers.DataPackProvider;
import io.github.pixzarpg.core.impl.spigot.world.regions.WorldRegion;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class DataPack {

    private final DataPackManager manager;
    private final DataPackProvider provider;

    private final DataPackManifestObject manifest;
    private final DataPackFileParserRegistry registry;

    private final Set<WorldRegion> worldRegions = new HashSet<>();


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
    public void register() throws IOException {
        this.registerRegions();
        this.registerItems();
    }

    /**
     * Remove the contents of this data pack from the server
     */
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

    private void registerItems() throws IOException {
        String[] itemFiles = this.provider.getFiles("/items", true);
        for (String itemFilePath : itemFiles) {
            DataPackItemObject itemObject = this.registry.getItemParser().parse(this.provider.getFile(itemFilePath));
            // TODO: ItemRegistry: register item
        }
    }

    /**
     * Recursive method that registers parent region as well as all sub regions within the DataPackRegionObject
     * @param regionObject
     * @return
     */
    private WorldRegion registerAndRetrieveSubRegion(DataPackRegionObject regionObject) {
        Set<WorldRegion> subRegions = new HashSet<WorldRegion>(){
            {
                for (DataPackRegionObject subRegion : regionObject.getSubRegions()) {
                    this.add(DataPack.this.registerAndRetrieveSubRegion(subRegion));
                }
            }
        };

        // TODO: parse flags
        WorldRegion region = new WorldRegion.Builder()
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
