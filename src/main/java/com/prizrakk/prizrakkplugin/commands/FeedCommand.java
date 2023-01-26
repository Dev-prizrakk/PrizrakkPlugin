package com.prizrakk.prizrakkplugin.commands;

import com.prizrakk.prizrakkplugin.PrizrakkPlugin;
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

        if (command.getName().equalsIgnoreCase("feed")) {
            player.setFoodLevel(20);
            player.sendMessage(plugin.getConfig().getString("message.prefix") + plugin.getConfig().getString("message.feed"));
        }
        return true;
    }
}
