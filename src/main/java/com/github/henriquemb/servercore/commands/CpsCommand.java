package com.github.henriquemb.servercore.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.github.henriquemb.servercore.Main;
import com.github.henriquemb.servercore.Model;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;

@CommandAlias("cps") @CommandPermission("servercore.cps")
public class CpsCommand extends BaseCommand {
    private final Main pl = Main.getMain();
    private final Model m = Main.getModel();
    private static final FileConfiguration config = Main.getMain().getMessages();

    @Default @CommandCompletion("@players") @Syntax("<jogador>")
    public void onCommand(Player p, @Flags("other") Player t) {
        if (p.equals(t)) {
            m.sendMessage(p, PlaceholderAPI.setPlaceholders(p, config.getString("permission.cannot-execute-youself")));
            return;
        }

        if (m.getCps().containsKey(t)) {
            m.sendMessage(p, PlaceholderAPI.setPlaceholders(p, config.getString("cps.waiting")));
            return;
        }

        m.getCps().put(t, 0);

        m.sendMessage(p, PlaceholderAPI.setPlaceholders(t, config.getString("cps.analyzing")));
        Bukkit.getScheduler().runTaskLater(pl, () -> {
            float cps = m.getCps().get(t) / 5f;
            String fcps = cps >= 20 ? "&4" : cps > 10 ? "&6" : "&a";
            String status = cps >= 20 ? config.getString("cps.gravity.high") :
                    cps > 10 ? config.getString("cps.gravity.middle") : config.getString("cps.gravity.low");

            for (String msg : config.getStringList("cps.success")) {
                msg = msg.replace("%sc_cps%", String.format("%s%.2f", fcps, cps)).replace("%sc_gravity%", status);
                m.sendMessage(p, PlaceholderAPI.setPlaceholders(t, msg));
            }

            m.getCps().remove(t);
        }, 100); // delay em tick, 20 ticks = 1 segundo
    }
}
