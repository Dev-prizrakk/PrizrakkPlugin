package com.prizrakk.prizrakkplugin.Discord.commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.util.stream.Collectors;

public class ServerInfoCommand implements MessageCreateListener {

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        // Ignore any message that is not !whoIsOnline
        if (!event.getMessageContent().equalsIgnoreCase("!server")) {
            return;
        }

        event.getChannel().sendMessage("**TPS**: " + Bukkit.getTicksPerMonsterSpawns() + ", " + Bukkit.getTicksPerAnimalSpawns()
                + "\n" + "**Players**: " + Bukkit.getOnlinePlayers()
                + "\n" + "**Motd**: " + Bukkit.getMotd());
    }
}