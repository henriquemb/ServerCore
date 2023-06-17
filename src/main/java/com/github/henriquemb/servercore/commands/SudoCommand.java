package com.github.henriquemb.servercore.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.github.henriquemb.servercore.Main;
import com.github.henriquemb.servercore.Model;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

@CommandAlias("sudo") @CommandPermission("servercore.sudo")
public class SudoCommand extends BaseCommand {
    private final Model m = Main.getModel();
    private static final FileConfiguration config = Main.getMain().getMessages();

    @Default @CommandCompletion("@players") @Syntax("<jogador> <comando>")
    public void onCommand(CommandSender s, @Flags("other") Player t, String cmd) {
        Player p = (Player) s;

        if (t.hasPermission("servercore.sudo.bypass")) {
            m.sendMessage(p, PlaceholderAPI.setPlaceholders(p, config.getString("permission.bypass")));
            return;
        }

        Bukkit.dispatchCommand(t, cmd.trim());
        m.sendMessage(p, PlaceholderAPI.setPlaceholders(t, config.getString("sudo")));
    }
}
