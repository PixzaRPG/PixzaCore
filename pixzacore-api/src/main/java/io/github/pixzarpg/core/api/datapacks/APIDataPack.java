package io.github.pixzarpg.core.api.datapacks;

import io.github.pixzarpg.core.datapacks.api.DataPackManifestObject;

import java.io.IOException;

public interface APIDataPack {

    /**
     * Read contents of data pack and register it to the server
     * @throws IOException
     */
    void register() throws IOException;

    /**
     * Remove contents of this datapack from the server
     */
    void unregister();

    /**
     * Information about the datapack
     * @return
     */
    DataPackManifestObject getManifest();

}
