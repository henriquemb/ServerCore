package com.github.henriquemb.servercore.events;

import com.github.henriquemb.servercore.Main;
import com.github.henriquemb.servercore.Model;
import com.github.henriquemb.servercore.utils.AdminModeItems;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class AdminModeListener implements Listener {
    private final AdminModeItems modeItems = new AdminModeItems();
    private final Model m = Main.getModel();

    @EventHandler
    public void onUse(PlayerInteractEntityEvent e) {
        if (!(e.getRightClicked() instanceof Player)) return;
        if (!m.getAdminMode().containsKey(e.getPlayer())) return;

        Player p = e.getPlayer();
        Player t = (Player) e.getRightClicked();

        if (!modeItems.contains(p.getItemInHand())) return;

        ItemStack itemInHand = p.getItemInHand();
        if (modeItems.getItem("rotate").equals(itemInHand)) {
            p.performCommand(String.format("vulcan rotate %s", t.getName()));
        } else if (modeItems.getItem("freeze").equals(itemInHand)) {
            p.performCommand(String.format("jail %s", t.getName()));
        } else if (modeItems.getItem("cps").equals(itemInHand)) {
            p.performCommand(String.format("cps %s", t.getName()));
        }
    }

    @EventHandler
    public void onClickVanish(PlayerInteractEvent e) {
        if (!m.getAdminMode().containsKey(e.getPlayer())) return;
        if (!modeItems.getItem("vanish").equals(e.getPlayer().getItemInHand())) return;
        if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) e.getPlayer().performCommand("v");
    }
}
