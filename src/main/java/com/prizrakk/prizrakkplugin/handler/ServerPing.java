package com.prizrakk.prizrakkplugin.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Iterator;
public class ServerPing implements Listener {

    /*
        Данный класс пока не буду переписывать на него у меня большие планы!
         */
    public ServerPing() {
    }
    public static int portbind = 11222;
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(portbind), 0);
        server.createContext("/mconline/", (exchange) -> {
            StringBuilder resp_text = new StringBuilder("[");
            Iterator<? extends Player> var3 = Bukkit.getOnlinePlayers().iterator();

            while(true) {
                Player p;
                if (!var3.hasNext()) {
                    if (resp_text.toString().endsWith(",")) {
                        resp_text = new StringBuilder(resp_text.substring(0, resp_text.length() - 1));
                    }

                    resp_text.append("]");
                    doOkResponse(resp_text.toString(), exchange);
                    return;
                }

                p = var3.next();

                resp_text.append("\"").append(p.getDisplayName()).append("\",");
            }
        });
        server.setExecutor(null);
        server.start();
    }

    private static void doOkResponse(String data, HttpExchange exchange) throws IOException {
        doResponse(data, exchange);
    }

    private static void doResponse(String data, HttpExchange exchange) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, data.getBytes().length);
        OutputStream output = exchange.getResponseBody();
        output.write(data.getBytes());
        output.flush();
        exchange.close();
    }

}
