package com.prizrakk.prizrakkplugin.commands;

import com.prizrakk.prizrakkplugin.PrizrakkPlugin;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FeedCommand implements CommandExecutor {

    private final PrizrakkPlugin plugin;
    public FeedCommand(PrizrakkPlugin plugin) {
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(plugin.getConfig().getString("message.prefix") + plugin.getConfig().getString("message.noconsole"));
            return true;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("prizrakk.feed") || !sender.hasPermission("prizrakk.*")) {
            sender.sendMessage(plugin.getConfig().getString("message.prefix") + plugin.getConfig().getString("message.noperm"));
            return true;
        }

        if (command.getName().equalsIgnoreCase("heal")) {
            if (args.length == 0) {
                player.setFoodLevel(20);
                player.sendMessage(plugin.getConfig().getString("message.prefix") + plugin.getConfig().getString("message.feed"));
            }
        } else {
            Player target = Bukkit.getPlayerExact(args[0]);
            if (target == null) {
                player.sendMessage(plugin.getConfig().getString("message.offline"));
            } else {
                target.setFoodLevel(20);
                target.sendMessage(plugin.getConfig().getString("message.prefix") + plugin.getConfig().getString("message.feed"));
            }
        }
        return false;
    }
}
