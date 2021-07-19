package io.github.pixzarpg.core.impl.spigot;

import org.bukkit.plugin.java.JavaPlugin;

public class RPGPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        RPGConfig config = new RPGConfig(this.getConfig());
        RPGManager.create(config, this);
    }

    @Override
    public void onDisable() {

    }

}
