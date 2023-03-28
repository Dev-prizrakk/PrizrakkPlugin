package com.prizrakk.prizrakkplugin.handler;

import com.prizrakk.prizrakkplugin.PrizrakkPlugin;
import com.prizrakk.prizrakkplugin.db.Database;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.sql.SQLException;
import java.util.Date;


public class PlayerEvent implements Listener {
    public PlayerEvent(Database database, PrizrakkPlugin plugin) {
        this.database = database;
        this.plugin = plugin;
    }
    private final PrizrakkPlugin plugin;
    private final Database database;

    public PlayerStats getPlayerStatsFromDatabase(Player player) throws SQLException {

        PlayerStats playerStats = database.findPlayerStatsByNICK(player.getName());

        if (playerStats == null) {
            playerStats = new PlayerStats(player.getName(), 0, "default",0, 0, 0, 0,0.0, new Date(), new Date());
            database.createPlayerStats(playerStats);
        }

        return playerStats;
    }


    @EventHandler
    public void onJoinPlayer(PlayerJoinEvent e) {
        Player player = e.getPlayer();

        PlayerStats playerStats;
        try {
            playerStats = getPlayerStatsFromDatabase(player);
        } catch (SQLException s) {
            if (plugin.getConfig().getBoolean("config.debug") == true) {
                s.printStackTrace();
            }
            throw new RuntimeException(s);
        }
        int warncount = playerStats.getWarn_count();
        if (warncount == plugin.getConfig().getInt("config.warncount")) {
            player.kickPlayer(ChatColor.translateAlternateColorCodes('&', PrizrakkPlugin.getInstance().getConfig().getString("message.reason.warncount")).replace("%warncount%", plugin.getConfig().getString("config.warncount")));
        }
        String message = ChatColor.translateAlternateColorCodes('&', PrizrakkPlugin.getInstance().getConfig().getString("message.event.join")).replace("%prefix%", playerStats.getPrefix()).replace("%player%", player.getName());
        e.setJoinMessage(message);
    }
    @EventHandler
    public void onLeftPlayer(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        PlayerStats playerStats;
        try {
            playerStats = getPlayerStatsFromDatabase(player);
        } catch (SQLException s) {
            if (plugin.getConfig().getBoolean("config.debug") == true) {
                s.printStackTrace();
            }
            throw new RuntimeException(s);
        }
        String message = ChatColor.translateAlternateColorCodes('&', PrizrakkPlugin.getInstance().getConfig().getString("message.event.left")).replace("%prefix%", playerStats.getPrefix()).replace("%player%", player.getName());
        e.setQuitMessage(message);
    }
}
