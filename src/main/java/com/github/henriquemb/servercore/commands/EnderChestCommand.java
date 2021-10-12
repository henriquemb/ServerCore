package com.github.henriquemb.servercore.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.github.henriquemb.servercore.Main;
import com.github.henriquemb.servercore.Model;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

@CommandAlias("enderchest|ec") @CommandPermission("servercore.enderchest")
public class EnderChestCommand extends BaseCommand {
    private final Model m = Main.getModel();
    private static final FileConfiguration config = Main.getMain().getMessages();

    @Default @CommandCompletion("@players") @Syntax("[jogador]")
    public void onCommand(Player p, @Flags("other") @Optional Player t) {
        if (t != null && !p.hasPermission("servercore.enderchest.other")) {
            m.sendMessage(p, PlaceholderAPI.setPlaceholders(p, config.getString("permission.no-permission")));
            return;
        }

        if (t == null) {
            m.sendMessage(p, PlaceholderAPI.setPlaceholders(p, config.getString("enderchest.success")));
            p.openInventory(p.getEnderChest());
            return;
        }

        m.sendMessage(p, PlaceholderAPI.setPlaceholders(t, config.getString("enderchest.other.success")));
        p.openInventory(t.getEnderChest());
    }
}
