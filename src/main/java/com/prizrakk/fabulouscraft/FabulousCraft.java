package com.prizrakk.fabulouscraft;

import com.prizrakk.fabulouscraft.commands.*;
import com.prizrakk.fabulouscraft.db.Database;
import com.prizrakk.fabulouscraft.handler.ServerPing;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
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
        saveDefaultConfig();
        log.info(ChatColor.GOLD + "Плагин запущен!");
        log.info(ChatColor.GOLD + "Версия: Pre-Release 1.0");
        try {
            ServerPing.main((String[])null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            this.database = new Database(this);
            database.initializeDatabase();
        }catch (SQLException ex) {
            log.warning(ChatColor.RED + "Ошибка Базы Данных!");
            ex.printStackTrace();
        }

        getServer().getPluginManager().registerEvents(new ServerPing(this),this);

        getServer().getPluginCommand("heal").setExecutor(new HealCommand(this));
        getServer().getPluginCommand("feed").setExecutor(new FeedCommand(this));
        getServer().getPluginCommand("gm").setExecutor(new gmSurvivalCommand(this));
        getServer().getPluginCommand("day").setExecutor(new DayCommand(this));
        getServer().getPluginCommand("prizrakk").setExecutor(new SystemCommand(this));
    }


    @Override
    public void onDisable() {
        log.info(ChatColor.RED + "Плагин отключен!");
    }

    public Database getDatabase() {
        return database;
    }
    public static FabulousCraft getInstance() {
        return instance;
    }

}

