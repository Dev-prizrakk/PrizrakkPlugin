package com.prizrakk.fabulouscraft.db;

import com.prizrakk.fabulouscraft.FabulousCraft;
import com.prizrakk.fabulouscraft.commands.AdminWarnCommand;
import com.prizrakk.fabulouscraft.handler.PlayerStats;
import org.bukkit.ChatColor;
import java.sql.*;


public class Database {
    private final FabulousCraft plugin;
    public Database(FabulousCraft plugin) {
        this.plugin = plugin;
    }

    private Connection connection;




    public Connection getConnection() throws  SQLException{

        if(connection != null){
            return connection;
        }

        String url = "jdbc:mysql://"+ plugin.getConfig().getString("config.mysql.ip") + ":" + plugin.getConfig().getString("config.mysql.port") + "/" + plugin.getConfig().getString("config.mysql.database");
        String user = plugin.getConfig().getString("config.mysql.user");
        String password = plugin.getConfig().getString("config.mysql.password");


        this.connection = DriverManager.getConnection(url, user, password);
        plugin.log.info(ChatColor.GOLD + "Подключения к базе данных успешно произведено!");

        return this.connection;
    }

    public void initializeDatabase() throws SQLException{
        Statement statement = getConnection().createStatement();
        //String sql = "CREATE TABLE IF NOT EXISTS player_stats(uuid varchar(36) primary key, deaths int, kills int, blocks_broken long)";
        String sql = "CREATE TABLE IF NOT EXISTS admin_warn(admin_nick VARCHAR(41), warn_reason VARCHAR(41), owner_by VARCHAR(41))";
        sql = "CREATE TABLE IF NOT EXISTS player_stats (uuid varchar(36) primary key, deaths int, kills int, blocks_broken long, balance double, last_login DATE, last_logout DATE)";
        statement.execute(sql);

        statement.close();
        plugin.log.info(ChatColor.GOLD + "База данных успешно импротирована!");
    }
    public PlayerStats findPlayerStatsByUUID(String uuid) throws SQLException {

        PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM player_stats WHERE uuid = ?");
        statement.setString(1, uuid);

        ResultSet resultSet = statement.executeQuery();

        PlayerStats playerStats;

        if(resultSet.next()){

            playerStats = new PlayerStats(resultSet.getString("uuid"), resultSet.getInt("deaths"), resultSet.getInt("kills"), resultSet.getLong("blocks_broken"), resultSet.getDouble("balance"), resultSet.getDate("last_login"), resultSet.getDate("last_logout"));

            statement.close();

            return playerStats;
        }

        statement.close();

        return null;
    }

    public void createPlayerStats(PlayerStats playerStats) throws SQLException {

        PreparedStatement statement = getConnection()
                .prepareStatement("INSERT INTO player_stats(uuid, deaths, kills, blocks_broken, balance, last_login, last_logout) VALUES (?, ?, ?, ?, ?, ?, ?)");
        statement.setString(1, playerStats.getPlayerUUID());
        statement.setInt(2, playerStats.getDeaths());
        statement.setInt(3, playerStats.getKills());
        statement.setLong(4, playerStats.getBlocksBroken());
        statement.setDouble(5, playerStats.getBalance());
        statement.setDate(6, new Date(playerStats.getLastLogin().getTime()));
        statement.setDate(7, new Date(playerStats.getLastLogout().getTime()));

        statement.executeUpdate();

        statement.close();

    }

    public void updatePlayerStats(PlayerStats playerStats) throws SQLException {

        PreparedStatement statement = getConnection().prepareStatement("UPDATE player_stats SET deaths = ?, kills = ?, blocks_broken = ?, balance = ?, last_login = ?, last_logout = ? WHERE uuid = ?");
        statement.setInt(1, playerStats.getDeaths());
        statement.setInt(2, playerStats.getKills());
        statement.setLong(3, playerStats.getBlocksBroken());
        statement.setDouble(4, playerStats.getBalance());
        statement.setDate(5, new Date(playerStats.getLastLogin().getTime()));
        statement.setDate(6, new Date(playerStats.getLastLogout().getTime()));
        statement.setString(7, playerStats.getPlayerUUID());

        statement.executeUpdate();

        statement.close();

    }

    public void deletePlayerStats(PlayerStats playerStats) throws SQLException {

        PreparedStatement statement = getConnection().prepareStatement("DELETE FROM player_stats WHERE uuid = ?");
        statement.setString(1, playerStats.getPlayerUUID());

        statement.executeUpdate();

        statement.close();

    }

}