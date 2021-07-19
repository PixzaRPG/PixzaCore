package io.github.pixzarpg.core.impl.spigot.entities;

import io.github.pixzarpg.core.impl.spigot.RPGManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

import java.util.HashMap;
import java.util.Map;

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
    public void onPlayerQuit(PlayerQuitEvent event) {
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
