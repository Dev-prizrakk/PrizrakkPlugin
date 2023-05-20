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

import static com.prizrakk.prizrakkplugin.PrizrakkPlugin.chanel_id;


public class ChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        Player player = e.getPlayer();
        Random random = new Random();
        float r = random.nextFloat();
        float g = random.nextFloat();
        float b = random.nextFloat();
        Color randomColor = new Color(r, g, b);

        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(randomColor);
        embed.setAuthor(player.getName() ,null , null);
        embed.setDescription(player.getName() + " >> "+e.getMessage());
        embed.setFooter("Powered by prizrakk-team");


        TextChannel channel = PrizrakkPlugin.getJda().getTextChannelById(chanel_id);
        channel.sendMessageEmbeds(embed.build()).queue();
    }

}
