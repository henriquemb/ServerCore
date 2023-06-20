package com.github.henriquemb.servercore.placeholders.api;

import com.github.henriquemb.servercore.Main;
import com.github.henriquemb.servercore.Model;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ServerCorePlaceholder extends PlaceholderExpansion {
    private static final Main plugin = Main.getMain();
    private static final Model m = Main.getModel();

    public ServerCorePlaceholder() {
    }

    @Override
    public @NotNull String getAuthor() {
        return plugin.getPluginMeta().getAuthors().get(0);
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getPluginMeta().getVersion();
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "servercore";
    }

    @Override
    public String onPlaceholderRequest(Player p, String params) {
        if (params.equalsIgnoreCase("online_players")) {
            if (p.getPlayer() != null && p.getPlayer().hasPermission("servercore.vanish"))
                return String.valueOf(Bukkit.getOnlinePlayers().size());
            else
                return String.valueOf(Bukkit.getOnlinePlayers().size() - m.getVanish().size());
        }

        return null;
    }
}
