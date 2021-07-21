package io.github.pixzarpg.core.impl.spigot.entities;

import io.github.pixzarpg.core.impl.spigot.RPGManager;
import io.github.pixzarpg.core.impl.spigot.utils.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class RPGEntityManager implements Listener {

    private final Map<Integer, RPGEntity> entities = new HashMap<>();

    private final RPGManager rpgManager;


    public RPGEntityManager(RPGManager rpgManager) {
        this.rpgManager = rpgManager;
        Bukkit.getServer().getPluginManager().registerEvents(this, rpgManager.getPlugin());
    }

    /**
     * Entities passed here need to be manually removed from the cache.
     * It is highly recommended you use the NMSRPGVersion.createEntity which will create entities
     * that automatically remove themselves from the cache on death/despawn.
     *
     * Player entities do not need to be removed manually as this is handled internally.
     *
     * @param entity the entity
     * @return the RPGEntity equivalent
     */
    public RPGEntity get(LivingEntity entity) {
        if (!this.entities.containsKey(entity.getEntityId())) {
            RPGEntity rpgEntity = new RPGEntity(entity);
            this.entities.put(entity.getEntityId(), rpgEntity);
        }
        return this.entities.get(entity.getEntityId());
    }

    public RPGPlayer get(Player player) {
        if (!this.entities.containsKey(player.getEntityId())) {
            RPGPlayer rpgPlayer = new RPGPlayer(player);
            this.entities.put(player.getEntityId(), rpgPlayer);
        }
        return (RPGPlayer)this.entities.get(player.getEntityId());
    }


    public void remove(RPGEntity rpgEntity) {
        this.remove(rpgEntity.getEntity());
    }

    public void remove(LivingEntity entity) {
        this.entities.remove(entity.getEntityId());
    }

    public RPGManager getRpgManager() {
        return this.rpgManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        RPGPlayer rpgPlayer = this.get(event.getPlayer());
        event.getPlayer().sendMessage(TextUtils.generatePlayerMessage("Data", "Loading RPG player data..."));
        event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Integer.MAX_VALUE, 0, false, false, false));

        rpgPlayer.loadAsync().whenComplete((firstTime, exception) -> {
            if (exception != null) {
                this.getRpgManager().getPlugin().getLogger().log(Level.SEVERE, "Failed to load RPGPlayer data for " + event.getPlayer().getUniqueId(), exception);
                event.getPlayer().kickPlayer("Failed to load RPG player data");
            } else {
                event.getPlayer().removePotionEffect(PotionEffectType.BLINDNESS);
                event.getPlayer().sendMessage(TextUtils.generatePlayerMessage("Data", "Loaded!"));
            }
        });
    }

    // Player data must be loaded before they can move around
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        RPGPlayer rpgPlayer = this.get(event.getPlayer());
        if (!rpgPlayer.isLoaded()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        RPGPlayer rpgPlayer = this.get(event.getPlayer());
        if (rpgPlayer.isLoaded()) {
            try {
                rpgPlayer.requestAsyncSave().whenComplete((ignored, exception) -> {
                    if (exception != null) {
                        this.getRpgManager().getPlugin().getLogger().log(Level.SEVERE, "Failed to save player " + event.getPlayer().getUniqueId(), exception);
                    }
                });
            } catch (IOException exception) {
                this.getRpgManager().getPlugin().getLogger().log(Level.SEVERE, "Failed to serialize inventory and save player " + event.getPlayer().getUniqueId(), exception);
            }
        }

        this.remove(event.getPlayer());
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (!(event.getEntity() instanceof Player)) {   // Players can respawn
            this.remove(event.getEntity());
        }
    }

    @EventHandler
    public void onEntityChunkUnload(ChunkUnloadEvent event) {
        for (Entity entity : event.getChunk().getEntities()) {
            if (entity instanceof LivingEntity) {
                this.remove((LivingEntity)entity);
            }
        }
    }

}
