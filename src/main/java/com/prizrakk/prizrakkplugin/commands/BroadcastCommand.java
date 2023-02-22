package com.prizrakk.prizrakkplugin.commands;

import com.prizrakk.prizrakkplugin.config.MessageConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class BroadcastCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String noperm = ChatColor.translateAlternateColorCodes('&', MessageConfig.get().getString("message.system.noperm"));
        String prefix = ChatColor.translateAlternateColorCodes('&', MessageConfig.get().getString("message.system.prefix"));
        if (!sender.hasPermission("prizrakk.bc")) {
            sender.sendMessage(prefix + noperm);
        }
        if (command.getName().equalsIgnoreCase("bc")) {
            String message = String.join(" ", args);
            Bukkit.broadcastMessage("§6[§2INFO§6] §f" + message);
        }
        return true;
    }
}
