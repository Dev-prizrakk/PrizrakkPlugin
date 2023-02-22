package com.prizrakk.prizrakkplugin.commands;

import com.prizrakk.prizrakkplugin.PrizrakkPlugin;
import com.prizrakk.prizrakkplugin.config.MessageConfig;
import com.prizrakk.prizrakkplugin.db.Database;
import com.prizrakk.prizrakkplugin.handler.PlayerStats;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.Date;

public class StatsCommand implements CommandExecutor {

    private final Database database;
    private final PrizrakkPlugin plugin;

    public StatsCommand(Database database, PrizrakkPlugin plugin) {
        this.database = database;
        this.plugin = plugin;
    }


    public PlayerStats getPlayerStatsFromDatabase(Player player) throws SQLException {

        PlayerStats playerStats = database.findPlayerStatsByNICK(player.getName());

        if (playerStats == null) {
            playerStats = new PlayerStats(player.getName(), 0, "Житель",0, 0, 0, 0,0.0, new Date(), new Date());
            database.createPlayerStats(playerStats);
        }

        return playerStats;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String offline = ChatColor.translateAlternateColorCodes('&', MessageConfig.get().getString("message.system.offline"));
        String prefix = ChatColor.translateAlternateColorCodes('&', MessageConfig.get().getString("message.system.prefix"));
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
                        + "\n" + "§2Получено предупреждений: §e" + warn
                        + "\n" + "§2Баланс: §e" + balance
                        + "\n" + "§2Репутация: §e" + balance);
            } else {
                Player target = Bukkit.getPlayerExact(args[0]);
                if (target == null) {
                    player.sendMessage(offline);
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
                    Date last_login = playerStats.getLastLogin();
                    Date last_logout = playerStats.getLastLogout();
                    sender.sendMessage("§a====== §6Статистика игрока §3" + nick + " §a=====");
                    sender.sendMessage("§2Ник: §e" + nick
                            + "\n" + "§2Сломано блоков: §e" + block
                            + "\n" + "§2Убито игроков: §e" + kills
                            + "\n" + "§2Смертей: §e" + death
                            + "\n" + "§2Получено предупреждений: §e" + warn
                            + "\n" + "§2Баланс: §e" + balance
                            + "\n" + "§2Репутация: §e" + balance
                            + "\n" + "§2Здоровье игрка: §e" + target.getHealth()
                            + "\n" + "§2Последний вход: §e" + last_login
                            + "\n" + "§2Последний выход: §e" + last_logout
                            );
                    if (sender.hasPermission("prizrakk.admin")) {
                        sender.sendMessage("§2Ip адресс: §e" + target.getAddress());
                    }
                }
            }
            if (!(sender instanceof Player)) {
                return true;
            } else {
                plugin.getLogger().info(prefix + " >> " + player.getName() + "used: /prizrakk help");
            }
        }
        return true;
    }
}
