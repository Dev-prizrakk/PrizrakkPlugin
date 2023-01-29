package com.prizrakk.prizrakkplugin.commands;

import com.prizrakk.prizrakkplugin.PrizrakkPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;


public class SystemCommand extends AbstractCommand {



    public SystemCommand() {
        super("prizrakk");
    }


    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(PrizrakkPlugin.getInstance().getConfig().getString("message.prefix") + ChatColor.RED + "/prizrakk help");
            return;
        }
        if (args[0].equalsIgnoreCase("reload")) {
            if(!sender.hasPermission("prizrakk.reload") || !sender.hasPermission("prizrakk.*")) {
                sender.sendMessage(PrizrakkPlugin.getInstance().getConfig().getString("message.prefix") + PrizrakkPlugin.getInstance().getConfig().getString("message.noperm"));
                return;
            }
            //Player player = (Player) sender;
            sender.sendMessage(PrizrakkPlugin.getInstance().getConfig().getString("message.prefix") + PrizrakkPlugin.getInstance().getConfig().getString("message.reload"));
            return;
        }
        if (args[0].equalsIgnoreCase("help")) {
            if(!sender.hasPermission("prizrakk.help") || !sender.hasPermission("prizrakk.*")) {
                sender.sendMessage(PrizrakkPlugin.getInstance().getConfig().getString("message.prefix") + PrizrakkPlugin.getInstance().getConfig().getString("message.noperm"));
                return;
            }
            //Player player = (Player) sender;
            sender.sendMessage("§2=-=-=-=-=-=-=-=§6PrizrakkPlugin help list§2=-=-=-=-=-=-=-=");
            sender.sendMessage("                     §6Plugin Version 1.0                   ");
            sender.sendMessage("         Support Server: https://discord.gg/U6H9Zw7Fhg      ");
            sender.sendMessage(" ");
            sender.sendMessage("1. /prizrakk reload - Перезапускает плагин!");
            sender.sendMessage("2. /admin setwarn - выдает выговор администратору");
            sender.sendMessage("3. /admin delwarn - удаляет варн");
            sender.sendMessage("4. /pirzrakk perm - показывает список доступных пермишинов в плагине");
            sender.sendMessage("5. /prizrakk ........");
            sender.sendMessage("§2=-=-=-=-=-=-=-=§6PrizrakkPlugin help list§2=-=-=-=-=-=-=-=");
            return;
        }
        if (args[0].equalsIgnoreCase("perm")) {
            sender.sendMessage("§2=-=-=-=-=-=-=-=§6PrizrakkPlugin permission list§2=-=-=-=-=-=-=-=");
            sender.sendMessage("1. prizrakk.reload");
            sender.sendMessage("2. prizrakk.admin.setwarn and prizrakk.admin.delwarn");
            sender.sendMessage("3. prizrakk.admin.delwarn");
            sender.sendMessage("4. prizrakk.time.night and prizrakk.time.day");
            sender.sendMessage("5. prizrakk.feed and prizrakk.heal");
            sender.sendMessage("6. prizrakk.gm");
            sender.sendMessage("7. prizrakk.help");
            sender.sendMessage("§2=-=-=-=-=-=-=-=§6PrizrakkPlugin help list§2=-=-=-=-=-=-=-=");
            return;
        }

        sender.sendMessage(PrizrakkPlugin.getInstance().getConfig().getString("message.prefix") + ChatColor.RED + "Неизвестная подкоманда: " + args[0]);
    }
}
