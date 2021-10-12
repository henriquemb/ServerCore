package com.github.henriquemb.servercore.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.github.henriquemb.servercore.Main;
import com.github.henriquemb.servercore.Model;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

@CommandAlias("fly")
public class FlyCommand extends BaseCommand {
    private final Model m = Main.getModel();
    private static final FileConfiguration config = Main.getMain().getMessages();

    @Default @CatchUnknown @CommandPermission("servercore.fly") @Syntax("[jogador]")
    public void onEnable(Player s, @Flags("other") @Optional Player t) {
        Player p = t != null ? t : s;

        if (!s.hasPermission("servercore.fly.other") && t != null) {
            m.sendMessage(s, PlaceholderAPI.setPlaceholders(p, config.getString("permission.no-permission")));
            return;
        }

        if (!m.getFly().contains(p) || !p.getAllowFlight()) {
            p.setAllowFlight(true);
            m.getFly().add(p);

            if (t == null) m.sendMessage(s, PlaceholderAPI.setPlaceholders(p, config.getString("fly.enabled")));
            else m.sendMessage(s, PlaceholderAPI.setPlaceholders(p, config.getString("fly.other.enabled")));
        } else {
            p.setAllowFlight(false);
            p.setFlying(false);
            m.getFly().remove(p);

            if (t == null) m.sendMessage(s, PlaceholderAPI.setPlaceholders(p, config.getString("fly.disabled")));
            else m.sendMessage(s, PlaceholderAPI.setPlaceholders(p, config.getString("fly.other.disabled")));
        }
    }
}
