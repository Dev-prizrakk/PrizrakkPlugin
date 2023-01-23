package com.prizrakk.fabulouscraft.db;

import com.prizrakk.fabulouscraft.FabulousCraft;
import com.prizrakk.fabulouscraft.commands.AdminWarnCommand;
import org.bukkit.ChatColor;


import java.sql.*;
import java.util.UUID;

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

        String url = plugin.getConfig().getString("config.mysql.connect");
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
        statement.execute(sql);

        statement.close();
        plugin.log.info(ChatColor.GOLD + "База данных успешно импротирована!");
    }

}
