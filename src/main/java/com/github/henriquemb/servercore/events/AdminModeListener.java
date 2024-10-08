package com.github.henriquemb.servercore.events;

import com.github.henriquemb.servercore.Main;
import com.github.henriquemb.servercore.Model;
import com.github.henriquemb.servercore.utils.AdminModeItems;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class AdminModeListener implements Listener {
    private final AdminModeItems modeItems = new AdminModeItems();
    private final Main pl = Main.getMain();
    private final Model m = Main.getModel();

    @EventHandler
    public void onUse(PlayerInteractEntityEvent e) {
        if (!(e.getRightClicked() instanceof Player)) return;
        if (!m.getAdminMode().containsKey(e.getPlayer())) return;

        Player p = e.getPlayer();
        Player t = (Player) e.getRightClicked();

        if (!modeItems.contains(p.getInventory().getItemInMainHand())) return;

        ItemStack itemInHand = p.getInventory().getItemInMainHand();
        if (modeItems.getItem("rotate").equals(itemInHand)) {
            if (pl.getServer().getPluginManager().getPlugin("Vulcan") != null)
                p.performCommand(String.format("vulcan rotate %s", t.getName()));
        } else if (modeItems.getItem("jail").equals(itemInHand)) {
            p.performCommand(String.format("jail %s", t.getName()));
        } else if (modeItems.getItem("cps").equals(itemInHand)) {
            p.performCommand(String.format("cps %s", t.getName()));
        }
    }

    @EventHandler
    public void onMove(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();

        if ((e.getSlot() >= 0 && e.getSlot() <= 8) && e.getInventory() == e.getInventory() && m.getAdminMode().containsKey(p)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onClickVanish(PlayerInteractEvent e) {
        if (!m.getAdminMode().containsKey(e.getPlayer())) return;
        if (!modeItems.getItem("vanish").equals(e.getPlayer().getInventory().getItemInMainHand())) return;
        if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) e.getPlayer().performCommand("v");
    }
}
