package com.github.henriquemb.servercore.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.github.henriquemb.servercore.Main;
import com.github.henriquemb.servercore.Model;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

@CommandAlias("tploc") @CommandPermission("servercore.tploc")
public class TeleportLocationCommand extends BaseCommand {
    private final Model m = Main.getModel();
    private static final FileConfiguration config = Main.getMain().getMessages();

    @Default @CommandCompletion("@worlds @players") @Syntax("<mundo> <x> <y> <z> [jogador]")
    public void onCommand(Player p, World w, double x, double y, double z, @Flags("other") @Optional Player t) {
        final Location l = new org.bukkit.Location(w, x, y, z);

        if (t != null) {
            t.teleport(l);
            m.sendMessage(p, PlaceholderAPI.setPlaceholders(p, config.getString("teleport.tploc")));
            return;
        }

        p.teleport(l);
        m.sendMessage(p, PlaceholderAPI.setPlaceholders(p, config.getString("teleport.tploc")));
    }
}
