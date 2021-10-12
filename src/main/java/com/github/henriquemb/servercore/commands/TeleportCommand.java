package com.github.henriquemb.servercore.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.github.henriquemb.servercore.Main;
import com.github.henriquemb.servercore.Model;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

@CommandAlias("tp") @CommandPermission("servercore.tp")
public class TeleportCommand extends BaseCommand {
    private final Model m = Main.getModel();
    private static final FileConfiguration config = Main.getMain().getMessages();

    @Default @CommandCompletion("@players") @Syntax("<jogador>")
    public void onCommand(Player p, @Flags("other") Player t) {
        Location l = t.getLocation();
        p.teleport(l);
        m.sendMessage(p, PlaceholderAPI.setPlaceholders(t, config.getString("teleport.tp")));
    }
}
