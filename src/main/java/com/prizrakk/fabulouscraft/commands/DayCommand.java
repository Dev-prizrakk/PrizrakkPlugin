package com.prizrakk.fabulouscraft.commands;

import com.prizrakk.fabulouscraft.FabulousCraft;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DayCommand implements CommandExecutor {
    private final FabulousCraft plugin;
    public DayCommand(FabulousCraft plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if (!(sender instanceof Player)) {
            sender.sendMessage(plugin.getConfig().getString("message.prefix") + plugin.getConfig().getString("message.noconsole"));
            return true;
        }
        if (!player.hasPermission("prizrakk.time.day") || !sender.hasPermission("prizrakk.*")) {
            sender.sendMessage(plugin.getConfig().getString("message.prefix") + plugin.getConfig().getString("message.noperm"));
            return true;
        }
        if (command.getName().equalsIgnoreCase("day")) {
            sender.sendMessage(plugin.getConfig().getString("message.prefix") + ChatColor.RED + "Скоро будет!");
            sender.sendMessage(plugin.getConfig().getString("message.prefix") + plugin.getConfig().getString("message.day"));
        }
        return true;
    }
}
