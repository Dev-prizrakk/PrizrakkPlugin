package com.prizrakk.prizrakkplugin.commands;


import com.prizrakk.prizrakkplugin.PrizrakkPlugin;
import com.prizrakk.prizrakkplugin.config.MessageConfig;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;


public class  gmSurvivalCommand implements CommandExecutor, TabExecutor {
    private final PrizrakkPlugin plugin;

    public gmSurvivalCommand(PrizrakkPlugin plugin) {
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            plugin.getLogger().info(("message.system.prefix") + MessageConfig.get().getString("message.system.no-console"));
            return true;
        }
        Player player =(Player) sender;

        String message = MessageConfig.get().getString("message.system.prefix") + MessageConfig.get().getString("message.other.gm");
        String message_other = MessageConfig.get().getString("message.system.prefix") + MessageConfig.get().getString("message.other.gm-other");
        if (!sender.hasPermission("prizrakk.gm") || !sender.hasPermission("prizrakk.*")) {
            sender.sendMessage(MessageConfig.get().getString("message.system.prefix") + MessageConfig.get().getString("message.system.no-perm"));
            return true;
        }


        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("0") || args[0].equalsIgnoreCase("survival")) {
                player.setGameMode(GameMode.SURVIVAL);
                player.sendMessage(message.replace("%gamemode%", "survival"));
                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10, 1);
            } else if (args[0].equalsIgnoreCase("1") || args[0].equalsIgnoreCase("creative")) {
                player.setGameMode(GameMode.CREATIVE);
                player.sendMessage(message.replace("%gamemode%", "creative"));
                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10, 1);
            } else if (args[0].equalsIgnoreCase("2") || args[0].equalsIgnoreCase("adventure")) {
                player.setGameMode(GameMode.ADVENTURE);
                player.sendMessage(message.replace("%gamemode%", "adventure"));
                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10, 1);
            } else if (args[0].equalsIgnoreCase("3") || args[0].equalsIgnoreCase("spectator")) {
                player.setGameMode(GameMode.SPECTATOR);
                player.sendMessage(message.replace("%gamemode%", "spectator"));
                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10, 1);
            }
        }
        if (!sender.hasPermission("prizrakk.gm") && !sender.hasPermission("prizrakk.gm.other") || !sender.hasPermission("prizrakk.*")) {
            sender.sendMessage(plugin.getConfig().getString("message.system.prefix") + plugin.getConfig().getString("message.system.noperm"));
            return true;
        }
        if (args.length == 2) {
            if (args[1].equalsIgnoreCase("@a")) {
                if (args[0].equalsIgnoreCase("0") || args[0].equalsIgnoreCase("survival")) {
                    for (Player pl : Bukkit.getOnlinePlayers()) {
                        pl.setGameMode(GameMode.SURVIVAL);
                        pl.sendMessage(message_other.replace("%gamemode%", "survival"));
                        pl.playSound(pl.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10, 1);
                    }
                } else if (args[0].equalsIgnoreCase("1") || args[0].equalsIgnoreCase("creative")) {
                    for (Player pl : Bukkit.getOnlinePlayers()) {
                        pl.setGameMode(GameMode.CREATIVE);
                        pl.sendMessage(message_other.replace("%gamemode%", "creative"));
                        pl.playSound(pl.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10, 1);
                    }
                } else if (args[0].equalsIgnoreCase("2") || args[0].equalsIgnoreCase("adventure")) {
                    for (Player pl : Bukkit.getOnlinePlayers()) {
                        pl.setGameMode(GameMode.ADVENTURE);
                        pl.sendMessage(message_other.replace("%gamemode%", "adventure"));
                        pl.playSound(pl.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10, 1);
                    }
                } else if (args[0].equalsIgnoreCase("3") || args[0].equalsIgnoreCase("spectator")) {
                    for (Player pl : Bukkit.getOnlinePlayers()) {
                        pl.setGameMode(GameMode.SPECTATOR);
                        pl.sendMessage(message_other.replace("%gamemode%", "spectator"));
                        pl.playSound(pl.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10, 1);
                    }
                }
            } else {
                try {
                    Player s = Bukkit.getPlayer(args[1]);
                    if (args[0].equalsIgnoreCase("0") || args[0].equalsIgnoreCase("survival")) {
                        s.setGameMode(GameMode.SURVIVAL);
                        s.sendMessage(message.replace("%gamemode%", "survival"));
                        s.playSound(s.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10, 1);
                    } else if (args[0].equalsIgnoreCase("1") || args[0].equalsIgnoreCase("creative")) {
                        s.setGameMode(GameMode.CREATIVE);
                        s.sendMessage(message.replace("%gamemode%", "creative"));
                        s.playSound(s.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10, 1);
                    } else if (args[0].equalsIgnoreCase("2") || args[0].equalsIgnoreCase("adventure")) {
                        s.setGameMode(GameMode.ADVENTURE);
                        s.sendMessage(message.replace("%gamemode%", "adventure"));
                        s.playSound(s.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10, 1);
                    } else if (args[0].equalsIgnoreCase("3") || args[0].equalsIgnoreCase("spectator")) {
                        s.setGameMode(GameMode.SPECTATOR);
                        s.sendMessage(message.replace("%gamemode%", "spectator"));
                        s.playSound(s.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10, 1);
                    }
                } catch (Exception e) {
                    for (Player pl : Bukkit.getOnlinePlayers()) {
                        pl.sendMessage(MessageConfig.get().getString("message.system.prefix") + MessageConfig.get().getString("message.system.offline"));
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 10, 1);
                    }
                }
            }
        }
        return true;
    }
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1){
            List<String> arguments = new ArrayList<>();
            arguments.add("0");
            arguments.add("1");
            arguments.add("2");
            arguments.add("3");
            arguments.add("survival");
            arguments.add("creative");
            arguments.add("adventure");
            arguments.add("spectator");

            return arguments;
        }
        return null;
    }
}