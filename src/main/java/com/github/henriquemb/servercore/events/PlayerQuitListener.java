package com.github.henriquemb.servercore.events;

import com.github.henriquemb.servercore.Main;
import com.github.henriquemb.servercore.Model;
import com.github.henriquemb.servercore.database.controller.PlayerJailController;
import com.github.henriquemb.servercore.database.model.PlayerJail;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class PlayerQuitListener implements Listener {
    private final Model m = Main.getModel();

    private static final FileConfiguration config = Main.getMain().getMessages();

    private String diff(PlayerJail pj) {
        long diff = new Date(System.currentTimeMillis()).getTime() - pj.getTimestamp().getTime();
        long s = diff / 1000 % 60;
        long min = diff / (60 * 1000) % 60;
        long h = diff / (60 * 60 * 1000) % 24;

        String time = "";

        if (h == 1) time += String.format("%d %s", h, config.getString("jail.time.hour"));
        if (h > 1) time += String.format("%d %s", h, config.getString("jail.time.hours"));

        if (min == 1) time += String.format("%d %s", h, config.getString("jail.time.minute"));
        if (min > 1) time += String.format("%d %s", h, config.getString("jail.time.minutes"));

        if (s > 0) time += String.format("%d %s", s, config.getString("jail.time.seconds"));

        return time;
    }

    @EventHandler
    public void onQuit(org.bukkit.event.player.PlayerQuitEvent e) {
        Player p = e.getPlayer();

        if (Main.getMain().getDbCoonection() != null) {
            PlayerJailController pjController = new PlayerJailController();
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

            PlayerJail pj = pjController.findByPlayerAndActive(p.getName());

            if (pj != null) {
                pjController.toggleActive(pj.getId());

                Bukkit.getOnlinePlayers().forEach(player -> {
                    if (player.hasPermission("servercore.jail.use")) {
                        String msg = config.getString("jail.disconnect");
                        msg = msg.replace("%sc_time%", diff(pj));

                        m.sendMessage(player, PlaceholderAPI.setPlaceholders(p, msg));
                    }
                });
            }
        }

        m.getVanish().remove(p);
        m.getGod().remove(p);
        m.getFly().remove(p);
        p.getInventory().clear();
        e.setQuitMessage(null);
    }
}
