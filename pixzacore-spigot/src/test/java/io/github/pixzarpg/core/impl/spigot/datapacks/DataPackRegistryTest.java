package io.github.pixzarpg.core.impl.spigot.datapacks;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.github.pixzarpg.core.datapacks.api.DataPackManifestObject;
import io.github.pixzarpg.core.datapacks.providers.DataPackProvider;
import io.github.pixzarpg.core.impl.spigot.exceptions.datapacks.CircularDependencyException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.fail;

public class DataPackRegistryTest {

    @Test
    public void shouldLoadDependenciesBeforeParentDataPack() throws IOException {

        // parentDataPack depends on dependencyA and dependencyB. DependencyA depends on parentDataPack

        DataPack innerDependency = new MockDataPack();
        DataPack dependencyA = new MockDataPack(new HashSet<DataPackManifestObject.Dependency>(){
            {
                this.add(toDependency(innerDependency));
            }
        });
        DataPack dependencyB = new MockDataPack();

        DataPack parentDataPack = new MockDataPack(new HashSet<DataPackManifestObject.Dependency>(){
            {
                this.add(toDependency(dependencyA));
                this.add(toDependency(dependencyB));
            }
        });

        DataPackRegistry registry = new DataPackRegistry(new HashMap<UUID, DataPack>(){
            {
                this.put(dependencyA.getManifest().getUuid(), dependencyA);
                this.put(dependencyB.getManifest().getUuid(), dependencyB);
                this.put(innerDependency.getManifest().getUuid(), innerDependency);
                this.put(parentDataPack.getManifest().getUuid(), parentDataPack);
            }
        });
        registry.registerAll((dataPack, exception) -> fail("Failed to load data packs"));

    }

    @Test
    public void shouldFailWithCircularDependencies() throws IOException {

        // rootDataPack depends on innerDependency which depends on rootDataPack
        MockDataPack innerDependency = new MockDataPack();
        DataPack rootDataPack = new MockDataPack(new HashSet<DataPackManifestObject.Dependency>(){
            {
                this.add(toDependency(innerDependency));
            }
        });
        innerDependency.getManifest().setDependencies(new HashSet<DataPackManifestObject.Dependency>(){
            {
                this.add(toDependency(rootDataPack));
            }
        });

        DataPackRegistry registry = new DataPackRegistry(new HashMap<UUID, DataPack>(){
            {
                this.put(rootDataPack.getManifest().getUuid(), rootDataPack);
                this.put(innerDependency.getManifest().getUuid(), innerDependency);
            }
        });

        try {
            registry.register(rootDataPack);
        } catch (CircularDependencyException exception) {
            return;
        } catch (IOException exception) {
            fail("Recieved IOException", exception);
        }
        fail("Loaded dependencies without throwing circular dependency exception");

    }

    private static DataPackManifestObject.Dependency toDependency(DataPack dataPack) {
        return new DataPackManifestObject.Dependency(dataPack.getManifest().getUuid(), dataPack.getManifest().getVersion());
    }


    private static class MockDataPack extends DataPack {

        private final MockManifestObject manifest;


        public MockDataPack() throws IOException {
            this(Collections.emptySet());
        }

        public MockDataPack(Set<DataPackManifestObject.Dependency> dependencies) throws IOException {
            super(null, new DataPackProvider() {
                @Override
                public String getDataPackName() {
                    return null;
                }

                @Override
                public JsonObject getFile(String path) {
                    JsonObject manifestFile = new JsonObject();
                    manifestFile.addProperty("version", 1);
                    manifestFile.add("dependencies", new JsonArray());

                    JsonObject infoObj = new JsonObject();
                    infoObj.addProperty("uuid", UUID.randomUUID().toString());
                    infoObj.addProperty("description", "str");
                    infoObj.addProperty("name", "str");
                    infoObj.addProperty("version", 1);
                    infoObj.addProperty("author", "str");

                    manifestFile.add("info", infoObj);

                    return manifestFile;
                }

                @Override
                public String[] getFiles(String rootDirPath, boolean recursive) {
                    return new String[0];
                }

            });
            this.manifest = new MockManifestObject(UUID.randomUUID(), 1, "", "", "", 1, dependencies);
        }

        @Override
        public void register() throws IOException {}

        @Override
        public void unregister() {}

        @Override
        public MockManifestObject getManifest() {
            return this.manifest;
        }

    }

    private static class MockManifestObject extends DataPackManifestObject {

        private Set<Dependency> dependencies;

        protected MockManifestObject(UUID uuid, int manifestVersion, String name, String description, String author, int version, Set<Dependency> dependencies) {
            super(uuid, manifestVersion, name, description, author, version, dependencies);
            this.dependencies = dependencies;
        }

        public void setDependencies(Set<Dependency> dependencies) {
            this.dependencies = dependencies;
        }

        @Override
        public Set<Dependency> getDependencies() {
            return this.dependencies;
        }
    }

}
