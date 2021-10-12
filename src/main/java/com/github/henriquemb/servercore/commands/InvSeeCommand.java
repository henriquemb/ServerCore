package com.github.henriquemb.servercore.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.github.henriquemb.servercore.Main;
import com.github.henriquemb.servercore.Model;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@CommandAlias("invsee") @CommandPermission("servercore.invsee")
public class InvSeeCommand extends BaseCommand {
    private final Model m = Main.getModel();
    private static final FileConfiguration config = Main.getMain().getMessages();

    @Default @CommandCompletion("@players") @Syntax("<jogador>")
    public void onCommand(Player p, @Flags("other") Player t) {
        m.sendMessage(p, PlaceholderAPI.setPlaceholders(t, config.getString("invsee.open")));
        p.openInventory(t.getInventory());
    }
}
