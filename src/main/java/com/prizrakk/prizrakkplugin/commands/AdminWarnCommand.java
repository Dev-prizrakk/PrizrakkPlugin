package com.prizrakk.prizrakkplugin.commands;

import com.prizrakk.prizrakkplugin.PrizrakkPlugin;
import com.prizrakk.prizrakkplugin.db.Database;
import com.prizrakk.prizrakkplugin.handler.PlayerStats;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.Date;

public class AdminWarnCommand extends AbstractCommand {

    public AdminWarnCommand(Database database) {
        super("admin");
        this.database = database;
    }
    private final Database database;

    public PlayerStats getPlayerStatsFromDatabase(Player player) throws SQLException {

        PlayerStats playerStats = database.findPlayerStatsByNICK(player.getName());

        if (playerStats == null) {
            playerStats = new PlayerStats(player.getName(), 0, 0, 0, 0,0.0, new Date(), new Date());
            database.createPlayerStats(playerStats);
        }

        return playerStats;
    }


    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(PrizrakkPlugin.getInstance().getConfig().getString("message.prefix") + ChatColor.RED + "/prizrakk help");
            return;
        }
        Player player = (Player) sender;
        if (args[0].equalsIgnoreCase("addwarn")) {
            if (args.length == 1) {
                sender.sendMessage(PrizrakkPlugin.getInstance().getConfig().getString("message.error"));

            } else {
                Player target = Bukkit.getPlayerExact(args[1]);
                if (target == null) {

                    player.sendMessage(PrizrakkPlugin.getInstance().getConfig().getString("message.offline"));
                } else {
                    try {
                        PlayerStats playerStats = getPlayerStatsFromDatabase(target);
                        playerStats.setWarn_count(playerStats.getWarn_count() + 1);
                        database.updatePlayerStats(playerStats);
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                        PrizrakkPlugin.getInstance().getLogger().warning("Could not update player stats after block break.");
                    }
                    sender.sendMessage(PrizrakkPlugin.getInstance().getConfig().getString("message.addwarn") + target.getName());

                    String message = PrizrakkPlugin.getInstance().getConfig().getString("message.add-warn-view");
                    message = message.replace("%admin%", player.getName());
                    String remessage1 = ChatColor.translateAlternateColorCodes('&', message);
                    target.sendMessage(PrizrakkPlugin.getInstance().getConfig().getString("message.prefix") + remessage1);
                }
            }
            return;
        }
        if (args[0].equalsIgnoreCase("delwarn")) {
            if (args.length == 1) {
                sender.sendMessage(PrizrakkPlugin.getInstance().getConfig().getString("message.error"));

            } else {
                Player target = Bukkit.getPlayerExact(args[1]);
                if (target == null) {
                    player.sendMessage(PrizrakkPlugin.getInstance().getConfig().getString("message.offline"));
                } else {
                    try {
                        PlayerStats playerStats = getPlayerStatsFromDatabase(target);
                        playerStats.setWarn_count(playerStats.getWarn_count() - 1);
                        database.updatePlayerStats(playerStats);
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                        PrizrakkPlugin.getInstance().getLogger().warning("Could not update player stats after block break.");
                    }
                    sender.sendMessage(PrizrakkPlugin.getInstance().getConfig().getString("message.delwarn") + target.getName());

                    String message = PrizrakkPlugin.getInstance().getConfig().getString("message.del-warn-view");
                    message = message.replace("%admin%", player.getName());
                    String remessage = ChatColor.translateAlternateColorCodes('&', message);

                    target.sendMessage(PrizrakkPlugin.getInstance().getConfig().getString("message.prefix") + remessage);
                }
            }
            return;
        }

        sender.sendMessage(PrizrakkPlugin.getInstance().getConfig().getString("message.prefix") + ChatColor.RED + "Неизвестная подкоманда: " + args[0]);
    }
}
