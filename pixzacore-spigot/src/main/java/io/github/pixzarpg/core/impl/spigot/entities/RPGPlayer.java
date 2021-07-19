package io.github.pixzarpg.core.impl.spigot.entities;

import org.bukkit.entity.Player;

public class RPGPlayer extends RPGEntity {

    private final Player player;

    public RPGPlayer(Player player) {
        super(player);
        this.player = player;
    }

    public Player getPlayer() {
        return this.player;
    }

}
