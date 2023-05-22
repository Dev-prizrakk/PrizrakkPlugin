package com.prizrakk.prizrakkplugin.discord.commands;

import com.prizrakk.prizrakkplugin.PrizrakkPlugin;
import com.prizrakk.prizrakkplugin.events.Lag;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CommandManager extends ListenerAdapter {
    private final PrizrakkPlugin plugin;

    public CommandManager(PrizrakkPlugin plugin) {
        this.plugin = plugin;
    }
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        String command = event.getName();
        if (command.equals("list")) {
            int players_count = plugin.getServer().getOnlinePlayers().size();
            int max_players = plugin.getServer().getMaxPlayers();
            String playersOnline = plugin.getServer().getOnlinePlayers()
                    .stream()
                    .map(Player::getName)
                    .collect(Collectors.joining("\n"));
            EmbedBuilder embed = new EmbedBuilder();
            embed.setColor(new Color(0, 255, 0));
            embed.setAuthor("Online " + players_count + " / " + max_players ,null , null);
            embed.setDescription(playersOnline);
            embed.setFooter("Powered by prizrakk-team");
            event.replyEmbeds(embed.build()).queue();
        } else if(command.equals("serverinfo")) {
            String server_name = plugin.getServer().getName();
            int players_count = plugin.getServer().getOnlinePlayers().size();
            int max_players = plugin.getServer().getMaxPlayers();
            long usedmemory = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory() / 1048576);
            long totalmemory = Runtime.getRuntime().totalMemory() / 1048576;
            double tps = Lag.getTPS();
            String motd = plugin.getServer().getMotd();
            EmbedBuilder embed = new EmbedBuilder();
            embed.setColor(new Color(0, 255, 0));
            embed.setAuthor("Информация сервера" ,null , null);
            embed.setDescription("Название сервера: " + server_name
                    + "\n" + "Количество игроков: " + players_count + " из " + max_players
                    + "\n" + "TPS Сервера: " + tps
                    + "\n" + "MOTD Сервера: " + motd
                    + "\n" + "Используется оперативки: " + usedmemory + " из " + totalmemory);
            embed.setFooter("Powered by prizrakk-team");
            event.replyEmbeds(embed.build()).queue();
        } else if(command.equals("plugininfo")) {
            EmbedBuilder embed = new EmbedBuilder();
            embed.setColor(new Color(0,255,0));
            embed.setTitle("Информация о плагине");
            embed.setDescription("Разработан: " + plugin.pdf.getAuthors()
            + "\n" + "Версия: " + plugin.pdf.getVersion()
            + "\n" + "Об плагине: плагин создан для общего пользование я с целью упростить задачу добавив много функционала одним плагином"
            + "\n" + "GitHub: [**Кликни на меня**](https://github.com/prizrakk-team/PrizrakkPlugin/)"
            + "\n" + "SpigotMC: [**Кликни на меня**](https://www.spigotmc.org/resources/prizrakkplugin.109972/)"
            + "\n" + "Discord Support: [**Кликни на меня**](https://discord.gg/U6H9Zw7Fhg)");
            embed.setFooter("Powered by prizrakk-team");
            event.replyEmbeds(embed.build()).queue();
        } else if(command.equals("reload")) {
            if(!event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
                EmbedBuilder embed = new EmbedBuilder();
                embed.setColor(new Color(255, 0, 0));
                embed.setTitle("Упс....  ERROR 403 FORBIDDEN");
                embed.setDescription("Походу у тебя не прав на эту команду вернись позже когда права появятся");
                embed.setFooter("Powered by prizrakk-team");
                event.replyEmbeds(embed.build());
            }
            EmbedBuilder embed = new EmbedBuilder();
            embed.setColor(new Color(0,255,0));
            embed.setTitle("Перезагружен!");
            embed.setDescription("Конфигурационые файлы были успешно перезапущены!");
            embed.setFooter("Powered by prizrakk-team");
            event.replyEmbeds(embed.build());
        }
    }
    @Override
    public void onGuildReady(@NotNull GuildReadyEvent event) {
        List<CommandData> commandData = new ArrayList<>();
        //OptionData option1 = new OptionData(OptionType.STRING, "nickname", "Укажите ник игрока чтобы бот мог найти его в базе данных и дать статистику", true);
        commandData.add(Commands.slash("list", "Показывает список игроков на сервере"));
        commandData.add(Commands.slash("serverinfo", "Показывает информацию о сервере"));
        commandData.add(Commands.slash("plugininfo", "Показывает информацию о плагине"));
        commandData.add(Commands.slash("reload", "Перезапускает конфигруции"));
        //commandData.add(Commands.slash("stats", "Отображает статистику игрока").addOptions(option1));
        event.getGuild().updateCommands().addCommands(commandData).queue();
    }

}
