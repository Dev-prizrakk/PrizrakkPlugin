package com.prizrakk.prizrakkplugin.web;

import com.prizrakk.prizrakkplugin.PrizrakkPlugin;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


public class WebRun implements HttpHandler {
    public WebRun(PrizrakkPlugin plugin) {
        this.plugin = plugin;
    }
    private final PrizrakkPlugin plugin;
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String requestURI = httpExchange.getRequestURI().toString();
        if (requestURI.equals("/")) {
            requestURI = "/index.html";
        }
        // Путь к файлу в вашем плагине
        File file = new File(plugin.getDataFolder(), "web-site" + requestURI);
        if (file.exists() && !file.isDirectory()) {
            FileInputStream fis = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            fis.close();

            httpExchange.sendResponseHeaders(200, data.length);
            httpExchange.getResponseBody().write(data);
        } else {
            String response = "File not found";
            httpExchange.sendResponseHeaders(404, response.length());
            httpExchange.getResponseBody().write(response.getBytes());
        }
        httpExchange.close();
    }
}
