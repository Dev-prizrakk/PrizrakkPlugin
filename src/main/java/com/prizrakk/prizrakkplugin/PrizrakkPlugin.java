package com.prizrakk.prizrakkplugin;

import com.prizrakk.prizrakkplugin.commands.*;
import com.prizrakk.prizrakkplugin.db.Database;
import com.prizrakk.prizrakkplugin.handler.PlayerEvent;
import com.prizrakk.prizrakkplugin.handler.PlayerListen;
import com.prizrakk.prizrakkplugin.handler.ServerPing;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import java.sql.SQLException;
import java.util.Objects;
import java.util.logging.Logger;


public final class PrizrakkPlugin extends JavaPlugin implements Listener {

    public PrizrakkPlugin() {
    }
    private static PrizrakkPlugin instance;
    private Database database;
    public Logger log = Logger.getLogger("Minecraft");
    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        log.info(ChatColor.GOLD + "Version: Pre-Release 1.0");
        try {
            ServerPing.main(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            this.database = new Database(this);
            database.initializeDatabase();
        }catch (SQLException ex) {
            log.warning(ChatColor.RED + "Error Database!");
            ex.printStackTrace();
        }
        String mconline = getConfig().getString("config.mconline.enable");
        String enable = String.valueOf(true);
        if (Objects.equals(mconline, enable)) {
            getServer().getPluginManager().registerEvents(new ServerPing(), this);
        } else {
            log.info("MCONLINE not enabled!");
        }
        getServer().getPluginManager().registerEvents(new PlayerListen(this, database), this);
        Bukkit.getPluginManager().registerEvents(new PlayerEvent(), this);

        getServer().getPluginCommand("heal").setExecutor(new HealCommand(this));
        getServer().getPluginCommand("feed").setExecutor(new FeedCommand(this));
        getServer().getPluginCommand("gm").setExecutor(new gmSurvivalCommand(this));
        getServer().getPluginCommand("day").setExecutor(new DayCommand());
        getServer().getPluginCommand("night").setExecutor(new NightCommand(this));
        getServer().getPluginCommand("prizrakk").setExecutor(new SystemCommand());
        getServer().getPluginCommand("admin").setExecutor(new AdminWarnCommand());
    }


    @Override
    public void onDisable() {}

    public static PrizrakkPlugin getInstance() {
        return instance;
    }
}

