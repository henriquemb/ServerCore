package com.github.henriquemb.servercore.events;

import com.github.henriquemb.servercore.Main;
import com.github.henriquemb.servercore.Model;
import com.github.henriquemb.servercore.structure.JailStructure;
import com.github.henriquemb.servercore.utils.TimeUtils;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class PlayerQuitListener implements Listener {
    private final Model m = Main.getModel();

    private static final FileConfiguration config = Main.getMain().getMessages();

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

        JailStructure jail = m.getJails().get(p);

        if (jail != null) {
            jail.getBlockLocations().forEach(loc -> {
                loc.getBlock().setType(Material.AIR);
            });

            Bukkit.getOnlinePlayers().forEach(player -> {
                if (player.hasPermission("servercore.jail.use")) {
                    String msg = config.getString("jail.disconnect");
                    msg = msg.replace("%sc_time%", TimeUtils.millisecondsToStringTime(System.currentTimeMillis() - jail.getTimestamp()));

                    m.sendMessage(player, PlaceholderAPI.setPlaceholders(p, msg));
                }
            });

            m.getJails().remove(p);
        }

        if (m.getAdminMode().containsKey(p)) {
            ItemStack[] items = m.getAdminMode().get(p);
            for (int i = 0; i < 9; i++) {
                p.getInventory().setItem(i, items[i]);
            }

            m.getAdminMode().remove(p);
        }

        m.getVanish().remove(p);
        m.getGod().remove(p);
        m.getFly().remove(p);
        m.getJails().remove(p);
        m.getNight().remove(p);
    }
}
