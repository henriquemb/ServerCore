package com.github.henriquemb.servercore.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CatchUnknown;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import com.github.henriquemb.servercore.Main;
import com.github.henriquemb.servercore.Model;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

@CommandAlias("vanish|v")
public class VanishCommand extends BaseCommand {
    private final Model m = Main.getModel();
    private final Main pl = Main.getMain();
    private static final FileConfiguration config = Main.getMain().getMessages();

    @Default @CatchUnknown @CommandPermission("servercore.vanish")
    public void onVanish(Player p) {
        if (m.getVanish().contains(p)) {
            m.sendMessage(p, PlaceholderAPI.setPlaceholders(p, config.getString("vanish.disabled")));
            m.getVanish().remove(p);
            p.removePotionEffect(PotionEffectType.INVISIBILITY);
            Bukkit.getOnlinePlayers().forEach(all ->
                    all.showPlayer(pl, p));
        } else {
            m.sendMessage(p, PlaceholderAPI.setPlaceholders(p, config.getString("vanish.enabled")));
            m.getVanish().add(p);
            p.addPotionEffect(PotionEffectType.INVISIBILITY.createEffect(-1, 4));
            Bukkit.getOnlinePlayers().forEach(all -> {
                if (!all.hasPermission("servercore.vanish.bypass")) {
                    all.hidePlayer(pl, p);
                }
            });
        }
    }
}
