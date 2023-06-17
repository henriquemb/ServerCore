package com.github.henriquemb.servercore.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.github.henriquemb.servercore.Main;
import com.github.henriquemb.servercore.Model;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

@CommandAlias("crash") @CommandPermission("servercore.crash")
public class CrashCommand extends BaseCommand {
    private final Model m = Main.getModel();
    private static final FileConfiguration config = Main.getMain().getMessages();

    @Default @CatchUnknown @CommandCompletion("@players") @Syntax("<jogador>")
    public void onCommand(Player p, @Flags("other") Player t) {
        if(p.equals(t)) {
            m.sendMessage(p, PlaceholderAPI.setPlaceholders(p, config.getString("permission.cannot-execute-youself")));
            return;
        }

        if(t.hasPermission("servercore.crash.bypass")) {
            m.sendMessage(p, PlaceholderAPI.setPlaceholders(p, config.getString("permission.bypass")));
            return;
        }

        Location loc = t.getLocation();
        t.spawnParticle(Particle.EXPLOSION_HUGE, loc, Integer.MAX_VALUE);

        m.sendMessage(p, PlaceholderAPI.setPlaceholders(t, config.getString("crash.success")));
    }
}
