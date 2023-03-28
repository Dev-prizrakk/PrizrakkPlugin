package com.prizrakk.prizrakkplugin.commands;

import com.prizrakk.prizrakkplugin.PrizrakkPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.*;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractCommand implements CommandExecutor, TabCompleter, Listener {

    public AbstractCommand(String command) {
        PluginCommand pluginCommand = PrizrakkPlugin.getInstance().getCommand(command);
        if(pluginCommand != null) {
            pluginCommand.setExecutor(this);
            pluginCommand.setTabCompleter(this);
        }
    }

    public abstract void execute(CommandSender sender, String label, String[] args) throws InterruptedException;

    public List<String> complete() {
        return null;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            execute(sender, label, args);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public  List<String> onTabComplete( CommandSender sender, Command command, String alias, String[] args) {
        return filter(complete(), args);
    }

    private List<String> filter(List<String> list, String[] args) {
        if(list == null) return null;
        String last = args[args.length - 1];
        List<String> result = new ArrayList<>();
        for(String arg : list) {
            if(arg.toLowerCase().startsWith(last.toLowerCase())) result.add(arg);
        }
        return result;
    }

}
