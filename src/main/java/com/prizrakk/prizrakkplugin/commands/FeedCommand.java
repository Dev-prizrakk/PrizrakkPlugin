package com.prizrakk.prizrakkplugin.commands;

import com.prizrakk.prizrakkplugin.PrizrakkPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FeedCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String prefix = ChatColor.translateAlternateColorCodes('&', PrizrakkPlugin.getInstance().getConfig().getString("message.system.prefix"));
        String noperm = ChatColor.translateAlternateColorCodes('&', PrizrakkPlugin.getInstance().getConfig().getString("message.system.noperm"));
        String noconsole = ChatColor.translateAlternateColorCodes('&', PrizrakkPlugin.getInstance().getConfig().getString("message.system.noconsole"));
        String offline = ChatColor.translateAlternateColorCodes('&', PrizrakkPlugin.getInstance().getConfig().getString("message.system.offline"));
        String feed = ChatColor.translateAlternateColorCodes('&', PrizrakkPlugin.getInstance().getConfig().getString("message.other.feed"));

        if (!(sender instanceof Player)) {
            sender.sendMessage(prefix + noconsole);
            return true;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("prizrakk.feed") || !sender.hasPermission("prizrakk.*")) {
            sender.sendMessage(prefix + noperm);
            return true;
        }

        if (command.getName().equalsIgnoreCase("feed")) {
            if (args.length == 0) {
                player.setFoodLevel(20);
                player.sendMessage(prefix + feed);
            } else {
                Player target = Bukkit.getPlayerExact(args[0]);
                if (target == null) {
                    player.sendMessage(prefix + offline);
                } else {
                    target.setFoodLevel(20);
                    target.sendMessage(prefix + feed);
                    String feeded = ChatColor.translateAlternateColorCodes('&', PrizrakkPlugin.getInstance().getConfig().getString("message.other.feeded")).replace("{player}", target.getName());
                    player.sendMessage(prefix + feeded);
                }
            }
        }
        return true;
    }
}
