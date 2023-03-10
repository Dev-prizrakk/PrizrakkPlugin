package com.prizrakk.prizrakkplugin.commands;

import com.prizrakk.prizrakkplugin.config.MessageConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NightCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String prefix = ChatColor.translateAlternateColorCodes('&', MessageConfig.get().getString("message.system.prefix"));
        String night = ChatColor.translateAlternateColorCodes('&', MessageConfig.get().getString("message.time.night"));
        String noconsole = ChatColor.translateAlternateColorCodes('&', MessageConfig.get().getString("message.system.no-console"));
        String noperm = ChatColor.translateAlternateColorCodes('&', MessageConfig.get().getString("message.system.no-perm"));
        if (!(sender instanceof Player)) {
            sender.sendMessage(prefix + noconsole);
            return true;
        }
        Player player = (Player) sender;
        World a = Bukkit.getWorld(args[0]);
        if (!player.hasPermission("prizrakk.time.night") || !sender.hasPermission("prizrakk.*")) {
            sender.sendMessage(prefix + noperm);
            return true;
        }
        if (args[0].equalsIgnoreCase("world")) {
            a.setTime(13000);
            sender.sendMessage(prefix + night);
        }
        sender.sendMessage(prefix + ChatColor.RED + "/night world ");
        return true;
    }
}