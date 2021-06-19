package io.github.pixzarpg.core.loadbalancing.bungee.load;

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

    public String getServerId() {
        return this.serverId;
    }

    /**
     * Retrieve how full the server is on a scale from 0-1.
     * @return How full the server is from 0-1. Will return -1 if the server is no longer available.
     */
    public double getSpacity() {
        return this.spacity;
    }

}
