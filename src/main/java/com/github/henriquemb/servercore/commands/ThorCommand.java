package com.github.henriquemb.servercore.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.github.henriquemb.servercore.Main;
import com.github.henriquemb.servercore.Model;
import org.bukkit.entity.Player;

@CommandAlias("thor") @CommandPermission("servercore.thor")
public class ThorCommand extends BaseCommand {
    private final Model m = Main.getModel();

    @Default @CommandCompletion("@players") @Syntax("[jogador]")
    public void onCommand(Player p,  @Flags("other") @Optional Player t) {
        if (t != null) {
            t.getWorld().strikeLightningEffect(t.getLocation());
            return;
        }

        p.getWorld().strikeLightningEffect(p.getTargetBlock(null, 600).getLocation());
    }
}
