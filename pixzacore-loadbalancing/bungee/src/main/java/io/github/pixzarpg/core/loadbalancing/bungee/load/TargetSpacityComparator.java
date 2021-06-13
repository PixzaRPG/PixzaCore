package io.github.pixzarpg.core.loadbalancing.bungee.load;

import java.util.Comparator;
import java.util.Map;

public class TargetSpacityComparator implements Comparator<Map.Entry<String, Double>> {

    /**
     * float between 0-1 that describes how full servers should be
     */
    private final double targetSpacity;

    public TargetSpacityComparator(double targetSpacity) {
        this.targetSpacity = targetSpacity;
    }

    @Override
    public int compare(Map.Entry<String, Double> serverA, Map.Entry<String, Double> serverB) {
        // In the case that both servers exceed the target space desired
        if (serverA.getValue() >= targetSpacity && serverB.getValue() >= targetSpacity) {
            // Sort it by which server is the least full
            if (serverA.getValue() == serverB.getValue()) {
                // either or is okay
                return 0;
            } else if (serverA.getValue() > serverB.getValue()) {
                // serverB is the better choice
                return 1;
            } else {
                // serverA is the better choice
                return -1;
            }
        }

        // In the case that only one of the servers are above the spacity, go for the other server
        if (serverA.getValue() >= targetSpacity) {
            // serverB is the better choice
            return 1;
        } else if (serverB.getValue() >= targetSpacity) {
            // serverA is the better choice
            return -1;
        }

        // Sort by which is the closest to the target spacity.
        double distanceA = this.targetSpacity - serverA.getValue();
        double distanceB = this.targetSpacity - serverB.getValue();

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
