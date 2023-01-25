package com.prizrakk.fabulouscraft.commands;

import com.prizrakk.fabulouscraft.FabulousCraft;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HealCommand implements CommandExecutor {

    private final FabulousCraft plugin;
    public HealCommand(FabulousCraft plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if (!(sender instanceof Player)) {
            sender.sendMessage(plugin.getConfig().getString("message.prefix") + plugin.getConfig().getString("message.noconsole"));
            return true;
        }
        if (!player.hasPermission("prizrakk.heal") || !sender.hasPermission("prizrakk.*")) {
            sender.sendMessage(plugin.getConfig().getString("message.prefix") + plugin.getConfig().getString("message.noperm"));
            return true;
        }
        if (command.getName().equalsIgnoreCase("heal")) {
            double maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
            player.setHealth(maxHealth);
            player.setFoodLevel(20);
            player.sendMessage(plugin.getConfig().getString("message.prefix") + plugin.getConfig().getString("message.health"));
        }
        return true;
    }
}

