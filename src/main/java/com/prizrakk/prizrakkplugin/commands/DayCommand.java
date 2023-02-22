package com.prizrakk.prizrakkplugin.commands;

import com.prizrakk.prizrakkplugin.config.MessageConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DayCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String prefix = ChatColor.translateAlternateColorCodes('&', MessageConfig.get().getString("message.system.prefix"));
        String day = ChatColor.translateAlternateColorCodes('&', MessageConfig.get().getString("message.time.day"));
        String noconsole = ChatColor.translateAlternateColorCodes('&', MessageConfig.get().getString("message.system.noconsole"));
        String noperm = ChatColor.translateAlternateColorCodes('&', MessageConfig.get().getString("message.system.noperm"));

        if (!(sender instanceof Player)) {
            sender.sendMessage(prefix + noconsole);
            return true;
        }

        Player player = (Player) sender;
        World a = Bukkit.getWorld(args[0]);
        if (!player.hasPermission("prizrakk.time.day") || !sender.hasPermission("prizrakk.*")) {
            sender.sendMessage(prefix + noperm);
            return true;
        }
        if (args[0].equalsIgnoreCase("world")) {
            a.setTime(5000);
            sender.sendMessage(prefix + day);
        }
        sender.sendMessage(prefix + ChatColor.RED + "/day world ");
        return true;
    }
}
