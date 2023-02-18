package com.prizrakk.prizrakkplugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.List;

public class GodCommand implements CommandExecutor, Listener {
    private static final List<Player> GODS = new ArrayList<>();
    @Override
    public boolean onCommand(CommandSender s, Command c, String l, String[] a) {
        if (s instanceof Player && s.hasPermission("prizrakk.godmode")) {
            boolean b = (GODS.contains(((Player)s))) ? GODS.remove((Player)s) : GODS.add((Player)s);
            s.sendMessage("§6God mode §c" + (GODS.contains((Player)s) ? "enabled" : "disabled") + "§6.");
        }
        return true;
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        GODS.remove(e.getPlayer());
    }
    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        GODS.remove(e.getPlayer());
    }
}
