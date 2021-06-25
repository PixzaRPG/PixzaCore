package io.github.pixzarpg.core.impl.datapacks;

import io.github.pixzarpg.core.api.datapacks.APIDataPack;
import io.github.pixzarpg.core.datapacks.api.DataPackManifestObject;
import io.github.pixzarpg.core.impl.exceptions.datapacks.CircularDependencyException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.fail;

public class DataPackRegistryTest {

    @Test
    public void shouldLoadDependenciesBeforeParentDataPack() {

        // parentDataPack depends on dependencyA and dependencyB. DependencyA depends on parentDataPack

        APIDataPack innerDependency = new MockDataPack();
        APIDataPack dependencyA = new MockDataPack(new DataPackManifestObject.Dependency[]{ toDependency(innerDependency) });
        APIDataPack dependencyB = new MockDataPack();

        APIDataPack parentDataPack = new MockDataPack(new DataPackManifestObject.Dependency[]{
                toDependency(dependencyA), toDependency(dependencyB)
        });

        DataPackRegistry registry = new DataPackRegistry(new HashMap<UUID, APIDataPack>(){
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
    public void shouldFailWithCircularDependencies() {

        // rootDataPack depends on innerDependency which depends on rootDataPack
        MockDataPack innerDependency = new MockDataPack();
        APIDataPack rootDataPack = new MockDataPack(new DataPackManifestObject.Dependency[]{ toDependency(innerDependency) });
        innerDependency.getManifest().setDependencies(new DataPackManifestObject.Dependency[]{ toDependency(rootDataPack) });

        DataPackRegistry registry = new DataPackRegistry(new HashMap<UUID, APIDataPack>(){
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

    private static DataPackManifestObject.Dependency toDependency(APIDataPack dataPack) {
        return new DataPackManifestObject.Dependency(dataPack.getManifest().getUuid(), dataPack.getManifest().getVersion());
    }


    private static class MockDataPack implements APIDataPack {

        private final MockManifestObject manifest;


        public MockDataPack() {
            this(new DataPackManifestObject.Dependency[0]);
        }

        public MockDataPack(DataPackManifestObject.Dependency[] dependencies) {
            this.manifest = new MockManifestObject(UUID.randomUUID(), 1, "", "", "", 1, dependencies);
        }

        @Override
        public void register() throws IOException {

        }

        @Override
        public void unregister() {

        }

        @Override
        public MockManifestObject getManifest() {
            return this.manifest;
        }

    }

    public static class MockManifestObject extends DataPackManifestObject {

        private Dependency[] dependencies;

        protected MockManifestObject(UUID uuid, int manifestVersion, String name, String description, String author, int version, Dependency[] dependencies) {
            super(uuid, manifestVersion, name, description, author, version, dependencies);
            this.dependencies = dependencies;
        }

        public void setDependencies(Dependency[] dependencies) {
            this.dependencies = dependencies;
        }

        @Override
        public Dependency[] getDependencies() {
            return this.dependencies;
        }
    }

}
