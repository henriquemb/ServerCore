package com.github.henriquemb.servercore.events;

import com.github.henriquemb.servercore.Main;
import com.github.henriquemb.servercore.Model;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    private final Main pl = Main.getMain();
    private final Model m = Main.getModel();
    private static final FileConfiguration config = Main.getMain().getMessages();

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        e.setJoinMessage(null);

        if (!p.hasPermission("servercore.vanish")) {
            m.getVanish().forEach(player -> {
                p.hidePlayer(pl, player);
            });
        }

        if (!p.hasPermission("servercore.join.bypass"))
            m.broadcastMessage(PlaceholderAPI.setPlaceholders(p, config.getString("join-message")));

        if (pl.getConfig().getBoolean("spawn-command-on-join")) p.performCommand("spawn");

        Bukkit.getScheduler().runTaskLater(pl, () -> {
            if (p.hasPermission("servercore.vanish")) p.performCommand("v");
            if (p.hasPermission("servercore.fly")) p.performCommand("fly");
        }, 8); // delay em tick, 20 ticks = 1 segundo
    }
}
