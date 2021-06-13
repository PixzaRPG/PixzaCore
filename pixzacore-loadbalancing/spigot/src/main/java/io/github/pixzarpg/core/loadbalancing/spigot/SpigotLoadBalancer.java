package io.github.pixzarpg.core.loadbalancing.spigot;

import com.google.gson.Gson;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;

import java.util.logging.Level;

public class SpigotLoadBalancer extends JavaPlugin implements Listener {

    // Broadcasts player count channel
    private final static String REDIS_PLAYER_COUNT_CHANNEL = "pixza_loadbalancing_playercount";

    private final static Gson GSON = new Gson();

    private JedisPool connectionPool;

    private String serverId;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();

        SpigotLoadBalancerConfig config = new SpigotLoadBalancerConfig(this.getConfig());

        this.serverId = config.getServerId();

        this.connectionPool = new JedisPool(
                config.getRedisHost(),
                config.getRedisPort(),
                config.getRedisUsername(),
                config.getRedisPassword());

        // In the case scenario we had to restart the game server without shutting down the bungees
        this.publishPlayerCount(this.getCurrentServerSpacity());
    }

    @Override
    public void onDisable() {
        try {
            this.publishPlayerCount(-1);
            this.connectionPool.close();
        } catch (JedisException exception) {
            this.getLogger().log(Level.SEVERE, "Failed to disable load balancer.", exception);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(PlayerJoinEvent event) {
        this.getServer().getScheduler().runTaskAsynchronously(this, () -> {
           try {
                this.publishPlayerCount(this.getCurrentServerSpacity());
           } catch (JedisException exception) {
               this.getLogger().log(Level.WARNING, "Failed to publish current player count", exception);
           }
        });
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        this.getServer().getScheduler().runTaskAsynchronously(this, () -> {
            try {
                this.publishPlayerCount(this.getCurrentServerSpacity());
            } catch (JedisException exception) {
                this.getLogger().log(Level.WARNING, "Failed to publish current player count", exception);
            }
        });
    }

    /**
     * Retrieve how full the server is
     * @return double from 0-1 indicating how full the server is
     */
    private double getCurrentServerSpacity() {
        return (double)this.getServer().getOnlinePlayers().size() / this.getServer().getMaxPlayers();
    }

    private void publishPlayerCount(double spacity) {
        try (Jedis jedis = this.connectionPool.getResource()) {
            jedis.publish(REDIS_PLAYER_COUNT_CHANNEL, GSON.toJson(new ServerLoadResponse(this.serverId, spacity)));
        }
    }

}
