package com.github.henriquemb.servercore.events;

import com.github.henriquemb.servercore.Main;
import com.github.henriquemb.servercore.Model;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;

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

        if (!p.hasPlayedBefore() && config.getBoolean("first-access-message")) {
            e.joinMessage(null);

            m.sendMessage(p, PlaceholderAPI.setPlaceholders(p, messages.getString("first-access-message")));
        }

        if (p.hasPlayedBefore() && config.getBoolean("join-message")) {
            e.joinMessage(null);

            if (!p.hasPermission("servercore.join.bypass")) {
                m.broadcastMessage(PlaceholderAPI.setPlaceholders(p, messages.getString("join-message")));
            }
        }

        if (config.getBoolean("join-private-message")) {
            e.joinMessage(null);

            StringBuilder str = new StringBuilder();
            List<String> msg = messages.getStringList("join-private-message");

            for (int i = 0; i < msg.size(); i++) {
                str.append(msg.get(i));

                if (i < msg.size() - 1)
                    str.append("\n");
            }

            m.sendMessage(p, PlaceholderAPI.setPlaceholders(p, str.toString()));
        }

        if (pl.getConfig().getBoolean("spawn-command-on-join")) p.performCommand("spawn");

        if (p.hasPermission("servercore.vanish")) p.performCommand("v");
        if (p.hasPermission("servercore.fly")) p.performCommand("fly");
    }
}
