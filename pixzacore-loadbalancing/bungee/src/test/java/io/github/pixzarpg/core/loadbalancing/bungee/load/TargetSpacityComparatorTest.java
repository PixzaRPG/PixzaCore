package io.github.pixzarpg.core.loadbalancing.bungee.load;

import org.junit.jupiter.api.Test;
import redis.clients.jedis.Tuple;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class TargetSpacityComparatorTest {

    @Test
    public void shouldOrderOverfilledServersProperly() {
        float targetSpacity = 0.5f;
        Map.Entry<String, Double> unfilledServer = new AbstractMap.SimpleEntry<>("underfilled", 0d);
        Map.Entry<String, Double> fullServer = new AbstractMap.SimpleEntry<>("full", 0.5d);
        Map.Entry<String, Double> overFilledServer = new AbstractMap.SimpleEntry<>("overfilled", 0.8d);

        Map.Entry[] inputData = {
            overFilledServer,
            unfilledServer,
            fullServer
        };

        // Closest unfilled server and than closest full servers
        Map.Entry[] correctOutput = {
            unfilledServer,
            fullServer,
            overFilledServer
        };

        Map.Entry<String, Double>[] output = Arrays.stream((Map.Entry<String, Double>[])inputData)
                .sorted(new TargetSpacityComparator(targetSpacity))
                .toArray(Map.Entry[]::new);
        assertArrayEquals(correctOutput, output);
    }

    @Test
    public void shouldOrderUnfilledServersProperly() {
        float targetSpacity = 0.5f;
        Map.Entry<String, Double> serverA = new AbstractMap.SimpleEntry<>("a", 0.1d);
        Map.Entry<String, Double> serverB = new AbstractMap.SimpleEntry<>("a", 0.2d);
        Map.Entry<String, Double> serverC = new AbstractMap.SimpleEntry<>("a", 0.3d);
        Map.Entry<String, Double> serverD = new AbstractMap.SimpleEntry<>("a", 0.4d);

        Map.Entry[] inputData = {
            serverD,
            serverB,
            serverA,
            serverC
        };

        // Closest unfilled server first
        Map.Entry[] correctOutput = {
            serverD,
            serverC,
            serverB,
            serverA
        };

        Map.Entry[] output = Arrays.stream((Map.Entry<String, Double>[])inputData)
            .sorted(new TargetSpacityComparator(targetSpacity))
            .toArray(Map.Entry[]::new);
        assertArrayEquals(correctOutput, output);
    }

}
