package com.prizrakk.prizrakkplugin;

import com.prizrakk.prizrakkplugin.discord.events.ChatListener;
import com.prizrakk.prizrakkplugin.discord.events.MessageListener;
import com.prizrakk.prizrakkplugin.events.ChatMessage;
import com.prizrakk.prizrakkplugin.commands.*;
import com.prizrakk.prizrakkplugin.config.MessageConfig;
import com.prizrakk.prizrakkplugin.config.PrefixConfig;
import com.prizrakk.prizrakkplugin.db.Database;
import com.prizrakk.prizrakkplugin.events.PlayerEvent;
import com.prizrakk.prizrakkplugin.events.PlayerListen;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.*;
import java.sql.SQLException;
import java.util.logging.Logger;


public final class PrizrakkPlugin extends JavaPlugin implements Listener {




    public PrizrakkPlugin() {
    }
    private static PrizrakkPlugin instance;
    private Database database;
    public Logger log = Logger.getLogger("Minecraft");
    public PluginDescriptionFile pdf = this.getDescription();


    private static JDA jda;
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
        MessageConfig.get().addDefault("message.discord.embed-start.title", "Запущен!");
        MessageConfig.get().addDefault("message.discord.embed-start.description", "Сервер майнкрафт успешно был запущен! а также Discord интеграция!");
        MessageConfig.get().addDefault("message.discord.embed-stop.title", "Прощай!");
        MessageConfig.get().addDefault("message.discord.embed-stop.description", "Сервер майнкрафт отключился! эй это не повод растраиватся может просто перезапускается!");
        MessageConfig.get().addDefault("message.discord.events.join", "%prefix% %player% зашел на сервер!");
        MessageConfig.get().addDefault("message.discord.events.left", "%prefix% %player% вышел с сервера!");
        MessageConfig.get().addDefault("message.discord.send-discord-to-minecraft" , "&6[&9DISCORD&6] &f %username% &5>>&f %message%");
        MessageConfig.get().options().copyDefaults(true);
        MessageConfig.save();

        PrefixConfig.setup();
        PrefixConfig.get().addDefault("president.prefix", "§6§lПрезидент §f");
        PrefixConfig.get().addDefault("president.prefix-discord", "Президент ");
        PrefixConfig.get().addDefault("president.chat-format", "%prefix% %player% &6>>&f %message%");
        PrefixConfig.get().addDefault("default.prefix", "§8§lЖитель §f");
        PrefixConfig.get().addDefault("default.prefix-discord", "Житель ");
        PrefixConfig.get().addDefault("default.chat-format", "%prefix% %player% &6>>&f %message%");
        PrefixConfig.get().options().copyDefaults(true);
        PrefixConfig.save();

        getLogger().info(
                "\n" + ChatColor.BLUE + "====================================="
                        + "\n" + " "
                        + "\n" + ChatColor.GOLD + "Version: " + pdf.getVersion()
                        + "\n" + "Плагин сырой и не рекомендуется на профессиональных проектах"
                        + "\n" + "GitHub: https://github.com/Dev-prizrakk/PrizrakkPlugin"
                        + "\n" + "Language RU error? FIX: https://rubukkit.org/threads/podderzhka-kirillicy-serverom-2.32312/"
                        + "\n" + " "
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



        if (getConfig().getBoolean("config.enable") == false) {
            getLogger().warning(ChatColor.RED + "Плагин отключен в конфигурациях!");
            getPluginLoader().disablePlugin(this);
        }
        if (getConfig().getBoolean("config.discord.enable") == true) {
            try {
                jda = JDABuilder.createDefault(getConfig().getString("config.discord.token"))
                        .setStatus(OnlineStatus.ONLINE)
                        .setActivity(Activity.playing(getConfig().getString("config.discord.status")))
                        .enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT)
                        .addEventListeners(new MessageListener(this))
                        .build().awaitReady();
            } catch (InterruptedException e) {
                if (getConfig().getBoolean("config.debug") == true) {
                    e.printStackTrace();
                }
            }
            sendStartEmbed();
            getServer().getPluginManager().registerEvents(new ChatListener(this), this);
            getServer().getPluginManager().registerEvents(new PlayerListen(this, database), this);
        }else {
            getLogger().info("Discord integration was not initialized because it was turned off in the configuration");
        }
    }




    @Override
    public void onDisable() {
        if(getConfig().getBoolean("config.discord.enable") == true) {
            sendStopEmbed();
        }
    }
    public static PrizrakkPlugin getInstance() {
        return instance;
    }
    private void sendStartEmbed(){
        TextChannel channel = jda.getTextChannelById(getConfig().getString("config.discord.chat"));
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(new Color(0, 255, 0, 255));
        embed.setTitle(MessageConfig.get().getString("message.discord.embed-start.title"));
        embed.setDescription(MessageConfig.get().getString("message.discord.embed-start.description"));
        channel.sendMessageEmbeds(embed.build()).queue();
    }
    private void sendStopEmbed(){
        TextChannel channel = jda.getTextChannelById(getConfig().getString("config.discord.chat"));
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(new Color(255, 0, 0, 255));
        embed.setTitle(MessageConfig.get().getString("message.discord.embed-stop.title"));
        embed.setDescription(MessageConfig.get().getString("message.discord.embed-stop.description"));
        channel.sendMessageEmbeds(embed.build()).queue();
    }
    public static JDA getJda() {
        return jda;
    }

}

