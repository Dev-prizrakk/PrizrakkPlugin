package com.prizrakk.prizrakkplugin.discord.events;


import com.prizrakk.prizrakkplugin.PrizrakkPlugin;
import com.prizrakk.prizrakkplugin.config.MessageConfig;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class MessageListener extends ListenerAdapter {
    private final PrizrakkPlugin plugin;

    public MessageListener(PrizrakkPlugin plugin) {
        this.plugin = plugin;
    }
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        if(event.getChannel().equals(PrizrakkPlugin.getJda().getTextChannelById(plugin.getConfig().getString("config.discord.chat")))) {
            if (!Objects.requireNonNull(event.getMember()).getUser().isBot()) {
                String username = event.getMember().getEffectiveName();
                String message = event.getMessage().getContentRaw();
                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', MessageConfig.get().getString("message.discord.send-discord-to-minecraft").replace("%username%", username).replace("%message%", message)));
            }
        }

    }
}
