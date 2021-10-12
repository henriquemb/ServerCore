package com.github.henriquemb.servercore.events;

import com.github.henriquemb.servercore.Main;
import com.github.henriquemb.servercore.Model;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class GodListener implements Listener {
    private final Model m = Main.getModel();

    @EventHandler
    public void onDamaged(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            if (m.getGod().contains(((Player) e.getEntity()).getPlayer())) {
                e.setCancelled(true);
            }
        }
    }
}
