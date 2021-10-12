package com.github.henriquemb.servercore.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.github.henriquemb.servercore.Main;
import com.github.henriquemb.servercore.Model;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

@CommandAlias("speed")
public class SpeedCommand extends BaseCommand {
    private final Model m = Main.getModel();
    private static final FileConfiguration config = Main.getMain().getMessages();

    @Default @Subcommand("walk") @CommandPermission("servercore.speed.walk") @Syntax("<1-10>")
    public void onWalk(Player p, int value) {
        if (value <= 0 || value > 10) {
            m.sendMessage(p, PlaceholderAPI.setPlaceholders(p, config.getString("speed.invalid-value")));
            return;
        }

        p.setWalkSpeed(value / 10f);

        m.sendMessage(p, PlaceholderAPI.setPlaceholders(p, config.getString("speed.walk")
                .replace("%sc_velocity%", String.valueOf(value))));
    }

    @Subcommand("fly") @CommandPermission("servercore.speed.fly") @Syntax("<1-10>")
    public void onFly(Player p, int value) {
        if (value <= 0 || value > 10) {
            m.sendMessage(p, PlaceholderAPI.setPlaceholders(p, config.getString("speed.invalid-value")));
            return;
        }

        p.setFlySpeed(value / 10f);

        m.sendMessage(p, PlaceholderAPI.setPlaceholders(p, config.getString("speed.fly")
                .replace("%sc_velocity%", String.valueOf(value))));
    }
}
