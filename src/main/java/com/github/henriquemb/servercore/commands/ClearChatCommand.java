package com.github.henriquemb.servercore.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CatchUnknown;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import com.github.henriquemb.servercore.Main;
import com.github.henriquemb.servercore.Model;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

@CommandAlias("clearchat|cc") @CommandPermission("servercore.clearchat")
public class ClearChatCommand extends BaseCommand {
    private final Model m = Main.getModel();
    private static final FileConfiguration config = Main.getMain().getMessages();

    @Default @CatchUnknown
    public void onCommand(Player p) {
        m.broadcastMessage("\n".repeat(2000));

        m.broadcastMessage(PlaceholderAPI.setPlaceholders(p, config.getString("clearchat")));
    }
}
