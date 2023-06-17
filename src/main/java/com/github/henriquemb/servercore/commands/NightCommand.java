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
import org.bukkit.potion.PotionEffectType;

@CommandAlias("night|luz") @CommandPermission("servercore.night")
public class NightCommand extends BaseCommand {
    private final Model m = Main.getModel();
    private static final FileConfiguration config = Main.getMain().getMessages();

    @Default @CatchUnknown
    public void onCommand(Player p) {
        if (m.getNight().contains(p)) {
            m.getNight().remove(p);
            p.removePotionEffect(PotionEffectType.NIGHT_VISION);
            m.sendMessage(p, PlaceholderAPI.setPlaceholders(p, config.getString("night.disabled")));
            return;
        }

        m.getNight().add(p);
        p.addPotionEffect(PotionEffectType.NIGHT_VISION.createEffect(-1, 10));
        m.sendMessage(p, PlaceholderAPI.setPlaceholders(p, config.getString("night.enabled")));
    }
}
