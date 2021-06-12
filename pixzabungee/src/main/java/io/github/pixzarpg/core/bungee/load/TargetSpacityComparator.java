package io.github.pixzarpg.core.bungee.load;

import redis.clients.jedis.Tuple;

import java.util.Comparator;

public class TargetSpacityComparator implements Comparator<Tuple> {

    /**
     * float between 0-1 that describes how full servers should be
     */
    private final float targetSpacity;

    public TargetSpacityComparator(float targetSpacity) {
        this.targetSpacity = targetSpacity;
    }

    @Override
    public int compare(Tuple serverA, Tuple serverB) {
        // In the case that both servers exceed the target space desired
        if (serverA.getScore() >= targetSpacity && serverB.getScore() >= targetSpacity) {
            // Sort it by which server is the least full
            if (serverA.getScore() == serverB.getScore()) {
                // either or is okay
                return 0;
            } else if (serverA.getScore() > serverB.getScore()) {
                // serverB is the better choice
                return 1;
            } else {
                // serverA is the better choice
                return -1;
            }
        }

        // In the case that only one of the servers are above the spacity, go for the other server
        if (serverA.getScore() >= targetSpacity) {
            // serverB is the better choice
            return 1;
        } else if (serverB.getScore() >= targetSpacity) {
            // serverA is the better choice
            return -1;
        }

        // Sort by which is the closest to the target spacity.
        float distanceA = this.targetSpacity - (float)serverA.getScore();
        float distanceB = this.targetSpacity - (float)serverB.getScore();

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
