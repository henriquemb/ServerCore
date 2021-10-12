package com.github.henriquemb.servercore.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CatchUnknown;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import com.github.henriquemb.servercore.Main;
import com.github.henriquemb.servercore.Model;
import org.bukkit.entity.Player;

@CommandPermission("servercore.craftingtable")
@CommandAlias("craftingtable|mesa|craft")
public class CraftCommand extends BaseCommand {
    private final Model m = Main.getModel();

    @Default @CatchUnknown
    public void onCommand(Player p) {
        p.openWorkbench(p.getLocation(), true);
    }
}
