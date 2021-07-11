package io.github.pixzarpg.core.impl.spigot.datapacks;

import io.github.pixzarpg.core.api.datapacks.APIDataPack;
import io.github.pixzarpg.core.datapacks.api.DataPackManifestObject;
import io.github.pixzarpg.core.impl.spigot.exceptions.datapacks.CircularDependencyException;
import io.github.pixzarpg.core.impl.spigot.exceptions.datapacks.MissingDependencyException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiConsumer;

public class DataPackRegistry {

    private final Map<UUID, APIDataPack> dataPacks;
    private final Map<UUID, Status> dataPackLoadStatuses;

    public DataPackRegistry(Map<UUID, APIDataPack> dataPacks) {
        this.dataPacks = dataPacks;
        this.dataPackLoadStatuses = new HashMap<UUID, Status>(){
            {
                dataPacks.keySet().forEach(uuid -> this.put(uuid, Status.UNLOADED));
            }
        };
    }

    /**
     * Attempt to register all data packs regardless of if a data pack mid-registration fails to register
     * @param exceptionHandler handler when registration of a datapack fails
     */
    public void registerAll(BiConsumer<APIDataPack, IOException> exceptionHandler) {
        this.dataPacks.values()
                .forEach(dataPack -> {
                    try {
                        this.register(dataPack);
                    } catch (IOException exception) {
                        exceptionHandler.accept(dataPack, exception);
                    }
                });
    }

    public void register(APIDataPack dataPack) throws IOException {
        if (this.dataPackLoadStatuses.get(dataPack.getManifest().getUuid()) != Status.LOADED) {
            this.dataPackLoadStatuses.put(dataPack.getManifest().getUuid(), Status.LOADING);

            // Load dependencies
            for (DataPackManifestObject.Dependency dependency : dataPack.getManifest().getDependencies()) {
                Status loadStatus = this.dataPackLoadStatuses.getOrDefault(dependency.getUuid(), null);

                // Check if the dependency exists
                if (loadStatus == null) {
                    this.dataPackLoadStatuses.put(dependency.getUuid(), Status.FAILED);
                    throw new MissingDependencyException("Missing dependency " + dependency.getUuid() + " (no dependency found)");
                }

                APIDataPack dependencyDataPack = this.dataPacks.get(dependency.getUuid());
                if (dependencyDataPack.getManifest().getVersion() != dependency.getVersion()) {
                    this.dataPackLoadStatuses.put(dependency.getUuid(), Status.FAILED);
                    throw new MissingDependencyException("Missing dependency " + dependency.getUuid() + " (Invalid version)");
                }

                // At what stage is this dependency?
                switch (loadStatus) {
                    case UNLOADED:
                        this.dataPackLoadStatuses.put(dependency.getUuid(), Status.LOADING);
                        this.register(dependencyDataPack);
                        break;
                    case FAILED:
                        this.dataPackLoadStatuses.put(dependency.getUuid(), Status.FAILED);
                        throw new MissingDependencyException("Could not load " + dependency.getUuid() + " because it failed to load.");
                    case LOADING:
                        this.dataPackLoadStatuses.put(dependency.getUuid(), Status.FAILED);
                        throw new CircularDependencyException("Could not load " + dependency.getUuid() + " because it is a circular reference");
                }
            }

            // All dependencies have loaded. Load the requested data pack.
            try {
                dataPack.register();
            } catch (IOException exception) {
                this.dataPackLoadStatuses.put(dataPack.getManifest().getUuid(), Status.FAILED);
                throw exception;
            }
            this.dataPackLoadStatuses.put(dataPack.getManifest().getUuid(), Status.LOADED);

        }
    }

    public void unregisterAll() {
        this.dataPacks.values().forEach(this::unregister);
    }

    public void unregister(APIDataPack dataPack) {
        dataPack.unregister();
    }


    private enum Status {
        LOADED,
        UNLOADED,
        FAILED,
        LOADING
    }

}
