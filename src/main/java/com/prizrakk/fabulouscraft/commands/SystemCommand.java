package com.prizrakk.fabulouscraft.commands;

import com.prizrakk.fabulouscraft.FabulousCraft;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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


        sender.sendMessage(FabulousCraft.getInstance().getConfig().getString("message.prefix") + ChatColor.RED + "Неизвестная подкоманда: " + args[0]);
    }
}
