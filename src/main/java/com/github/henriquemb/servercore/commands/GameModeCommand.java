package com.github.henriquemb.servercore.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.github.henriquemb.servercore.Main;
import com.github.henriquemb.servercore.Model;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.GameMode;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

@CommandPermission("servercore.gamemode")
public class GameModeCommand extends BaseCommand {
    private final Model m = Main.getModel();
    private static final FileConfiguration config = Main.getMain().getMessages();

    @CommandAlias("gamemode|gm") @CommandCompletion("@gamemode @players") @Syntax("<modo> [jogador]")
    public void onCommand(Player p, String mode, @Flags("other") @Optional Player t) {
        GameMode gm = null;

        try {
            int md = Integer.parseInt(mode);
            GameMode[] values = GameMode.values();
            if(md < 0 || md >= values.length) throw new Exception();
            for (GameMode gameMode : values) {
                if (gameMode.getValue() == md) {
                    gm = gameMode;
                    break;
                }
            }
        } catch (Exception e) {
            try {
                gm = GameMode.valueOf(mode.toUpperCase());
            } catch (Exception err) {
                m.sendMessage(p, PlaceholderAPI.setPlaceholders(p, config.getString("gamemode.invalid")));
                return;
            }
        }

        if (t == null) {
            t = p;
        }

        if (t.getGameMode() == gm) {
            m.sendMessage(p, PlaceholderAPI.setPlaceholders(p, config.getString("gamemode.actual").replace("%sc_gm%", gm.name())));
            return;
        }

        t.setGameMode(gm);
        m.sendMessage(t, PlaceholderAPI.setPlaceholders(p, config.getString("gamemode.success").replace("%sc_gm%", gm.name())));
    }

    @CommandAlias("gms") @CommandCompletion("@players") @Syntax("[jogador]")
    public void gmsCommand(Player p, @Flags("other") @Optional Player t) {
        if (t == null) p.performCommand("gm 0");
        else p.performCommand("gm 0 " + t.getName());
    }

    @CommandAlias("gmc") @CommandCompletion("@players") @Syntax("[jogador]")
    public void gmcCommand(Player p, @Flags("other") @Optional Player t) {
        if (t == null) p.performCommand("gm 1");
        else p.performCommand("gm 1 " + t.getName());
    }

    @CommandAlias("gma") @CommandCompletion("@players") @Syntax("[jogador]")
    public void gmaCommand(Player p, @Flags("other") @Optional Player t) {
        if (t == null) p.performCommand("gm 2");
        else p.performCommand("gm 2 " + t.getName());
    }

    @CommandAlias("gmsp") @CommandCompletion("@players") @Syntax("[jogador]")
    public void gmspCommand(Player p, @Flags("other") @Optional Player t) {
        if (t == null) p.performCommand("gm 3");
        else p.performCommand("gm 3 " + t.getName());
    }
}
