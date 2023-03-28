package com.prizrakk.prizrakkplugin.commands;

import com.prizrakk.prizrakkplugin.PrizrakkPlugin;
import com.prizrakk.prizrakkplugin.config.MessageConfig;
import com.prizrakk.prizrakkplugin.db.Database;
import com.prizrakk.prizrakkplugin.handler.PlayerStats;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.Date;

public class PrefixCommand extends AbstractCommand {
    public PrefixCommand(Database database, PrizrakkPlugin plugin) {
        super("prefix");
        this.database = database;
        this.plugin = plugin;
    }
    private final PrizrakkPlugin plugin;
    private final Database database;

    public PlayerStats getPlayerStatsFromDatabase(Player player) throws SQLException {

        PlayerStats playerStats = database.findPlayerStatsByNICK(player.getName());

        if (playerStats == null) {
            playerStats = new PlayerStats(player.getName(), 0, "default",0, 0, 0, 0,0.0, new Date(), new Date());
            database.createPlayerStats(playerStats);
        }

        return playerStats;
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) throws InterruptedException {
        String prefix = ChatColor.translateAlternateColorCodes('&', MessageConfig.get().getString("message.system.prefix"));
        String error = ChatColor.translateAlternateColorCodes('&', MessageConfig.get().getString("message.system.error"));
        String noperm = ChatColor.translateAlternateColorCodes('&', MessageConfig.get().getString("message.system.no-perm"));
        String offline = ChatColor.translateAlternateColorCodes('&', MessageConfig.get().getString("message.system.offline"));


        Player player = (Player) sender;
        if (!player.hasPermission("prizrakk.admin") || !sender.hasPermission("prizrakk.*")) {
            sender.sendMessage(prefix + noperm);
            return;
        }

        if (args.length == 0) {
            sender.sendMessage(prefix + ChatColor.RED + "/prizrakk help");
            return;
        }
        if (args[0].equalsIgnoreCase("set")) {
            if (args.length == 1) {
                sender.sendMessage(prefix + error);
            } else {
                Player target = Bukkit.getPlayerExact(args[1]);
                if (target == null) {
                    player.sendMessage(prefix + offline);
                } else {
                    try {
                        PlayerStats playerStats = getPlayerStatsFromDatabase(target);
                        playerStats.setPrefix(args[2]);
                        database.updatePlayerStats(playerStats);
                    } catch (SQLException e1) {
                        if (plugin.getConfig().getBoolean("config.debug") == true) {
                            e1.printStackTrace();
                        }
                        PrizrakkPlugin.getInstance().getLogger().warning("Could not update player stats after block break.");
                    }
                    sender.sendMessage("Prefix set!");
                }
            }
            return;
        }
        if (args[0].equalsIgnoreCase("reset")) {
            if (args.length == 1) {
                sender.sendMessage(prefix + error);
            } else {
                Player target = Bukkit.getPlayerExact(args[1]);
                if (target == null) {
                    player.sendMessage(prefix + offline);
                } else {
                    try {
                        PlayerStats playerStats = getPlayerStatsFromDatabase(target);
                        playerStats.setPrefix("default");
                        database.updatePlayerStats(playerStats);
                    } catch (SQLException e1) {
                        if (plugin.getConfig().getBoolean("config.debug") == true) {
                            e1.printStackTrace();
                        }
                        PrizrakkPlugin.getInstance().getLogger().warning("Could not update player stats after block break.");
                    }
                    sender.sendMessage("Prefix set!");
                }
            }
            return;
        }
        sender.sendMessage(prefix + ChatColor.RED + "Неизвестная подкоманда: " + args[0]);

    }
}
