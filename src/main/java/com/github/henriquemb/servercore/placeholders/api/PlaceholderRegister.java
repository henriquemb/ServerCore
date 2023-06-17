package com.github.henriquemb.servercore.placeholders.api;

import org.bukkit.Bukkit;

public class PlaceholderRegister {
    public PlaceholderRegister() {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) {
            return;
        }

        new ServerCorePlaceholder().register();
    }
}
