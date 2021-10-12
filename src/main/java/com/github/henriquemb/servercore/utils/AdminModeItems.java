package com.github.henriquemb.servercore.utils;

import com.github.henriquemb.servercore.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;

public class AdminModeItems {
    private final Map<String, ItemStack> items = new HashMap<>();
    private static final FileConfiguration config = Main.getMain().getMessages();

    private String formatString(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    public AdminModeItems() {
        ItemStack rotateItem = new ItemStack(Material.SLIME_BALL);
        rotateItem.addUnsafeEnchantment(Enchantment.DURABILITY, 3);
        ItemMeta rotateMeta = rotateItem.getItemMeta();
        rotateMeta.setDisplayName(formatString(config.getString("adminmode.items.rotate")));
        rotateMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        rotateMeta.setLore(null);
        rotateItem.setItemMeta(rotateMeta);

        ItemStack freezeItem = new ItemStack(Material.NETHER_STAR);
        freezeItem.addUnsafeEnchantment(Enchantment.DURABILITY, 3);
        ItemMeta freezeMeta = freezeItem.getItemMeta();
        freezeMeta.setDisplayName(formatString(config.getString("adminmode.items.jail")));
        freezeMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        freezeMeta.setLore(null);
        freezeItem.setItemMeta(freezeMeta);

        ItemStack vanishItem = new ItemStack(Material.POTION);
        vanishItem.addUnsafeEnchantment(Enchantment.DURABILITY, 3);
        ItemMeta vanishMeta = vanishItem.getItemMeta();
        vanishMeta.setDisplayName(formatString(config.getString("adminmode.items.vanish")));
        vanishMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        vanishMeta.setLore(null);
        vanishItem.setItemMeta(vanishMeta);

        ItemStack cpsItem = new ItemStack(Material.BLAZE_ROD);
        cpsItem.addUnsafeEnchantment(Enchantment.DURABILITY, 3);
        ItemMeta cpsMeta = cpsItem.getItemMeta();
        cpsMeta.setDisplayName(formatString(config.getString("adminmode.items.cps")));
        cpsMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        cpsMeta.setLore(null);
        cpsItem.setItemMeta(cpsMeta);

        ItemStack fenceItem = new ItemStack(Material.STAINED_GLASS_PANE);
        fenceItem.setDurability((short) 15);
        ItemMeta fenceMeta = fenceItem.getItemMeta();
        fenceMeta.setDisplayName(formatString(config.getString("adminmode.items.empty")));
        fenceMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        fenceMeta.setLore(null);
        fenceItem.setItemMeta(fenceMeta);

        items.put("rotate", rotateItem);
        items.put("freeze", freezeItem);
        items.put("vanish", vanishItem);
        items.put("cps", cpsItem);
        items.put("fence", fenceItem);
    }

    public ItemStack getItem(String name) {
        return items.get(name);
    }

    public boolean contains(ItemStack item) {
        return items.containsValue(item);
    }
}
