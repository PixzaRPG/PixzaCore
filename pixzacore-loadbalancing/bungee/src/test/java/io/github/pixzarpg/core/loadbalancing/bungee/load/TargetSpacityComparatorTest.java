package io.github.pixzarpg.core.loadbalancing.bungee.load;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class TargetSpacityComparatorTest {

    @Test
    public void shouldOrderOverfilledServersProperly() {
        float targetSpacity = 0.5f;
        ServerLoadResponse unfilledServer = new ServerLoadResponse("underfilled", 0d);
        ServerLoadResponse fullServer = new ServerLoadResponse("full", 0.5d);
        ServerLoadResponse overFilledServer = new ServerLoadResponse("overfilled", 0.8d);

        ServerLoadResponse[] inputData = {
            overFilledServer,
            unfilledServer,
            fullServer
        };

        // Closest unfilled server and than closest full servers
        ServerLoadResponse[] correctOutput = {
            unfilledServer,
            fullServer,
            overFilledServer
        };

        ServerLoadResponse[] output = Arrays.stream(inputData)
                .sorted(new TargetSpacityComparator(targetSpacity))
                .toArray(ServerLoadResponse[]::new);
        assertArrayEquals(correctOutput, output);
    }

    @Test
    public void shouldOrderUnfilledServersProperly() {
        float targetSpacity = 0.5f;
        ServerLoadResponse serverA = new ServerLoadResponse("a", 0.1d);
        ServerLoadResponse serverB = new ServerLoadResponse("a", 0.2d);
        ServerLoadResponse serverC = new ServerLoadResponse("a", 0.3d);
        ServerLoadResponse serverD = new ServerLoadResponse("a", 0.4d);

        ServerLoadResponse[] inputData = {
            serverD,
            serverB,
            serverA,
            serverC
        };

        // Closest unfilled server first
        ServerLoadResponse[] correctOutput = {
            serverD,
            serverC,
            serverB,
            serverA
        };

        ServerLoadResponse[] output = Arrays.stream(inputData)
            .sorted(new TargetSpacityComparator(targetSpacity))
            .toArray(ServerLoadResponse[]::new);
        assertArrayEquals(correctOutput, output);
    }

}
