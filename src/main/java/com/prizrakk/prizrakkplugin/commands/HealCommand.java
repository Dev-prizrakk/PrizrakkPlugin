package com.prizrakk.prizrakkplugin.commands;

import com.prizrakk.prizrakkplugin.PrizrakkPlugin;
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
            double maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
            player.setHealth(maxHealth);
            player.setFoodLevel(20);
            player.sendMessage(plugin.getConfig().getString("message.prefix") + plugin.getConfig().getString("message.health"));
        }
        /*

        Тестовая функция скоро заработает)

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("@a")) {
                if (command.getName().equalsIgnoreCase("heal")) {
                    for (Player pl : Bukkit.getOnlinePlayers()) {
                        double maxHealth = pl.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
                        pl.setHealth(maxHealth);
                        pl.setFoodLevel(20);
                        pl.sendMessage(plugin.getConfig().getString("message.prefix") + plugin.getConfig().getString("message.health"));
                    }
                }
            } else {
                try {
                    Player s = Bukkit.getPlayer(args[0]);
                    double maxHealth = s.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
                    s.setHealth(maxHealth);
                    s.setFoodLevel(20);
                    s.sendMessage(plugin.getConfig().getString("message.prefix") + plugin.getConfig().getString("message.health"));
                } catch (Exception e) {
                    for (Player pl : Bukkit.getOnlinePlayers()) {
                        pl.sendMessage(plugin.getConfig().getString("message.prefix") + plugin.getConfig().getString("message.offline"));
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 10, 1);
                    }
                }
            }
        }

         */
        return true;
    }
}

