package com.prizrakk.prizrakkplugin.commands;

import com.prizrakk.prizrakkplugin.PrizrakkPlugin;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HealCommand implements CommandExecutor {

    private final PrizrakkPlugin plugin;
    public HealCommand(PrizrakkPlugin plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(plugin.getConfig().getString("message.prefix") + plugin.getConfig().getString("message.noconsole"));
            return true;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("prizrakk.heal") || !sender.hasPermission("prizrakk.*")) {
            sender.sendMessage(plugin.getConfig().getString("message.prefix") + plugin.getConfig().getString("message.noperm"));
            return true;
        }
        if (command.getName().equalsIgnoreCase("heal")) {
            if (args.length == 0) {
                double maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
                player.setHealth(maxHealth);
                player.setFoodLevel(20);
                player.sendMessage(plugin.getConfig().getString("message.prefix") + plugin.getConfig().getString("message.health"));
            }
            } else {

                Player target = Bukkit.getPlayerExact(args[0]);
                if (target == null) {
                    player.sendMessage(plugin.getConfig().getString("message.offline"));
                } else {
                    double maxHealth = target.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
                    target.setHealth(maxHealth);
                    target.setFoodLevel(20);
                    target.sendMessage(plugin.getConfig().getString("message.prefix") + plugin.getConfig().getString("message.health"));
                }
            }
        return false;
    }
}

