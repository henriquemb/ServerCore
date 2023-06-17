package com.github.henriquemb.servercore.events;

import com.github.henriquemb.servercore.Main;
import com.github.henriquemb.servercore.Model;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    private final Main pl = Main.getMain();
    private final Model m = Main.getModel();
    private static final FileConfiguration config = Main.getMain().getConfig();
    private static final FileConfiguration messages = Main.getMain().getMessages();

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        if (!p.hasPermission("servercore.vanish")) {
            m.getVanish().forEach(player -> {
                p.hidePlayer(pl, player);
            });
        }

        if (config.getBoolean("join-message")) {
            e.setJoinMessage(null);

            if (!p.hasPermission("servercore.join.bypass")) {
                m.broadcastMessage(PlaceholderAPI.setPlaceholders(p, messages.getString("join-message")));
            }
        }

        if (pl.getConfig().getBoolean("spawn-command-on-join")) p.performCommand("spawn");

        if (p.hasPermission("servercore.vanish")) p.performCommand("v");
        if (p.hasPermission("servercore.fly")) p.performCommand("fly");
    }
}
