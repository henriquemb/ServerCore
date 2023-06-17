package com.github.henriquemb.servercore.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.github.henriquemb.servercore.Main;
import com.github.henriquemb.servercore.Model;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

@CommandAlias("enchant")
public class EnchantCommand extends BaseCommand {
    private final Model m = Main.getModel();
    private static final FileConfiguration config = Main.getMain().getMessages();

    @Default @CommandCompletion("@enchants") @CommandPermission("servercore.enchant") @Syntax("<encantamento> <level>")
    public void onCommand(Player p, String enchant, int level) {
        ItemStack hand = p.getInventory().getItemInMainHand();
        if (hand.getType() == Material.AIR) {
            m.sendMessage(p, PlaceholderAPI.setPlaceholders(p, config.getString("enchant.item-invalid")));
            return;
        }

        Enchantment ench = EnchantmentWrapper.getByKey(NamespacedKey.minecraft(enchant));

        if (ench == null) {
            m.sendMessage(p, PlaceholderAPI.setPlaceholders(p, config.getString("enchant.enchant-invalid")));
            return;
        }

        hand.addUnsafeEnchantment(ench, level);


        String msg = config.getString("enchant.success").replace("%sc_item%", hand.getItemMeta().getDisplayName()).replace("%sc_enchant%", String.format("%s %d", enchant, level));

        m.sendMessage(p, PlaceholderAPI.setPlaceholders(p, msg));
    }
}
