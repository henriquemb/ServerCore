package com.github.henriquemb.servercore;

import com.github.henriquemb.servercore.structure.JailStructure;
import lombok.Data;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Data
public class Model {
    private Set<Player> vanish = new HashSet<>();
    private Set<Player> god = new HashSet<>();
    private Set<Player> fly = new HashSet<>();
    private Set<Player> night = new HashSet<>();
    private Map<Player, Integer> cps = new HashMap<>();
    private Map<Player, ItemStack[]> adminMode = new HashMap<>();
    private Map<Player, JailStructure> jails = new HashMap<>();

    public void sendMessage(Player player, String message) {
        player.sendMessage(Component.text(ChatColor.translateAlternateColorCodes('&', message)));
    }

    public void broadcastMessage(String message) {
        Bukkit.getOnlinePlayers().forEach(player ->
                player.sendMessage(Component.text(ChatColor.translateAlternateColorCodes('&', message)))
        );
    }
}
