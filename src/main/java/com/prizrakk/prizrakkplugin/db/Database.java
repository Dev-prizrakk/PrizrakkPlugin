package com.prizrakk.prizrakkplugin.db;

import com.prizrakk.prizrakkplugin.PrizrakkPlugin;
import com.prizrakk.prizrakkplugin.handler.PlayerStats;
import org.bukkit.ChatColor;

import java.sql.*;


public class Database {
    private static PrizrakkPlugin plugin;
    public Database(PrizrakkPlugin plugin) {
        Database.plugin = plugin;
    }

    private static Connection connection;




    public Connection getConnection() throws  SQLException{

        if(connection != null){
            return connection;
        }

        String url = plugin.getConfig().getString("config.mysql.url");
        String user = plugin.getConfig().getString("config.mysql.user");
        String password = plugin.getConfig().getString("config.mysql.password");


        connection = DriverManager.getConnection(url, user, password);
        plugin.log.info(ChatColor.GOLD + "Подключение успешно произведено!");

        return connection;
    }

    public void initializeDatabase() throws SQLException{
        Statement statement = getConnection().createStatement();
        //String sql = "CREATE TABLE IF NOT EXISTS player_stats(uuid varchar(36) primary key, deaths int, kills int, blocks_broken long)";
        String sql = "CREATE TABLE IF NOT EXISTS player_stats (nick varchar(36) primary key, warn_count int, deaths int, prefix varchar(36), rep int, kills int, blocks_broken long, balance double, last_login DATE, last_logout DATE)";
        statement.execute(sql);

        statement.close();
        plugin.log.info(ChatColor.GOLD + "Проверка базы данных прошло успешно!");
    }
    public PlayerStats findPlayerStatsByNICK(String nick) throws SQLException {

        PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM player_stats WHERE nick = ?");
        statement.setString(1, nick);

        ResultSet resultSet = statement.executeQuery();

        PlayerStats playerStats;

        if(resultSet.next()){

            playerStats = new PlayerStats(resultSet.getString("nick"), resultSet.getInt("warn_count"), resultSet.getString("prefix"), resultSet.getInt("rep"), resultSet.getInt("deaths"), resultSet.getInt("kills"), resultSet.getLong("blocks_broken"), resultSet.getDouble("balance"), resultSet.getDate("last_login"), resultSet.getDate("last_logout"));

            statement.close();

            return playerStats;
        }

        statement.close();

        return null;
    }

    public void createPlayerStats(PlayerStats playerStats) throws SQLException {

        PreparedStatement statement = getConnection()
                .prepareStatement("INSERT INTO player_stats(nick, warn_count, prefix, rep, deaths, kills, blocks_broken, balance, last_login, last_logout) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        statement.setString(1, playerStats.getPlayerNick());
        statement.setInt(2, playerStats.getWarn_count());
        statement.setString(3, playerStats.getPrefix());
        statement.setInt(4, playerStats.getRep());
        statement.setInt(5, playerStats.getDeaths());
        statement.setInt(6, playerStats.getKills());
        statement.setLong(7, playerStats.getBlocksBroken());
        statement.setDouble(8, playerStats.getBalance());
        statement.setDate(9, new Date(playerStats.getLastLogin().getTime()));
        statement.setDate(10, new Date(playerStats.getLastLogout().getTime()));

        statement.executeUpdate();

        statement.close();

    }

    public void updatePlayerStats(PlayerStats playerStats) throws SQLException {

        PreparedStatement statement = getConnection().prepareStatement("UPDATE player_stats SET warn_count = ?, deaths = ?, rep = ?, kills = ?, blocks_broken = ?, balance = ?, last_login = ?, last_logout = ? WHERE nick = ?");
        statement.setInt(1, playerStats.getWarn_count());
        statement.setInt(2, playerStats.getDeaths());
        statement.setInt(3, playerStats.getRep());
        statement.setInt(4, playerStats.getKills());
        statement.setLong(5, playerStats.getBlocksBroken());
        statement.setDouble(6, playerStats.getBalance());
        statement.setDate(7, new Date(playerStats.getLastLogin().getTime()));
        statement.setDate(8, new Date(playerStats.getLastLogout().getTime()));
        statement.setString(9, playerStats.getPlayerNick());

        statement.executeUpdate();

        statement.close();

    }
}