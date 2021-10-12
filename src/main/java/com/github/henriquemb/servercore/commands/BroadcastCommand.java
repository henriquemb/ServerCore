package com.github.henriquemb.servercore.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Syntax;
import com.github.henriquemb.servercore.Main;
import com.github.henriquemb.servercore.Model;
import org.bukkit.entity.Player;

@CommandAlias("bc|broadcast") @CommandPermission("servercore.broadcast")
public class BroadcastCommand extends BaseCommand {
    private final Model m = Main.getModel();

    @Default @Syntax("<mensagem>")
    public void onBroadCast(Player p, String str) {
        m.broadcastMessage(str);
    }
}
