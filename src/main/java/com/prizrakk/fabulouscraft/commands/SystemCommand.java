package com.prizrakk.fabulouscraft.commands;

import com.prizrakk.fabulouscraft.FabulousCraft;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class SystemCommand extends AbstractCommand {

    public SystemCommand(FabulousCraft command) {
        super("prizrakk");
    }

    public SystemCommand() {
        super("prizrakk");
    }
    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(FabulousCraft.getInstance().getConfig().getString("message.prefix") + ChatColor.RED + "/prizrakk help");
            return;
        }
        if (args[0].equalsIgnoreCase("reload")) {
            if(!sender.hasPermission("prizrakk.reload") || !sender.hasPermission("prizrakk.*")) {
                sender.sendMessage(FabulousCraft.getInstance().getConfig().getString("message.prefix") + FabulousCraft.getInstance().getConfig().getString("message.noperm"));
                return;
            }
            //Player player = (Player) sender;
            sender.sendMessage(FabulousCraft.getInstance().getConfig().getString("message.prefix") + FabulousCraft.getInstance().getConfig().getString("message.reload"));
            return;
        }
        if (args[0].equalsIgnoreCase("help")) {
            if(!sender.hasPermission("prizrakk.help") || !sender.hasPermission("prizrakk.*")) {
                sender.sendMessage(FabulousCraft.getInstance().getConfig().getString("message.prefix") + FabulousCraft.getInstance().getConfig().getString("message.noperm"));
                return;
            }
            //Player player = (Player) sender;
            sender.sendMessage("§2=-=-=-=-=-=-=-=§6Fabulous help list§2=-=-=-=-=-=-=-=");
            sender.sendMessage("1. /prizrakk reload - Перезапускает плагин!");
            sender.sendMessage("2. /prizrakk setwarn - выдает выговор администратору");
            sender.sendMessage("3. /prizrakk unwarn - удаляет варн");
            sender.sendMessage("4. /prizrakk guide - легкие примитивные гайды)");
            sender.sendMessage("5. /pirzrakk ........");
            sender.sendMessage("6. /prizrakk ........");
            sender.sendMessage("§2=-=-=-=-=-=-=-=§6Fabulous help list§2=-=-=-=-=-=-=-=");
            return;
        }


        sender.sendMessage(FabulousCraft.getInstance().getConfig().getString("message.prefix") + ChatColor.RED + "Неизвестная подкоманда: " + args[0]);
    }
}
