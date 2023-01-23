package com.prizrakk.fabulouscraft.commands;

import com.prizrakk.fabulouscraft.FabulousCraft;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AdminWarnCommand implements CommandExecutor {

    public String getAdmin_nick() {
        return admin_nick;
    }

    public void setAdmin_nick(String admin_nick) {
        this.admin_nick = admin_nick;
    }

    public String getWarn_reason() {
        return warn_reason;
    }

    public void setWarn_reason(String warn_reason) {
        this.warn_reason = warn_reason;
    }

    public String getOwner_by() {
        return owner_by;
    }

    public void setOwner_by(String owner_by) {
        this.owner_by = owner_by;
    }

    public FabulousCraft getPlugin() {
        return plugin;
    }

    public AdminWarnCommand(String admin_nick, String warn_reason, String owner_by, FabulousCraft plugin) {
        this.admin_nick = admin_nick;
        this.warn_reason = warn_reason;
        this.owner_by = owner_by;
        this.plugin = plugin;
    }

    private String admin_nick;
    private String warn_reason;
    private String owner_by;
    private final FabulousCraft plugin;
    public AdminWarnCommand(FabulousCraft plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if (!player.hasPermission("prizrakk.admin.staff.warn") || !sender.hasPermission("prizrakk.*")) {
            sender.sendMessage(plugin.getConfig().getString(plugin.getConfig().getString("message.prefix") + "message.noperm"));
            return true;
        }
        if (command.getName().equalsIgnoreCase("adm_warn")) {
            String sql = "";
            player.sendMessage(plugin.getConfig().getString(plugin.getConfig().getString("message.prefix") + "message.reload"));
        }

        return true;
    }
}
