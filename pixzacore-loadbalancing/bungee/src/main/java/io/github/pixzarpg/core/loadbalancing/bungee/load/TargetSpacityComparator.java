package io.github.pixzarpg.core.loadbalancing.bungee.load;

import java.util.Comparator;
import java.util.Map;

public class TargetSpacityComparator implements Comparator<ServerLoadResponse> {

    /**
     * float between 0-1 that describes how full servers should be
     */
    private final double targetSpacity;

    public TargetSpacityComparator(double targetSpacity) {
        this.targetSpacity = targetSpacity;
    }

    @Override
    public int compare(ServerLoadResponse serverA, ServerLoadResponse serverB) {
        // In the case that both servers exceed the target space desired
        if (serverA.getSpacity() >= targetSpacity && serverB.getSpacity() >= targetSpacity) {
            // Sort it by which server is the least full
            if (serverA.getSpacity() == serverB.getSpacity()) {
                // either or is okay
                return 0;
            } else if (serverA.getSpacity() > serverB.getSpacity()) {
                // serverB is the better choice
                return 1;
            } else {
                // serverA is the better choice
                return -1;
            }
        }

        // In the case that only one of the servers are above the spacity, go for the other server
        if (serverA.getSpacity() >= targetSpacity) {
            // serverB is the better choice
            return 1;
        } else if (serverB.getSpacity() >= targetSpacity) {
            // serverA is the better choice
            return -1;
        }

        // Sort by which is the closest to the target spacity.
        double distanceA = this.targetSpacity - serverA.getSpacity();
        double distanceB = this.targetSpacity - serverB.getSpacity();

        if (distanceA == distanceB) {
            // either or is okay
            return 0;
        } else if (distanceA > distanceB) {
            // serverB is the better choice
            return 1;
        } else {
            // serverA is the better choice
            return -1;
        }

    }

}
