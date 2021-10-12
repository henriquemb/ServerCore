package com.github.henriquemb.servercore.events;

import com.github.henriquemb.servercore.Main;
import com.github.henriquemb.servercore.Model;
import com.github.henriquemb.servercore.database.controller.PlayerJailController;
import com.github.henriquemb.servercore.database.model.PlayerJail;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class JailListener implements Listener {
    private final Model m = Main.getModel();
    private final PlayerJailController pjController = new PlayerJailController();

    private static final FileConfiguration config = Main.getMain().getMessages();

    @EventHandler
    public void onJoin(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();

        PlayerJail pj = pjController.findByPlayerAndActive(p.getName());

        if (pj != null && !p.hasPermission("servercore.jail.bypass")) {
            m.sendMessage(p, PlaceholderAPI.setPlaceholders(p, config.getString("jail.disable-cmds")));
            e.setCancelled(true);
        }
    }
}
