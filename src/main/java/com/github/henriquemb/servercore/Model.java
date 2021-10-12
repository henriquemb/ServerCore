package com.github.henriquemb.servercore;

import de.themoep.minedown.MineDown;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

@Data
public class Model {
    private Set<Player> vanish = new HashSet<>();
    private Set<Player> god = new HashSet<>();
    private Set<Player> fly = new HashSet<>();
    private Set<Player> night = new HashSet<>();
    private Map<Player, Integer> cps = new HashMap<>();
    private Map<Player, ItemStack[]> adminMode = new HashMap<>();

    public void sendMessage(Player player, String message) {
        player.spigot().sendMessage(MineDown.parse(message));
    }

    public String parseMessage(String message) {
        return Arrays.toString(MineDown.parse(message));
    }

    public void broadcastMessage(String message) {
        Bukkit.getOnlinePlayers().forEach(player ->
                player.spigot().sendMessage(MineDown.parse(message)));
    }
}
