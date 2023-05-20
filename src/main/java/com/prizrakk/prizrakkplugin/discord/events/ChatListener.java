package com.prizrakk.prizrakkplugin.discord.events;

import com.prizrakk.prizrakkplugin.PrizrakkPlugin;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;


import java.util.Random;
import java.awt.*;




public class ChatListener implements Listener {
    private final PrizrakkPlugin plugin;

    public ChatListener(PrizrakkPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player player = e.getPlayer();
        Random random = new Random();
        float r = random.nextFloat();
        float g = random.nextFloat();
        float b = random.nextFloat();
        Color randomColor = new Color(r, g, b);
        String skin_head = plugin.getConfig().getString("config.discord.skin-head-url").replace("%uuid%", "" + player.getUniqueId()).replace("%nickname%", player.getName());

        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(randomColor);
        embed.setAuthor(player.getName() ,null , skin_head);
        embed.setDescription(e.getMessage());
        embed.setFooter("Powered by prizrakk-team");


        TextChannel channel = PrizrakkPlugin.getJda().getTextChannelById(plugin.getConfig().getString("config.discord.chat"));
        channel.sendMessageEmbeds(embed.build()).queue();
    }

}
