package com.prizrakk.prizrakkplugin.web.api;

import com.prizrakk.prizrakkplugin.PrizrakkPlugin;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.io.OutputStream;
import java.util.stream.Collectors;

public class PlayerApi implements HttpHandler {
    public PlayerApi(PrizrakkPlugin plugin) {
        this.plugin = plugin;
    }
    private final PrizrakkPlugin plugin;
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String requestURI = httpExchange.getRequestURI().toString();
        if (requestURI.equals("/playercount")) {
            int playerCount = plugin.getServer().getOnlinePlayers().size();
            String response = Integer.toString(playerCount);
            httpExchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
        if (requestURI.equals("/playerlist")) {
            String playersOnline = plugin.getServer().getOnlinePlayers()
                    .stream()
                    .map(Player::getName)
                    .collect(Collectors.joining("\n"));
            if (playersOnline == null) {
                playersOnline = "404 Not Found";
            }
            String response = Integer.toString(Integer.parseInt(playersOnline));
            httpExchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}
