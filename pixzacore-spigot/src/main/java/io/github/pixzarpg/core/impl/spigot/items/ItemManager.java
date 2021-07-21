package io.github.pixzarpg.core.impl.spigot.items;

import io.github.pixzarpg.core.impl.spigot.RPGManager;
import io.github.pixzarpg.core.impl.spigot.entities.RPGPlayer;
import io.github.pixzarpg.core.impl.spigot.events.players.items.RPGPlayerItemInteractBlockEvent;
import io.github.pixzarpg.core.impl.spigot.events.players.items.RPGPlayerItemInteractEntityEvent;
import io.github.pixzarpg.core.impl.spigot.items.components.ItemComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ItemManager implements Listener {

    private final RPGManager rpgManager;

    private final Map<UUID, RPGItemType> data = new HashMap<>();
    private final Map<ItemStack, RPGItem> activeItemsCache = new HashMap<>();


    public ItemManager(RPGManager rpgManager) {
        this.rpgManager = rpgManager;
        Bukkit.getServer().getPluginManager().registerEvents(this, this.rpgManager.getPlugin());
    }

    public RPGManager getRPGManager() {
        return this.rpgManager;
    }

    public void register(RPGItemType itemType) {
        this.data.put(itemType.getUuid(), itemType);
    }

    public RPGItemType getType(UUID itemUuid) {
        return this.data.get(itemUuid);
    }

    public boolean hasType(UUID itemUuid) {
        return this.data.containsKey(itemUuid);
    }

    /**
     * Called internally to store the item object in the item object cache
     * @param item the RPGItem
     */
    public void registerActiveItem(RPGItem item) {
        this.activeItemsCache.put(item.getItemStack(), item);
    }

    public void unregisterActiveItem(RPGItem item) {
        this.activeItemsCache.remove(item.getItemStack());
    }

    /**
     * Retrieve the RPGItem equivalent of an item stack.
     * If none exist in the cache, it will create a new object.
     *
     * This method should ONLY be called for items that are currently in-play in the world (e.g. item entities or items in inventories)
     * because this calls registerActiveItem
     * @param itemStack
     * @return
     */
    public RPGItem getRPGItem(ItemStack itemStack) {
        if (this.activeItemsCache.containsKey(itemStack)) {
            return this.activeItemsCache.get(itemStack);
        }
        // Create a new RPGItem instance since it's not cached

        NamespacedKey itemTypeKey = new NamespacedKey(this.getRPGManager().getPlugin(), RPGItem.RPG_ITEM_TYPE_KEY);

        if (
                (itemStack.getType() == Material.AIR) ||
                (itemStack.getItemMeta().getPersistentDataContainer() == null) ||
                !(itemStack.getItemMeta().getPersistentDataContainer().has(itemTypeKey, PersistentDataType.STRING))
        ) {
            return null;
        }

        UUID itemTypeUuid = UUID.fromString(itemStack.getItemMeta().getPersistentDataContainer().get(itemTypeKey, PersistentDataType.STRING));
        if (!this.hasType(itemTypeUuid)) {
            this.getRPGManager().getPlugin().getLogger().warning("Failed to retrieve item by the UUID of " + itemTypeUuid + ". Is a data pack missing?");
            return null;
        }
        RPGItem item = new RPGItem(this.getType(itemTypeUuid), itemStack);
        this.registerActiveItem(item);

        return item;
    }

    // Cleanup: Remove RPGItems in a player's inventory from cache on quit
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        for (ItemStack itemStack : event.getPlayer().getInventory()) {
            if (itemStack != null) {
                RPGItem rpgItem = this.getRPGItem(itemStack);
                if (rpgItem != null) {
                    this.unregisterActiveItem(rpgItem);
                }
            }
        }
    }

    // Cleanup: Remove RPGItem from cache on rpg item dropped
    // Additionally, this handles checking an item's components if the item can be dropped.
    @EventHandler
    public void onItemDropped(EntityDropItemEvent event) {
        RPGItem rpgItem = this.getRPGItem(event.getItemDrop().getItemStack());
        if (rpgItem != null) {
            for (ItemComponent component : rpgItem.getItemType().getComponents()) {
                if (!component.canLose()) {
                    event.setCancelled(true);
                    return;
                }
            }

            // It's okay to drop this item. Remove it from the cache
            this.unregisterActiveItem(rpgItem);
        }
    }

    // Cleanup: Remove RPGItem from cache on moving to diff inventory
    @EventHandler
    public void onMoveRPGItemToDiffInventory(InventoryMoveItemEvent event) {
        if (!event.getDestination().equals(event.getInitiator()) && !((event.getDestination() instanceof PlayerInventory) || (event.getDestination() instanceof CraftingInventory))) {
            RPGItem rpgItem = this.getRPGItem(event.getItem());
            if (rpgItem != null) {
                this.unregisterActiveItem(rpgItem);
            }
        }
    }

    // Call component's block interact for RPGItems
    @EventHandler
    public void onPlayerBlockInteract(PlayerInteractEvent event) {
        if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            ItemStack heldItem = event.getItem();
            if (heldItem != null) {
                RPGItem rpgItem = this.getRPGItem(heldItem);
                if (rpgItem != null) {
                    RPGPlayer rpgPlayer = this.getRPGManager().getEntityManager().get(event.getPlayer());
                    RPGPlayerItemInteractBlockEvent rpgEvent = new RPGPlayerItemInteractBlockEvent(rpgPlayer, rpgItem, event.getClickedBlock());
                    Bukkit.getPluginManager().callEvent(rpgEvent);
                    if (rpgEvent.isCancelled()) {
                        event.setCancelled(true);
                        return;
                    }

                    for (ItemComponent component : rpgItem.getItemType().getComponents()) {
                        component.onItemBlockInteract(event.getPlayer(), event.getClickedBlock(), rpgItem);
                    }
                }
            }
        }
    }

    // Call component's entity interact for RPGItems
    @EventHandler
    public void onPlayerEntityInteract(PlayerInteractEntityEvent event) {
        ItemStack heldItem = event.getPlayer().getInventory().getItemInMainHand();
        RPGItem rpgItem = this.getRPGItem(heldItem);
        if (rpgItem != null) {
            RPGPlayer rpgPlayer = this.getRPGManager().getEntityManager().get(event.getPlayer());
            RPGPlayerItemInteractEntityEvent rpgEvent = new RPGPlayerItemInteractEntityEvent(rpgPlayer, rpgItem, event.getRightClicked());
            Bukkit.getPluginManager().callEvent(rpgEvent);
            if (rpgEvent.isCancelled()) {
                event.setCancelled(true);
                return;
            }

            for (ItemComponent component : rpgItem.getItemType().getComponents()) {
                component.onItemEntityInteract(event.getPlayer(), event.getRightClicked(), rpgItem);
            }
        }
    }

    // Call component's attack for RPGItems
    @EventHandler
    public void onItemAttack(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof LivingEntity) {
            LivingEntity attacker = (LivingEntity)event.getDamager();
            if (attacker.getEquipment() != null) {
                RPGItem rpgItem = this.getRPGItem(attacker.getEquipment().getItem(EquipmentSlot.HAND));
                if (rpgItem != null) {
                    for (ItemComponent component : rpgItem.getItemType().getComponents()) {
                        component.onItemAttack(attacker, event.getEntity(), rpgItem);
                    }

                }
            }
        }
    }

    // Call component's consumption for RPGItems
    @EventHandler
    public void onItemUsage(PlayerItemConsumeEvent event) {
        RPGItem rpgItem = this.getRPGItem(event.getItem());
        if (rpgItem != null) {
            for (ItemComponent component : rpgItem.getItemType().getComponents()) {
                component.onItemConsumption(event.getPlayer(), rpgItem);
            }
        }
    }

}
