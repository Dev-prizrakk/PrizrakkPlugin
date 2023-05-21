package com.prizrakk.prizrakkplugin.commands;

import com.prizrakk.prizrakkplugin.config.MessageConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class DayCommand implements CommandExecutor, TabExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String prefix = ChatColor.translateAlternateColorCodes('&', MessageConfig.get().getString("message.system.prefix"));
        String day = ChatColor.translateAlternateColorCodes('&', MessageConfig.get().getString("message.time.day"));
        String noconsole = ChatColor.translateAlternateColorCodes('&', MessageConfig.get().getString("message.system.no-console"));
        String noperm = ChatColor.translateAlternateColorCodes('&', MessageConfig.get().getString("message.system.no-perm"));

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
            return true;
        }
        sender.sendMessage(prefix + ChatColor.RED + "/day world ");
        return true;
    }
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1){
            List<String> arguments = new ArrayList<>();
            arguments.add("world");

            return arguments;
        }
        return null;
    }
}
