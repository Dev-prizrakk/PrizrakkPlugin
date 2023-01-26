package com.prizrakk.prizrakkplugin.commands;

import com.prizrakk.prizrakkplugin.PrizrakkPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class AdminWarnCommand extends AbstractCommand {

    public AdminWarnCommand() {
        super("admin");
    }
    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(PrizrakkPlugin.getInstance().getConfig().getString("message.prefix") + ChatColor.RED + "/prizrakk help");
            return;
        }
        if (args.length == 1){
            if (args[0].equalsIgnoreCase("setwarn")) {
                sender.sendMessage(PrizrakkPlugin.getInstance().getConfig().getString("message.prefix") + "Soon..");
            }
            if (args[0].equalsIgnoreCase("delwarn")) {
                sender.sendMessage(PrizrakkPlugin.getInstance().getConfig().getString("message.prefix") + "Soon..");
            }
            sender.sendMessage(PrizrakkPlugin.getInstance().getConfig().getString("message.prefix") + ChatColor.RED + "Неизвестная подкоманда: " + args[0]);
        }


        sender.sendMessage(PrizrakkPlugin.getInstance().getConfig().getString("message.prefix") + ChatColor.RED + "Неизвестная подкоманда: " + args[0]);
    }
}
