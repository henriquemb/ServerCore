package com.github.henriquemb.servercore.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.github.henriquemb.servercore.Main;
import com.github.henriquemb.servercore.Model;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

@CommandAlias("tphere") @CommandPermission("servercore.tphere")
public class TeleportHereCommand extends BaseCommand {
    private final Model m = Main.getModel();
    private static final FileConfiguration config = Main.getMain().getMessages();

    @Default @CommandCompletion("@players") @Syntax("<jogador>")
    public void onTeleportHere(Player p, @Flags("other") Player t) {
        Location l = p.getLocation();
        t.teleport(l);
        m.sendMessage(p, PlaceholderAPI.setPlaceholders(t, config.getString("teleport.tphere")));
    }
}
