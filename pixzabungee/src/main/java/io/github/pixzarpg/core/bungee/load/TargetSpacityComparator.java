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
        return 0;
    }

}
