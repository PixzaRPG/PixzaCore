package io.github.pixzarpg.core.bungee.load;

import org.junit.jupiter.api.Test;
import redis.clients.jedis.Tuple;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class TargetSpacityComparatorTest {

    @Test
    public void shouldOrderOverfilledServersProperly() {
        float targetSpacity = 0.5f;
        Tuple unfilledServer = new Tuple("underfilled", 0d);
        Tuple fullServer = new Tuple("full", 0.5d);
        Tuple overFilledServer = new Tuple("overfilled", 0.8d);

        Tuple[] inputData = {
            overFilledServer,
            unfilledServer,
            fullServer
        };

        // Closest unfilled server and than closest full servers
        Tuple[] correctOutput = {
            unfilledServer,
            fullServer,
            overFilledServer
        };

        Tuple[] output = Arrays.stream(inputData)
                .sorted(new TargetSpacityComparator(targetSpacity))
                .toArray(Tuple[]::new);
        assertArrayEquals(correctOutput, output);
    }

    @Test
    public void shouldOrderUnfilledServersProperly() {
        float targetSpacity = 0.5f;
        Tuple serverA = new Tuple("a", 0.1d);
        Tuple serverB = new Tuple("a", 0.2d);
        Tuple serverC = new Tuple("a", 0.3d);
        Tuple serverD = new Tuple("a", 0.4d);

        Tuple[] inputData = {
            serverD,
            serverB,
            serverA,
            serverC
        };

        // Closest unfilled server first
        Tuple[] correctOutput = {
            serverD,
            serverC,
            serverB,
            serverA
        };

        Tuple[] output = Arrays.stream(inputData)
            .sorted(new TargetSpacityComparator(targetSpacity))
            .toArray(Tuple[]::new);
        assertArrayEquals(correctOutput, output);
    }

}
