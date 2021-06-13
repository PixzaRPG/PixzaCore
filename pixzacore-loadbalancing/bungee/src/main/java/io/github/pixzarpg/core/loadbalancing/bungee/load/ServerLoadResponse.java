package io.github.pixzarpg.core.loadbalancing.bungee.load;

/**
 * Represents the JSON data retrieved from the game servers regarding player count
 */
public class ServerLoadResponse {

    private final String serverId;

    /**
     * 0-1 double on how full the server is
     */
    private final double spacity;


    public ServerLoadResponse(String serverId, double spacity) {
        this.serverId = serverId;
        this.spacity = spacity;
    }

    public String getServerId() {
        return this.serverId;
    }

    public double getSpacity() {
        return this.spacity;
    }

}
