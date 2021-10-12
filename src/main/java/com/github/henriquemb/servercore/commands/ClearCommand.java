package com.github.henriquemb.servercore.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.github.henriquemb.servercore.Main;
import com.github.henriquemb.servercore.Model;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

@CommandAlias("clear") @CommandPermission("servercore.clear")
public class ClearCommand extends BaseCommand {
    private final Model m = Main.getModel();
    private static final FileConfiguration config = Main.getMain().getMessages();

    @Default @CommandCompletion("@players") @Syntax("[jogador]")
    public void onCommand(Player p, @Flags("other") @Optional Player t) {
        if (t == null) {
            p.getInventory().clear();
            m.sendMessage(p, PlaceholderAPI.setPlaceholders(p, config.getString("clear.success")));
        } else {
            t.getInventory().clear();
            m.sendMessage(p, PlaceholderAPI.setPlaceholders(t, config.getString("clear.other.success")));
            m.sendMessage(t, PlaceholderAPI.setPlaceholders(p, config.getString("clear.other.alert")));
        }
    }
}
