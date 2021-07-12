package io.github.pixzarpg.core.impl.spigot.nms;

import io.github.pixzarpg.core.impl.spigot.nms.versions.NMSRPGVersion;
import io.github.pixzarpg.core.impl.spigot.nms.versions.v1_16_5_R01.NMSRPGVersionV1_16_5_R01;
import org.bukkit.Bukkit;

public class NMSRPGVersionMapper {

    public static NMSRPGVersion getVersion() {
        switch (Bukkit.getBukkitVersion()) {
            case "1.16.5-R0.1-SNAPSHOT":
                return NMSRPGVersionV1_16_5_R01.INSTANCE;
            default:
                throw new UnsupportedOperationException("Was unable to find NMSRPGVersion that supports " + Bukkit.getBukkitVersion());
        }
    }

}
