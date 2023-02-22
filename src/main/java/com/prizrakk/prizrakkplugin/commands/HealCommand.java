package com.prizrakk.prizrakkplugin.commands;

import com.prizrakk.prizrakkplugin.PrizrakkPlugin;
import com.prizrakk.prizrakkplugin.config.MessageConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HealCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String prefix = ChatColor.translateAlternateColorCodes('&', MessageConfig.get().getString("message.system.prefix"));
        String noperm = ChatColor.translateAlternateColorCodes('&', MessageConfig.get().getString("message.system.no-perm"));
        String noconsole = ChatColor.translateAlternateColorCodes('&', MessageConfig.get().getString("message.system.no-console"));
        String offline = ChatColor.translateAlternateColorCodes('&', MessageConfig.get().getString("message.system.offline"));
        String health = ChatColor.translateAlternateColorCodes('&', MessageConfig.get().getString("message.other.health"));

        if (!(sender instanceof Player)) {
            sender.sendMessage(prefix + noconsole);
            return true;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("prizrakk.heal") || !sender.hasPermission("prizrakk.*")) {
            sender.sendMessage(prefix + noperm);
            return true;
        }
        if (command.getName().equalsIgnoreCase("heal")) {
            if (args.length == 0) {
                double maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
                player.setHealth(maxHealth);
                player.setFoodLevel(20);
                player.sendMessage(prefix + health);
            } else {
                Player target = Bukkit.getPlayerExact(args[0]);
                if (target == null) {
                    player.sendMessage(prefix + offline);
                } else {
                    double maxHealth = target.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
                    target.setHealth(maxHealth);
                    target.setFoodLevel(20);
                    target.sendMessage(prefix + health);
                    String healed = ChatColor.translateAlternateColorCodes('&', PrizrakkPlugin.getInstance().getConfig().getString("message.other.healed")).replace("{player}", target.getName());
                    player.sendMessage(prefix + healed);
                }
            }
            return true;
        }
        return true;
    }
}

