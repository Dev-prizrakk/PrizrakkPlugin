package com.prizrakk.prizrakkplugin;

import com.prizrakk.prizrakkplugin.Discord.commands.WhoIsOnlineCommand;
import com.prizrakk.prizrakkplugin.Discord.event.ChatMessage;
import com.prizrakk.prizrakkplugin.commands.*;
import com.prizrakk.prizrakkplugin.config.MessageConfig;
import com.prizrakk.prizrakkplugin.config.PrefixConfig;
import com.prizrakk.prizrakkplugin.db.Database;
import com.prizrakk.prizrakkplugin.event.PlayerEvent;
import com.prizrakk.prizrakkplugin.event.PlayerListen;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import java.sql.SQLException;
import java.util.logging.Logger;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

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
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        MessageConfig.setup();
        MessageConfig.get().addDefault("message.system.prefix", "§6[§4PrizrakkPlugin§6] §f");
        MessageConfig.get().addDefault("message.system.no-perm", "У вас не прав на эту команду!");
        MessageConfig.get().addDefault("message.system.reload", "Плагин перезапущен!");
        MessageConfig.get().addDefault("message.system.no-console", "Команда доступна только игроку!");
        MessageConfig.get().addDefault("message.system.offline", "Этот игрок оффлайн");
        MessageConfig.get().addDefault("message.system.error", "Произошла ошибка при выполнение команды!");
        MessageConfig.get().addDefault("message.admin.add-warn", "Вы успешно выдали варн игроку: %player%");
        MessageConfig.get().addDefault("message.admin.del-warn", "Вы успешно удалили варн игроку: %player%");
        MessageConfig.get().addDefault("message.admin.add-warn-view", "Вы получили варн! от Администратора %admin% !");
        MessageConfig.get().addDefault("message.admin.del-warn-view", "Вам сняли варн! Ведите себя хорошо!");
        MessageConfig.get().addDefault("message.admin.reason.warn-count", "Вы были кикнуты т.к у вас допущено более %warncount% варнов");
        MessageConfig.get().addDefault("message.rep.add-rep", "Вы успешно выдали репутацию игроку: %target-player%");
        MessageConfig.get().addDefault("message.rep.del-rep", "Вы успешно удалили репутацию игроку: %target-player%");
        MessageConfig.get().addDefault("message.rep.add-rep-view", "Вы получили репутацию! от игрока %player% !");
        MessageConfig.get().addDefault("message.rep.del-rep-view", "Вам сняли репутацию это плохо :(!");
        MessageConfig.get().addDefault("message.time.day", "Установлено дневное время");
        MessageConfig.get().addDefault("message.time.night", "Установлено ночное время");
        MessageConfig.get().addDefault("message.event.player-join", "%prefix% %player% зашел на сервер");
        MessageConfig.get().addDefault("message.event.player-left", "%prefix% %player% вышел с сервера");
        MessageConfig.get().addDefault("message.other.health", "Вы исцелены!");
        MessageConfig.get().addDefault("message.other.healed", "Вы исцелили: {player}");
        MessageConfig.get().addDefault("message.other.feed", "Вы были покормлены!");
        MessageConfig.get().addDefault("message.other.feeded", "Вы покормили: {player}");
        MessageConfig.get().addDefault("message.other.gm", "Ваш игровой режим изменился на %game-mode%");
        MessageConfig.get().options().copyDefaults(true);
        MessageConfig.save();

        PrefixConfig.setup();
        PrefixConfig.get().addDefault("president.prefix", "§6§lПрезидент §f");
        PrefixConfig.get().addDefault("president.chat-format", "%prefix% %player% &6>>&f %message%");
        PrefixConfig.get().addDefault("default.prefix", "§8§lЖитель §f");
        PrefixConfig.get().addDefault("default.chat-format", "%prefix% %player% &6>>&f %message%");
        PrefixConfig.get().options().copyDefaults(true);
        PrefixConfig.save();

        getLogger().info(
                "\n" + ChatColor.BLUE + "====================================="
                + "\n" + ""
                + "\n" + ChatColor.GOLD + "Version: 1.3.1" 
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
        getServer().getPluginCommand("prizrakk").setExecutor(new SystemCommand(this));
        getServer().getPluginCommand("admin").setExecutor(new AdminWarnCommand(database, this));
        getServer().getPluginCommand("stats").setExecutor(new StatsCommand(database, this));
        getServer().getPluginCommand("bc").setExecutor(new BroadcastCommand());
        getServer().getPluginCommand("rep").setExecutor(new RepCommand(database, this));
        getServer().getPluginCommand("prefix").setExecutor(new PrefixCommand(database, this));

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
        } else
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
        api.addListener(new ChatMessage(database, this));
    }
}

