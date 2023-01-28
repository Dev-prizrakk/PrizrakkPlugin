package com.prizrakk.prizrakkplugin.handler;

import com.prizrakk.prizrakkplugin.PrizrakkPlugin;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;


public class PlayerEvent implements Listener {

    @EventHandler
    public void onJoinPlayer(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        String message = PrizrakkPlugin.getInstance().getConfig().getString("message.join");
        message = message.replace("%player%", player.getName());
        String remessage = ChatColor.translateAlternateColorCodes('&', message);
        e.setJoinMessage(remessage);
    }
    @EventHandler
    public void onLeftPlayer(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        String message = PrizrakkPlugin.getInstance().getConfig().getString("message.left");
        message = message.replace("%player%", player.getName());
        String remessage = ChatColor.translateAlternateColorCodes('&', message);
        e.setQuitMessage(remessage);
    }
}
