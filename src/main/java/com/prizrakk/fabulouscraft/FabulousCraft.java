package com.prizrakk.fabulouscraft;

import com.prizrakk.fabulouscraft.commands.*;
import com.prizrakk.fabulouscraft.db.Database;
import com.prizrakk.fabulouscraft.handler.ServerPing;
import com.sun.net.httpserver.HttpServer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.net.InetSocketAddress;
import java.sql.SQLException;
import java.util.logging.Logger;


public final class FabulousCraft extends JavaPlugin {

    public FabulousCraft() {
    }
    private static FabulousCraft instance;
    private Database database;
    public Logger log = Logger.getLogger("Minecraft");
    @Override
    public void onEnable() {
        instance = this;

        // Создаем конфиг
        saveDefaultConfig();
        // Тестовая шняга

        try {
            ServerPing.main((String[])null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // Лог о запуске
        log.info(ChatColor.GOLD + "Версия: "+ getConfig().getString("config.version"));
        //Подключаем к базе данных!
        try {
            this.database = new Database(this);
            database.initializeDatabase();
        }catch (SQLException ex) {
            log.warning(ChatColor.RED + "Ошибка Базы Данных!");
            ex.printStackTrace();
        }
        //getServer().getPluginManager().registerEvents(new Listeners(this), this);
        // Грузим эвенты!
        getServer().getPluginManager().registerEvents(new ServerPing(this),this);
        //getServer().getPluginManager().registerEvents(new SimpleEventHandler(this), this);
        // Подгружаем команды
        getServer().getPluginCommand("heal").setExecutor(new HealCommand(this));
        getServer().getPluginCommand("feed").setExecutor(new FeedCommand(this));
        getServer().getPluginCommand("gm").setExecutor(new gmSurvivalCommand(this));
        getServer().getPluginCommand("day").setExecutor(new DayCommand(this));
        getServer().getPluginCommand("prizrakk").setExecutor(new SystemCommand(this));
        //getServer().getPluginCommand("adm_warn").setExecutor(new AdminWarnCommand(this));
    }


    @Override
    public void onDisable() {}

    public Database getDatabase() {
        return database;
    }
    public static FabulousCraft getInstance() {
        return instance;
    }

}

