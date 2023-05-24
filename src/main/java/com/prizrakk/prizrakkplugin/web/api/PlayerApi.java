package com.prizrakk.prizrakkplugin.web.api;

import com.prizrakk.prizrakkplugin.PrizrakkPlugin;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
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
        if (httpExchange.getRequestURI().getPath().equals("/playerlist")) {
            // Получаем список игроков на сервере
            List<String> playerList = plugin.getServer().getOnlinePlayers().stream().map(p -> p.getName()).collect(Collectors.toList());

            // Создаем JSON-объект и добавляем имена игроков в массив
            JSONArray playerArray = new JSONArray();
            if (playerList.isEmpty()) {
                playerArray.add("No players online");
            } else {
                for (String playerName : playerList) {
                    playerArray.add(playerName);
                }
            }
            JSONObject responseJson = new JSONObject();
            responseJson.put("players", playerArray);

            // Преобразуем JSON-объект в строку и отправляем ее клиенту
            String response = responseJson.toJSONString();
            httpExchange.getResponseHeaders().set("Content-Type", "application/json");
            httpExchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = httpExchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}
