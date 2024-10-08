package com.github.henriquemb.servercore.utils;

import com.github.henriquemb.servercore.Main;
import net.kyori.adventure.text.Component;
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
    private static final FileConfiguration config = Main.getMain().getConfig();

    private String formatString(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    public AdminModeItems() {
        ItemStack rotate = createItem("rotate");

        ItemStack jail = createItem("jail");

        ItemStack vanish = createItem("vanish");

        ItemStack cps = createItem("cps");

        ItemStack empty = createItem("empty");

        items.put("rotate", rotate);
        items.put("jail", jail);
        items.put("vanish", vanish);
        items.put("cps", cps);
        items.put("empty", empty);
    }

    private ItemStack createItem(String name) {
        ItemStack item = new ItemStack(Material.valueOf(config.getString(String.format("adminmode.items.%s.id", name))));

        item.addUnsafeEnchantment(Enchantment.DURABILITY, 3);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.text(formatString(config.getString(String.format("adminmode.items.%s.name", name)))));
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.lore(null);
        item.setItemMeta(meta);

        return item;
    }

    public ItemStack getItem(String name) {
        return items.get(name);
    }

    public boolean contains(ItemStack item) {
        return items.containsValue(item);
    }
}
