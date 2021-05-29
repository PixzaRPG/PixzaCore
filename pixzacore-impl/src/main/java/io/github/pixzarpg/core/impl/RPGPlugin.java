package io.github.pixzarpg.core.impl;

import org.bukkit.plugin.java.JavaPlugin;

public class RPGPlugin extends JavaPlugin {

    private RPGManager manager;


    @Override
    public void onEnable() {
        this.manager = new RPGManager(this);
    }

    @Override
    public void onDisable() {
        this.manager.close();
    }

}
