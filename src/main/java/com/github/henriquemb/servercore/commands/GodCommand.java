package com.github.henriquemb.servercore.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.github.henriquemb.servercore.Main;
import com.github.henriquemb.servercore.Model;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

@CommandAlias("god") @CommandPermission("servercore.god")
public class GodCommand extends BaseCommand {
    private final Model m = Main.getModel();
    private static final FileConfiguration config = Main.getMain().getMessages();

    @Default @CatchUnknown @CommandCompletion("@players") @Syntax("[jogador]")
    public void onGod(Player p, @Flags("other") @Optional Player t) {

        if (t == null) {
            if (m.getGod().contains(p)) {
                m.sendMessage(p, PlaceholderAPI.setPlaceholders(p, config.getString("god.disabled")));
                m.getGod().remove(p);
            } else {
                m.sendMessage(p, PlaceholderAPI.setPlaceholders(p, config.getString("god.enabled")));
                m.getGod().add(p);
            }
        } else {
            if (m.getGod().contains(p)) {
                m.sendMessage(p, PlaceholderAPI.setPlaceholders(t, config.getString("god.other.disabled")));
                m.getGod().remove(p);
            } else {
                m.sendMessage(p, PlaceholderAPI.setPlaceholders(t, config.getString("god.other.enabled")));
                m.getGod().add(p);
            }
        }
    }
}
