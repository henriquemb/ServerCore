package com.github.henriquemb.servercore.events;

import com.github.henriquemb.servercore.Main;
import com.github.henriquemb.servercore.Model;
import com.github.henriquemb.servercore.structure.JailStructure;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class JailListener implements Listener {
    private final Model m = Main.getModel();

    private static final FileConfiguration config = Main.getMain().getMessages();

    @EventHandler
    public void onJoin(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();

        JailStructure jail = m.getJails().get(p);

        if (jail != null && !p.hasPermission("servercore.jail.bypass")) {
            m.sendMessage(p, PlaceholderAPI.setPlaceholders(p, config.getString("jail.disable-cmds")));
            e.setCancelled(true);
        }
    }
}
