package com.prizrakk.prizrakkplugin.events;

import com.prizrakk.prizrakkplugin.PrizrakkPlugin;
import com.prizrakk.prizrakkplugin.config.MessageConfig;
import com.prizrakk.prizrakkplugin.config.PrefixConfig;
import com.prizrakk.prizrakkplugin.db.Database;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.awt.*;
import java.sql.SQLException;
import java.util.Date;
import java.util.Random;


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
            player.kickPlayer(ChatColor.translateAlternateColorCodes('&', MessageConfig.get().getString("message.reason.warncount")).replace("%warncount%", MessageConfig.get().getString("config.warncount")));
        }
        String prefix = playerStats.getPrefix();
        String message = ChatColor.translateAlternateColorCodes('&', MessageConfig.get().getString("message.event.player-join")).replace("%prefix%", PrefixConfig.get().getString( prefix + ".prefix")).replace("%player%", player.getName());
        e.setJoinMessage(message);

        Random random = new Random();
        float r = random.nextFloat();
        float g = random.nextFloat();
        float b = random.nextFloat();
        Color randomColor = new Color(r, g, b);
        String skin_head = plugin.getConfig().getString("config.discord.skin-head-url").replace("%uuid%", "" + player.getUniqueId()).replace("%nickname%", player.getName());

        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(randomColor);
        embed.setAuthor(player.getName() ,null , skin_head);
        embed.setDescription(MessageConfig.get().getString("message.discord.events.left").replace("%prefix%", PrefixConfig.get().getString( prefix + ".prefix-discord")).replace("%player%", player.getName()));
        embed.setFooter("Powered by prizrakk-team");


        TextChannel channel = PrizrakkPlugin.getJda().getTextChannelById(plugin.getConfig().getString("config.discord.chat"));
        channel.sendMessageEmbeds(embed.build()).queue();
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
        String prefix = playerStats.getPrefix();
        String message = ChatColor.translateAlternateColorCodes('&', MessageConfig.get().getString("message.event.player-left")).replace("%prefix%", PrefixConfig.get().getString( prefix + ".prefix")).replace("%player%", player.getName());
        e.setQuitMessage(message);

        Random random = new Random();
        float r = random.nextFloat();
        float g = random.nextFloat();
        float b = random.nextFloat();
        Color randomColor = new Color(r, g, b);
        String skin_head = plugin.getConfig().getString("config.discord.skin-head-url").replace("%uuid%", "" + player.getUniqueId()).replace("%nickname%", player.getName());

        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(randomColor);
        embed.setAuthor(player.getName() ,null , skin_head);
        embed.setDescription(MessageConfig.get().getString("message.discord.events.left").replace("%prefix%", PrefixConfig.get().getString( prefix + ".prefix-discord")).replace("%player%", player.getName()));
        embed.setFooter("Powered by prizrakk-team");


        TextChannel channel = PrizrakkPlugin.getJda().getTextChannelById(plugin.getConfig().getString("config.discord.chat"));
        channel.sendMessageEmbeds(embed.build()).queue();
    }
}
