package com.prizrakk.prizrakkplugin.commands;


import com.prizrakk.prizrakkplugin.PrizrakkPlugin;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class  gmSurvivalCommand implements CommandExecutor {
    private final PrizrakkPlugin plugin;

    public gmSurvivalCommand(PrizrakkPlugin plugin) {
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            plugin.getLogger().info(plugin.getConfig().getString("message.noconsole"));
            return true;
        }
        Player player =(Player) sender;

        if (!sender.hasPermission("prizrakk.gm") || !sender.hasPermission("prizrakk.*")) {
            sender.sendMessage(plugin.getConfig().getString("message.prefix") + plugin.getConfig().getString("message.noperm"));
            return true;
        }


        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("0") || args[0].equalsIgnoreCase("survival")) {
                player.setGameMode(GameMode.SURVIVAL);
                player.sendMessage(plugin.getConfig().getString("message.prefix") + plugin.getConfig().getString("message.gm") + " SURVIVAL");
                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10, 1);
            } else if (args[0].equalsIgnoreCase("1") || args[0].equalsIgnoreCase("creative")) {
                player.setGameMode(GameMode.CREATIVE);
                player.sendMessage(plugin.getConfig().getString("message.prefix") + plugin.getConfig().getString("message.gm") + " CREATIVE");
                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10, 1);
            } else if (args[0].equalsIgnoreCase("2") || args[0].equalsIgnoreCase("adventure")) {
                player.setGameMode(GameMode.ADVENTURE);
                player.sendMessage(plugin.getConfig().getString("message.prefix") + plugin.getConfig().getString("message.gm") + " ADVENTURE");
                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10, 1);
            } else if (args[0].equalsIgnoreCase("3") || args[0].equalsIgnoreCase("spectator")) {
                player.setGameMode(GameMode.SPECTATOR);
                player.sendMessage(plugin.getConfig().getString("message.prefix") + plugin.getConfig().getString("message.gm") + " SPECTATOR");
                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10, 1);
            }
        }
        if (!sender.hasPermission("prizrakk.gm") && !sender.hasPermission("prizrakk.gm.other") || !sender.hasPermission("prizrakk.*")) {
            sender.sendMessage(plugin.getConfig().getString("message.prefix") + plugin.getConfig().getString("message.noperm"));
            return true;
        }
        if (args.length == 2) {
            if (args[1].equalsIgnoreCase("@a")) {
                if (args[0].equalsIgnoreCase("0") || args[0].equalsIgnoreCase("survival")) {
                    for (Player pl : Bukkit.getOnlinePlayers()) {
                        pl.setGameMode(GameMode.SURVIVAL);
                        pl.sendMessage(plugin.getConfig().getString("message.prefix") + plugin.getConfig().getString("message.gm") + " SURVIVAL");
                        pl.playSound(pl.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10, 1);
                    }
                } else if (args[0].equalsIgnoreCase("1") || args[0].equalsIgnoreCase("creative")) {
                    for (Player pl : Bukkit.getOnlinePlayers()) {
                        pl.setGameMode(GameMode.CREATIVE);
                        pl.sendMessage(plugin.getConfig().getString("message.prefix") + plugin.getConfig().getString("message.gm") + " CREATIVE");
                        pl.playSound(pl.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10, 1);
                    }
                } else if (args[0].equalsIgnoreCase("2") || args[0].equalsIgnoreCase("adventure")) {
                    for (Player pl : Bukkit.getOnlinePlayers()) {
                        pl.setGameMode(GameMode.ADVENTURE);
                        pl.sendMessage(plugin.getConfig().getString("message.prefix") + plugin.getConfig().getString("message.gm") + " ADVENTURE");
                        pl.playSound(pl.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10, 1);
                    }
                } else if (args[0].equalsIgnoreCase("3") || args[0].equalsIgnoreCase("spectator")) {
                    for (Player pl : Bukkit.getOnlinePlayers()) {
                        pl.setGameMode(GameMode.SPECTATOR);
                        pl.sendMessage(plugin.getConfig().getString("message.prefix") + plugin.getConfig().getString("message.gm") + " SPECTATOR");
                        pl.playSound(pl.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10, 1);
                    }
                }
            } else {
                try {
                    Player s = Bukkit.getPlayer(args[1]);
                    if (args[0].equalsIgnoreCase("0") || args[0].equalsIgnoreCase("survival")) {
                        s.setGameMode(GameMode.SURVIVAL);
                        s.sendMessage(plugin.getConfig().getString("message.prefix") + plugin.getConfig().getString("message.gm") + " SURVIVAL");
                        s.playSound(s.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10, 1);
                    } else if (args[0].equalsIgnoreCase("1") || args[0].equalsIgnoreCase("creative")) {
                        s.setGameMode(GameMode.CREATIVE);
                        s.sendMessage(plugin.getConfig().getString("message.prefix") + plugin.getConfig().getString("message.gm") + " CREATIVE");
                        s.playSound(s.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10, 1);
                    } else if (args[0].equalsIgnoreCase("2") || args[0].equalsIgnoreCase("adventure")) {
                        s.setGameMode(GameMode.ADVENTURE);
                        s.sendMessage(plugin.getConfig().getString("message.prefix") + plugin.getConfig().getString("message.gm") + " ADVENTURE");
                        s.playSound(s.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10, 1);
                    } else if (args[0].equalsIgnoreCase("3") || args[0].equalsIgnoreCase("spectator")) {
                        s.setGameMode(GameMode.SPECTATOR);
                        s.sendMessage(plugin.getConfig().getString("message.prefix") + plugin.getConfig().getString("message.gm") + " SPECTATOR");
                        s.playSound(s.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10, 1);
                    }
                } catch (Exception e) {
                    for (Player pl : Bukkit.getOnlinePlayers()) {
                        pl.sendMessage(plugin.getConfig().getString("message.prefix") + plugin.getConfig().getString("message.offline"));
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 10, 1);
                    }
                }
            }
        }
        return true;
    }
}


