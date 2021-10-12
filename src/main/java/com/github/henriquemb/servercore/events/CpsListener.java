package com.github.henriquemb.servercore.events;

import com.github.henriquemb.servercore.Main;
import com.github.henriquemb.servercore.Model;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class CpsListener implements Listener {
    private final Model m = Main.getModel();

    @EventHandler
    public void onClick(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player)) return;
        if (!m.getCps().containsKey((Player) e.getDamager())) return;

        Player p = (Player) e.getDamager();
        m.getCps().put(p, m.getCps().get(p) + 1);
    }
}
