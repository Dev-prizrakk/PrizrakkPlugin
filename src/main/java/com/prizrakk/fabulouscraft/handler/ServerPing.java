package com.prizrakk.fabulouscraft.handler;

import com.prizrakk.fabulouscraft.FabulousCraft;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import java.io.OutputStream;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Iterator;
import java.util.concurrent.Executor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
public class ServerPing implements Listener {

private final FabulousCraft plugin;
    public ServerPing(FabulousCraft plugin) {
        this.plugin = plugin;
    }
    public static int portbind = 11222;
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(portbind), 0);
        server.createContext("/mconline/", (exchange) -> {
            String resp_text = "[";
            Iterator var3 = Bukkit.getOnlinePlayers().iterator();

            while(true) {
                Player p;
                if (!var3.hasNext()) {
                    if (resp_text.endsWith(",")) {
                        resp_text = resp_text.substring(0, resp_text.length() - 1);
                    }

                    resp_text = resp_text + "]";
                    doOkResponse(resp_text, exchange);
                    return;
                }

                p = (Player)var3.next();

                resp_text = resp_text + "\"" + p.getDisplayName() + "\",";
            }
        });
        server.setExecutor((Executor)null);
        server.start();
    }

    private static void doOkResponse(String data, HttpExchange exchange) throws IOException {
        doResponse(data, "application/json", true, exchange);
    }

    private static void doResponse(String data, String format, Boolean is_good, HttpExchange exchange) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", format);
        exchange.sendResponseHeaders(is_good ? 200 : 400, (long)data.getBytes().length);
        OutputStream output = exchange.getResponseBody();
        output.write(data.getBytes());
        output.flush();
        exchange.close();
    }

    public static void logMessage(String text, Boolean is_fatal) {
        if (is_fatal) {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "[OnlineMC] " + text);
        } else {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[OnlineMC] " + text);
        }

    }

    public static void logMessage(String text) {
        logMessage(text, false);
    }
}
