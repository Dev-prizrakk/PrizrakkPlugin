package com.prizrakk.prizrakkplugin.handler;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.time.LocalDateTime;

public class SimpleEventHandler implements Listener {
    private final JavaPlugin plugin;
    /*
    Тестовая фича и она не запущена!
     */
    public SimpleEventHandler(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void handleBlockBreakEvent(BlockBreakEvent e) {
        if (e.getBlock().getType() == Material.DIAMOND_ORE) {
            e.setCancelled(true);
            e.getBlock().setType(Material.AIR);
            ItemStack diamonds = new ItemStack(Material.DIAMOND, 2);
            Location blockLocation = e.getBlock().getLocation();
            e.getBlock().getWorld().dropItemNaturally(blockLocation, diamonds);
        }
    }

    @EventHandler
    public void handlerJoinEvent(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        String uuid = player.getUniqueId().toString();
        String path = String.join(".", "users", uuid, "last-join");
        LocalDateTime currenTime = LocalDateTime.now();
        plugin.getConfig().set(path, currenTime.toString());
        plugin.saveConfig();
    }
}
