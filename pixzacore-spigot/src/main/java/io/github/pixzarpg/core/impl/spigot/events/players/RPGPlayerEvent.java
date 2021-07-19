package io.github.pixzarpg.core.impl.spigot.events.players;

import io.github.pixzarpg.core.impl.spigot.entities.RPGPlayer;
import org.bukkit.event.Event;

public abstract class RPGPlayerEvent extends Event {

    private final RPGPlayer rpgPlayer;


    public RPGPlayerEvent(RPGPlayer who) {
        this.rpgPlayer = who;
    }

    public RPGPlayer getRPGPlayer() {
        return this.rpgPlayer;
    }

}
