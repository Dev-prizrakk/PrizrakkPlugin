package com.prizrakk.prizrakkplugin.events;

import java.util.Date;

public class PlayerStats {

    private final String playerNick;
    private String prefix;
    private int rep;

    //random stats on each player
    private int warn_count;
    private int deaths;
    private int kills;
    private long blocksBroken;
    private double balance;

    //last login and logout times
    private Date lastLogin;
    private Date lastLogout;

    public PlayerStats(String playerNick, int warn_count, String prefix, int rep, int deaths, int kills, long blocksBroken, double balance, Date lastLogin, Date lastLogout) {
        this.playerNick = playerNick;
        this.deaths = deaths;
        this.kills = kills;
        this.blocksBroken = blocksBroken;
        this.balance = balance;
        this.lastLogin = lastLogin;
        this.lastLogout = lastLogout;
        this.warn_count = warn_count;
        this.prefix = prefix;
        this.rep = rep;
    }

    public String getPlayerNick() {
        return playerNick;
    }
    public String getPrefix() {
        return prefix;
    }
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public int getDeaths() {
        return deaths;
    }
    public int getWarn_count() {
        return warn_count;
    }
    public void setWarn_count(int warn_count) {
        this.warn_count = warn_count;
    }
    public int getRep() {
        return rep;
    }
    public void setRep(int rep) {
        this.rep = rep;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public long getBlocksBroken() {
        return blocksBroken;
    }

    public void setBlocksBroken(long blocksBroken) {
        this.blocksBroken = blocksBroken;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Date getLastLogout() {
        return lastLogout;
    }

    public void setLastLogout(Date lastLogout) {
        this.lastLogout = lastLogout;
    }

}
