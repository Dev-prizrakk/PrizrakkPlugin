package com.prizrakk.prizrakkplugin.commands;

import com.prizrakk.prizrakkplugin.PrizrakkPlugin;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NightCommand implements CommandExecutor {
    public NightCommand() {
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(PrizrakkPlugin.getInstance().getConfig().getString("message.prefix") + PrizrakkPlugin.getInstance().getConfig().getString("message.noconsole"));
            return true;
        }
        Player player = (Player) sender;
        World a = Bukkit.getWorld(args[0]);
        if (!player.hasPermission("prizrakk.time.night") || !sender.hasPermission("prizrakk.*")) {
            sender.sendMessage(PrizrakkPlugin.getInstance().getConfig().getString("message.prefix") + PrizrakkPlugin.getInstance().getConfig().getString("message.noperm"));
            return true;
        }
        if (args[0].equalsIgnoreCase("world")) {
            a.setTime(13000);
            sender.sendMessage(PrizrakkPlugin.getInstance().getConfig().getString("message.prefix") + PrizrakkPlugin.getInstance().getConfig().getString("message.night"));
        }
        return true;
    }
}