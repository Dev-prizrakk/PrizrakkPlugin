package com.prizrakk.prizrakkplugin.Discord.event;

import com.prizrakk.prizrakkplugin.PrizrakkPlugin;
import com.prizrakk.prizrakkplugin.db.Database;
import com.prizrakk.prizrakkplugin.handler.PlayerStats;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.javacord.api.event.message.MessageCreateEvent;

import java.sql.SQLException;
import java.util.Date;


public class ChatMessage implements Listener {
    public ChatMessage(Database database, PrizrakkPlugin plugin) {
        this.database = database;
        this.plugin = plugin;
    }
    private final PrizrakkPlugin plugin;
    private final Database database;

    public PlayerStats getPlayerStatsFromDatabase(Player player) throws SQLException {

        PlayerStats playerStats = database.findPlayerStatsByNICK(player.getName());

        if (playerStats == null) {
            playerStats = new PlayerStats(player.getName(), 0, "Житель",0, 0, 0,0.0, new Date(), new Date());
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
        //sendMessage(player.getName() + event.getMessage());
        event.setFormat(playerStats.getPrefix() + " " + player.getName() + " §6>>§f " + event.getMessage());
    }

}