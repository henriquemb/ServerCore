package com.github.henriquemb.servercore.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.github.henriquemb.servercore.Main;
import com.github.henriquemb.servercore.Model;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

@CommandPermission("servercore.infoplayer") @CommandAlias("infoplayer|checkplayer")
public class InfoPlayerCommand extends BaseCommand {
    private final Model m = Main.getModel();
    private static final FileConfiguration config = Main.getMain().getMessages();

    @Default @CatchUnknown @CommandCompletion("@players") @Syntax("<jogador>")
    public void onCommand(Player p, @Flags("other") Player t) {
        String ip = t.getAddress().getHostString();
        JsonParser jsonParser = new JsonParser();
        JsonObject json;
        String country;
        String region;
        String city;
        String inetProvider;
        String inet;
        boolean mobile;

        try {
            if((json = jsonParser.parse(getStringFromURL(new URL("http://ip-api.com/json/" + ip))).getAsJsonObject()).get("status").getAsString().equals("success")) {
                country = json.get("country").getAsString();
                region = json.get("regionName").getAsString();
                city = json.get("city").getAsString();
                inetProvider = json.get("org").getAsString();
                inet = json.get("isp").getAsString();
                mobile = json.get("isp").getAsBoolean();

                for (String msg : config.getStringList("infoplayer")) {
                    msg = msg.replace("%sc_ip%", ip);
                    msg = msg.replace("%sc_city%", city);
                    msg = msg.replace("%sc_country%", country);
                    msg = msg.replace("%sc_region%", region);
                    msg = msg.replace("%sc_provider%", String.format("%s/%s", inet, inetProvider));
                    msg = msg.replace("%sc_type%", mobile ? "Celular" : "Computador");

                    m.sendMessage(p, PlaceholderAPI.setPlaceholders(t, msg));
                }
            }
        }
        catch (Exception e) {
            m.sendMessage(p, PlaceholderAPI.setPlaceholders(p, "permission.error"));
            e.printStackTrace();
        }
    }

    protected static String getStringFromURL(URL url, String[]... requestProps) throws IOException {
        URLConnection connection = url.openConnection();
        for(String[] requestProp : requestProps)
            connection.addRequestProperty(requestProp[0], requestProp[1]);
        connection.connect();
        StringBuilder builder = new StringBuilder();
        try(InputStream stream = connection.getInputStream(); BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
            String line;
            while((line = reader.readLine()) != null)
                builder.append(line);
        }
        return builder.toString();
    }
}
