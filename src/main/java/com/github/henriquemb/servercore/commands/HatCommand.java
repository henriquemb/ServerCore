package com.github.henriquemb.servercore.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CatchUnknown;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import com.github.henriquemb.servercore.Main;
import com.github.henriquemb.servercore.Model;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

@CommandPermission("servercore.hat") @CommandAlias("hat")
public class HatCommand extends BaseCommand {
    private final Model m = Main.getModel();
    private static final FileConfiguration config = Main.getMain().getMessages();

    @Default @CatchUnknown
    public void onCommand(Player p) {
        PlayerInventory i = p.getInventory();
        ItemStack hand = p.getItemInHand();
        ItemStack helmet = i.getHelmet();

        if (hand != null || hand.getType() != Material.AIR && hand.getType().getMaxDurability() == 0) {
            i.setHelmet(hand);
            i.setItemInHand(helmet);
            m.sendMessage(p, PlaceholderAPI.setPlaceholders(p, config.getString("hat.success")));
        } else {
            m.sendMessage(p, PlaceholderAPI.setPlaceholders(p, config.getString("hat.invalid")));
        }
    }
}
