package com.prizrakk.prizrakkplugin.commands;

import com.prizrakk.prizrakkplugin.PrizrakkPlugin;
import com.prizrakk.prizrakkplugin.config.MessageConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class SystemCommand extends AbstractCommand {

    public SystemCommand(PrizrakkPlugin plugin) {
        super("prizrakk");
        this.plugin = plugin;
    }
    private final PrizrakkPlugin plugin;


    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        String prefix = ChatColor.translateAlternateColorCodes('&', MessageConfig.get().getString("message.system.prefix"));
        String noperm = ChatColor.translateAlternateColorCodes('&', MessageConfig.get().getString("message.system.no-perm"));
        String reload = ChatColor.translateAlternateColorCodes('&', MessageConfig.get().getString("message.system.reload"));
        if (args.length == 0) {

            sender.sendMessage(prefix + ChatColor.RED + "/prizrakk help");
            return;
        }
        if (args[0].equalsIgnoreCase("reload")) {
            if(!sender.hasPermission("prizrakk.reload") || !sender.hasPermission("prizrakk.*")) {

                sender.sendMessage(prefix + noperm);
                return;
            }
            PrizrakkPlugin.getInstance().reloadConfig();
            //MessageConfig.reload();
            sender.sendMessage(prefix + reload);
            return;
        }
        if (args[0].equalsIgnoreCase("help")) {
            sender.sendMessage("§a=-=-=-=-=-=-=-= §6PrizrakkPlugin help list §a=-=-=-=-=-=-=-="
                    + "\n" + "                       §6Plugin Version: §5" + Bukkit.getVersion()
                    + "\n" + "         §eSupport Server: §6https://discord.gg/U6H9Zw7Fhg      "
                    + "\n" + " "
                    + "\n" + "§91. §2/prizrakk reload §9-§b Перезапускает плагин! §4(prizrakk.reload)"
                    + "\n" + "§92. §2/admin delwarn §9-§b удаляет варн! §4(prizrakk.admin.delwarn)"
                    + "\n" + "§93. §2/admin setwarn §9-§b выдает выговор администратору! §4(prizrakk.admin.setwarn)"
                    + "\n" + "§94. §2/stats §9-§b показывает статистику игрока!"
                    + "\n" + "§95. §2/heal §9-§b востанавливает ваше здоровье! §4(prizrakk.heal)"
                    + "\n" + "§96. §2/feed §9-§b востанавливает ваш голод! §4(prizrakk.feed)"
                    + "\n" + "§97. §2/gm 0/1/2/3 §9-§b изменяет ваш игровой режим! §4(prizrakk.gm)"
                    + "\n" + "§98. §2/day world §9-§b устанавливает дневное время! §4(prizrakk.time.day)"
                    + "\n" + "§99. §2/night world §9-§b устанавливает ночное время суток! §4(prizrakk.time.night)"
                    + "\n" + "§a=-=-=-=-=-=-=-= §6PrizrakkPlugin help list §a=-=-=-=-=-=-=-=");
            if (!(sender instanceof Player)) {
                return;
            } else {
                Player player = (Player) sender;
                plugin.getLogger().info(prefix + " >> " + player.getName() + "used: /prizrakk help");
            }
            return;
        }

        sender.sendMessage(prefix + ChatColor.RED + "Неизвестная подкоманда: " + args[0]);
    }
}
