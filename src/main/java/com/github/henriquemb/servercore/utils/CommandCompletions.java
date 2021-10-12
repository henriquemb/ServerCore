package com.github.henriquemb.servercore.utils;

import com.github.henriquemb.servercore.Main;
import com.github.henriquemb.servercore.database.controller.JailController;
import com.github.henriquemb.servercore.database.model.Jail;
import com.google.common.collect.ImmutableList;
import org.bukkit.GameMode;
import org.bukkit.enchantments.Enchantment;

public class CommandCompletions {
    public CommandCompletions(Main plugin) {
        ImmutableList.Builder<String> bGamermode = ImmutableList.builder();
        for (GameMode gameMode : GameMode.values()){
            bGamermode.add(gameMode.name());
            bGamermode.add(String.valueOf(gameMode.getValue()));
        }

        plugin.getManager().getCommandCompletions().registerCompletion("gamemode", c -> bGamermode.build());

        ImmutableList.Builder<String> bEnchant = ImmutableList.builder();
        for (Enchantment e : Enchantment.values()) {
            bEnchant.add(e.getName());
        }

        plugin.getManager().getCommandCompletions().registerCompletion("enchants", e -> bEnchant.build());

        if (Main.getMain().getDbCoonection() != null) {
            ImmutableList.Builder<String> bJail = ImmutableList.builder();
            for (Jail j : new JailController().findAll()) {
                bJail.add(String.valueOf(j.getId()));
            }

            plugin.getManager().getCommandCompletions().registerCompletion("jails", e -> bJail.build());
        }
    }
}
