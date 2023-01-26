package com.prizrakk.prizrakkplugin;

import com.prizrakk.prizrakkplugin.commands.*;
import com.prizrakk.prizrakkplugin.db.Database;
import com.prizrakk.prizrakkplugin.handler.PlayerListen;
import com.prizrakk.prizrakkplugin.handler.ServerPing;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import java.sql.SQLException;
import java.util.logging.Logger;


public final class PrizrakkPlugin extends JavaPlugin {

    public PrizrakkPlugin() {
    }
    private static PrizrakkPlugin instance;
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
        String mconline = getConfig().getString("config.mconline.enable");
        String enable = String.valueOf(true);
        if (mconline == enable) {
            getServer().getPluginManager().registerEvents(new ServerPing(this), this);
        } else {
            log.info("MCONLINE not enabled!");
        }
        getServer().getPluginManager().registerEvents(new PlayerListen(database), this);

        getServer().getPluginCommand("heal").setExecutor(new HealCommand(this));
        getServer().getPluginCommand("feed").setExecutor(new FeedCommand(this));
        getServer().getPluginCommand("gm").setExecutor(new gmSurvivalCommand(this));
        getServer().getPluginCommand("day").setExecutor(new DayCommand(this));
        getServer().getPluginCommand("prizrakk").setExecutor(new SystemCommand(this));
    }


    @Override
    public void onDisable() {}

    public Database getDatabase() {
        return database;
    }
    public static PrizrakkPlugin getInstance() {
        return instance;
    }
}

