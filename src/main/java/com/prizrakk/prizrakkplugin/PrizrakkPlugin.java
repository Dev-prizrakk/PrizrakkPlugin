package com.prizrakk.prizrakkplugin;

import com.prizrakk.prizrakkplugin.Discord.commands.WhoIsOnlineCommand;
import com.prizrakk.prizrakkplugin.Discord.event.ChatMessage;
import com.prizrakk.prizrakkplugin.commands.*;
import com.prizrakk.prizrakkplugin.db.Database;
import com.prizrakk.prizrakkplugin.handler.PlayerEvent;
import com.prizrakk.prizrakkplugin.handler.PlayerListen;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import java.sql.SQLException;
import java.util.logging.Logger;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.listener.GloballyAttachableListener;

public final class PrizrakkPlugin extends JavaPlugin implements Listener {

    private DiscordApi api;


    public PrizrakkPlugin() {
    }
    private static PrizrakkPlugin instance;
    private Database database;
    public Logger log = Logger.getLogger("Minecraft");
    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        double version = 1.2;
        getLogger().info(
                "\n" + ChatColor.BLUE + "====================================="
                + "\n" + ""
                + "\n" + ChatColor.GOLD + "Version: " + version
                + "\n" + "Плагин сырой и не рекомендуется на профессиональных проектах"
                + "\n" + "GitHub: https://github.com/Dev-prizrakk/PrizrakkPlugin"
                + "\n" + "Language RU error? FIX: https://rubukkit.org/threads/podderzhka-kirillicy-serverom-2.32312/"
                + "\n" + ""
                + "\n" + ChatColor.BLUE + "=====================================");
        getLogger().info(" ");
        getLogger().info("Включаем дискорд интеграцию.....");
        getLogger().info("Проверка подключения к базе данных.........");
        try {
            this.database = new Database(this);
            database.initializeDatabase();
        }catch (SQLException ex) {
            log.warning(ChatColor.RED + "Error Database!");
            if (getConfig().getBoolean("config.debug") == true) {
                ex.printStackTrace();
            }
        }
        getServer().getPluginManager().registerEvents(new PlayerListen(this, database), this);
        Bukkit.getPluginManager().registerEvents(new PlayerEvent(database, this), this);
        Bukkit.getPluginManager().registerEvents(new ChatMessage(database, this), this);

        getServer().getPluginCommand("heal").setExecutor(new HealCommand());
        getServer().getPluginCommand("feed").setExecutor(new FeedCommand());
        getServer().getPluginCommand("gm").setExecutor(new gmSurvivalCommand(this));
        getServer().getPluginCommand("day").setExecutor(new DayCommand());
        getServer().getPluginCommand("night").setExecutor(new NightCommand());
        getServer().getPluginCommand("prizrakk").setExecutor(new SystemCommand());
        getServer().getPluginCommand("admin").setExecutor(new AdminWarnCommand(database, this));
        getServer().getPluginCommand("stats").setExecutor(new StatsCommand(database));
        getServer().getPluginCommand("god").setExecutor(new GodCommand());

        if (getConfig().getBoolean("config.discord.enable") == true) {
            new DiscordApiBuilder()
                    .setToken(getConfig().getString("config.discord.token")) // Set the token of the bot here
                    .login() // Log the bot in
                    .thenAccept(this::onConnectToDiscord) // Call #onConnectToDiscord(...) after a successful login
                    .exceptionally(error -> {
                        // Log a warning when the login to Discord failed (wrong token?)
                        getLogger().warning("Failed to connect to Discord! Disabling plugin!");
                        getPluginLoader().disablePlugin(this);
                        return null;
                    });
        }
        if (getConfig().getBoolean("config.enable") == false) {
            getLogger().warning(ChatColor.RED + "Плагин отключен в конфигурациях!");
            getPluginLoader().disablePlugin(this);
        }
    }



    @Override
    public void onDisable() {
        if (api != null) {
            // Make sure to disconnect the bot when the plugin gets disabled
            api.disconnect();
            api = null;
        }
    }

    public static PrizrakkPlugin getInstance() {
        return instance;
    }
    private void onConnectToDiscord(DiscordApi api) {
        this.api = api;

        // Log a message that the connection was successful and log the url that is needed to invite the bot
        getLogger().info("Connected to Discord as " + api.getYourself().getDiscriminatedName());
        getLogger().info("Open the following url to invite the bot: " + api.createBotInvite());

        api.addListener(new WhoIsOnlineCommand());
        api.addListener((GloballyAttachableListener) new ChatMessage(database, this));
    }
}

