package com.github.henriquemb.servercore.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CatchUnknown;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import com.github.henriquemb.servercore.Main;
import com.github.henriquemb.servercore.Model;
import com.github.henriquemb.servercore.utils.AdminModeItems;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@CommandAlias("adminmode|am") @CommandPermission("servercore.adminmode")
public class AdminModeCommand extends BaseCommand {
    private final Model m = Main.getModel();
    private static final FileConfiguration config = Main.getMain().getMessages();
    private final AdminModeItems modeItems = new AdminModeItems();

    @Default @CatchUnknown
    public void onCommand(Player p) {
        if (m.getAdminMode().containsKey(p)) {
            ItemStack[] items = m.getAdminMode().get(p);
            for (int i = 0; i < 9; i++) {
                p.getInventory().setItem(i, items[i]);
            }

            m.getAdminMode().remove(p);
            p.performCommand("night");
            m.sendMessage(p, PlaceholderAPI.setPlaceholders(p, config.getString("adminmode.disabled")));
            return;
        }

        ItemStack[] old = new ItemStack[9];
        for (int i = 0; i < 9; i++) {
            old[i] = p.getInventory().getItem(i);
        }

        p.performCommand("night");
        m.getAdminMode().put(p, old);
        loadInventory(p);
        m.sendMessage(p, PlaceholderAPI.setPlaceholders(p, config.getString("adminmode.enabled")));
    }

    private void loadInventory(Player p) {
        p.getInventory().setItem(0, modeItems.getItem("fence"));
        p.getInventory().setItem(1, modeItems.getItem("rotate"));
        p.getInventory().setItem(2, modeItems.getItem("fence"));
        p.getInventory().setItem(3, modeItems.getItem("cps"));
        p.getInventory().setItem(4, modeItems.getItem("fence"));
        p.getInventory().setItem(5, modeItems.getItem("freeze"));
        p.getInventory().setItem(6, modeItems.getItem("fence"));
        p.getInventory().setItem(7, modeItems.getItem("vanish"));
        p.getInventory().setItem(8, modeItems.getItem("fence"));
    }
}
