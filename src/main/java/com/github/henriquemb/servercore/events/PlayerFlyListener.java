package com.github.henriquemb.servercore.events;

import com.github.henriquemb.servercore.Main;
import com.github.henriquemb.servercore.Model;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;

public class PlayerFlyListener implements Listener {
    private final Model m = Main.getModel();

    @EventHandler
    public void onFly(PlayerToggleFlightEvent e) {
        Player p = e.getPlayer();

        if (!p.hasPermission("servercore.fly") && p.getGameMode() == GameMode.SURVIVAL) {
            p.setFlying(false);
            p.setAllowFlight(false);
            m.getFly().remove(p);
        }
    }
}
