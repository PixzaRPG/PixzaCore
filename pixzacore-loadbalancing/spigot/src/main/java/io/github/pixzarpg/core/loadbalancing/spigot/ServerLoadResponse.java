package io.github.pixzarpg.core.loadbalancing.spigot;

/**
 * Represents the JSON data retrieved from the game servers regarding player count
 */
public class ServerLoadResponse {

    private final String serverId;

    /**
     * 0-1 double on how full the server is
     * -1 if the server is not available.
     */
    private final double spacity;


    public ServerLoadResponse(String serverId, double spacity) {
        this.serverId = serverId;
        this.spacity = spacity;
    }

}
