package com.prizrakk.prizrakkplugin.commands;

import com.prizrakk.prizrakkplugin.PrizrakkPlugin;
import com.prizrakk.prizrakkplugin.db.Database;
import com.prizrakk.prizrakkplugin.handler.PlayerStats;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.Date;

public class StatsCommand implements CommandExecutor {

    private final PrizrakkPlugin plugin;
    private final Database database;

    public StatsCommand(PrizrakkPlugin plugin, Database database) {
        this.database = database;
        this.plugin = plugin;
    }


    public PlayerStats getPlayerStatsFromDatabase(Player player) throws SQLException {

        PlayerStats playerStats = database.findPlayerStatsByNICK(player.getName());

        if (playerStats == null) {
            playerStats = new PlayerStats(player.getName(), 0, 0, 0, 0,0.0, new Date(), new Date());
            database.createPlayerStats(playerStats);
        }

        return playerStats;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if (command.getName().equalsIgnoreCase("stats")) {
            if (args.length == 0) {

                PlayerStats playerStats;
                try {
                    playerStats = getPlayerStatsFromDatabase(player);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                int block = (int) playerStats.getBlocksBroken();
                double balance = playerStats.getBalance();
                int death = playerStats.getDeaths();
                int kills = playerStats.getKills();
                int warn = playerStats.getWarn_count();
                String nick = playerStats.getPlayerNick();
                sender.sendMessage("§a====== §6Статистика игрока §a======");
                sender.sendMessage("§2Ник: §e" + nick
                        + "\n" + "§2Сломано блоков: §e" + block
                        + "\n" + "§2Убито игроков: §e" + kills
                        + "\n" + "§2Смертей: §e" + death
                        + "\n" +"§2Получено предупреждений: §e" + warn
                        + "\n" + "§2Баланс: §e" + balance);
            } else {
                Player target = Bukkit.getPlayerExact(args[0]);
                if (target == null) {

                    player.sendMessage(plugin.getConfig().getString("message.offline"));
                } else {
                    PlayerStats playerStats;
                    try {
                        playerStats = getPlayerStatsFromDatabase(target);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    int block = (int) playerStats.getBlocksBroken();
                    double balance = playerStats.getBalance();
                    int death = playerStats.getDeaths();
                    int kills = playerStats.getKills();
                    int warn = playerStats.getWarn_count();
                    String nick = playerStats.getPlayerNick();
                    sender.sendMessage("§a====== §6Статистика игрока §3" + nick + " §a=====");
                    sender.sendMessage("§2Ник: §e" + nick
                            + "\n" + "§2Сломано блоков: §e" + block
                            + "\n" + "§2Убито игроков: §e" + kills
                            + "\n" + "§2Смертей: §e" + death
                            + "\n" +"§2Получено предупреждений: §e" + warn
                            + "\n" + "§2Баланс: §e" + balance);
                }
            }
        }
        return true;
    }
}
