package com.prizrakk.prizrakkplugin.commands;

import com.prizrakk.prizrakkplugin.PrizrakkPlugin;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DayCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(PrizrakkPlugin.getInstance().getConfig().getString("message.prefix") + PrizrakkPlugin.getInstance().getConfig().getString("message.noconsole"));
            return true;
        }
        Player player = (Player) sender;
        Player p =(Player) sender;
        World w = p.getWorld();
        World a = Bukkit.getWorld(args[0]);
        if (!player.hasPermission("prizrakk.time.day") || !sender.hasPermission("prizrakk.*")) {
            sender.sendMessage(PrizrakkPlugin.getInstance().getConfig().getString("message.prefix") + PrizrakkPlugin.getInstance().getConfig().getString("message.noperm"));
            return true;
        }
        if (args[0].equalsIgnoreCase("world")) {
            a.setTime(5000);
            sender.sendMessage(PrizrakkPlugin.getInstance().getConfig().getString("message.prefix") + PrizrakkPlugin.getInstance().getConfig().getString("message.day"));
        }
        return true;
    }
}
