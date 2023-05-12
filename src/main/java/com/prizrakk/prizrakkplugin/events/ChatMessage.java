package com.prizrakk.prizrakkplugin.events;

import com.prizrakk.prizrakkplugin.PrizrakkPlugin;
import com.prizrakk.prizrakkplugin.config.PrefixConfig;
import com.prizrakk.prizrakkplugin.db.Database;
import com.prizrakk.prizrakkplugin.events.PlayerStats;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.GloballyAttachableListener;
import org.javacord.api.listener.message.MessageCreateListener;

import java.sql.SQLException;
import java.sql.Struct;
import java.util.Date;


public class ChatMessage implements Listener, GloballyAttachableListener {
    public ChatMessage(Database database, PrizrakkPlugin plugin) {
        this.database = database;
        this.plugin = plugin;
    }
    private final PrizrakkPlugin plugin;
    private final Database database;

    public PlayerStats getPlayerStatsFromDatabase(Player player) throws SQLException {

        PlayerStats playerStats = database.findPlayerStatsByNICK(player.getName());

        if (playerStats == null) {
            playerStats = new PlayerStats(player.getName(), 0, "default", 0,0, 0, 0,0.0, new Date(), new Date());
            database.createPlayerStats(playerStats);
        }

        return playerStats;
    }

    @EventHandler
    public void ChatMeneger(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        PlayerStats playerStats;
        try {
            playerStats = getPlayerStatsFromDatabase(player);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String prefix = playerStats.getPrefix();
        String message = ChatColor.translateAlternateColorCodes('&' , PrefixConfig.get().getString(prefix + ".chat-format"))
                .replace("%prefix%", PrefixConfig.get().getString( prefix + ".prefix"))
                .replace("%player%", player.getName())
                .replace("%message%", event.getMessage());
        event.setFormat(message);
    }
}