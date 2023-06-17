package com.github.henriquemb.servercore.utils;

import com.github.henriquemb.servercore.Main;
import com.github.henriquemb.servercore.Model;
import com.google.common.collect.ImmutableList;
import org.bukkit.GameMode;
import org.bukkit.enchantments.Enchantment;

public class CommandCompletions {
    private final Model m = Main.getModel();

    public CommandCompletions(Main plugin) {
        ImmutableList.Builder<String> bGamermode = ImmutableList.builder();
        for (GameMode gameMode : GameMode.values()){
            bGamermode.add(gameMode.name().toLowerCase());
        }

        plugin.getManager().getCommandCompletions().registerCompletion("gamemode", c -> bGamermode.build());

        ImmutableList.Builder<String> bEnchant = ImmutableList.builder();
        for (Enchantment e : Enchantment.values()) {
            bEnchant.add(e.getKey().value());
        }

        plugin.getManager().getCommandCompletions().registerCompletion("enchants", e -> bEnchant.build());

        ImmutableList.Builder<String> bJail = ImmutableList.builder();
        m.getJails().forEach((p, jail) -> {
            bJail.add(p.getName());
        });

        plugin.getManager().getCommandCompletions().registerCompletion("jails", e -> bJail.build());
    }
}
