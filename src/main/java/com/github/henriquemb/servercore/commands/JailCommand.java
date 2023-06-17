package com.github.henriquemb.servercore.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.github.henriquemb.servercore.Main;
import com.github.henriquemb.servercore.Model;
import com.github.henriquemb.servercore.structure.JailStructure;
import com.github.henriquemb.servercore.utils.TimeUtils;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@CommandAlias("jail")
public class JailCommand extends BaseCommand {
    private final Main pl = Main.getMain();
    private final Model m = Main.getModel();
    private static final FileConfiguration messages = Main.getMain().getMessages();
    private static final FileConfiguration config = Main.getMain().getConfig();

    @Default @CatchUnknown @CommandPermission("servercore.jail.use")
    @CommandCompletion("@players") @Syntax("<player>")
    public void onCommand(Player p, @Flags("other") Player t) {
        JailStructure jail = m.getJails().get(t);

        if (p == t && jail == null) {
            m.sendMessage(p, PlaceholderAPI.setPlaceholders(p, messages.getString("permission.cannot-execute-youself")));
            return;
        }

        if (jail != null) {
            if ((System.currentTimeMillis() - jail.getTimestamp()) <= 3000)
                return;
        }

        if (t.hasPermission("servercore.jail.bypass") && p != t) {
            m.sendMessage(p, PlaceholderAPI.setPlaceholders(p, messages.getString("permission.bypass")));
            return;
        }

        if (jail != null) {
            JailStructure finalJail = jail;
            Bukkit.getOnlinePlayers().forEach(player -> {
                if (player.hasPermission("servercore.jail.use")) {
                    String msg = messages.getString("jail.unjail");
                    msg = msg.replace("%sc_owner%", p.getName());
                    msg = msg.replace("%sc_time%", TimeUtils.millisecondsToStringTime(System.currentTimeMillis() - finalJail.getTimestamp()));

                    m.sendMessage(player, PlaceholderAPI.setPlaceholders(t, msg));
                    t.teleport(finalJail.getLastPlayerLocation());
                }
            });

            jail.getBlockLocations().forEach(loc -> {
                loc.getBlock().setType(Material.AIR);
            });

            m.getJails().remove(t);
            return;
        }

        Bukkit.getOnlinePlayers().forEach(player -> {
            if (player.hasPermission("servercore.jail.use")) {
                String msg = messages.getString("jail.jailed");
                msg = msg.replace("%sc_owner%", p.getName());

                m.sendMessage(player, PlaceholderAPI.setPlaceholders(t, msg));
            }
        });

        int cageheight = config.getInt("cage-height");
        List<Location> locations = getBlockLocations(t, cageheight);
        Location jLoc = getJailLocation(t, cageheight);

        jail = new JailStructure(p, t.getLocation(), jLoc);
        jail.setBlockLocations(locations);

        for (Location loc : locations) {
            if (!loc.getBlock().isEmpty()) {
                m.sendMessage(p, messages.getString("jail.obstructed-place"));
                return;
            }
        }

        for (Location loc : locations) {
            loc.getBlock().setType(Material.BEDROCK);
        }

        m.getJails().put(t, jail);

        t.teleport(jLoc);
        p.teleport(new Location(jLoc.getWorld(), jLoc.getX() - 3, jLoc.getY() + 1, jLoc.getZ(), jLoc.getYaw(), jLoc.getPitch()));

        for (int i = 0; i < 2; i++) {
            for (String msg : messages.getStringList("jail.jailed-alert")) {
                m.sendMessage(t, PlaceholderAPI.setPlaceholders(p, msg));
            }
        }

        Bukkit.getScheduler().runTaskLater(pl, () -> {
            Location loc = t.getLocation();
            if (((loc.getX() <= jLoc.getX() - 20) || (loc.getX() <= jLoc.getX() + 20)) ||
                    ((loc.getZ() <= jLoc.getZ() - 20) || (loc.getZ() <= jLoc.getZ() + 20)) ||
                    ((loc.getY() <= jLoc.getY() - 5) || (loc.getY() <= jLoc.getY() + 5))) {
                t.teleport(jLoc);
            }
        }, 50); // delay em tick, 20 ticks = 1 segundo
    }

    @Subcommand("list") @CommandPermission("servercore.jail.list")
    public void onList(Player p) {
        Map<Player, JailStructure> jails = m.getJails();

        m.sendMessage(p, "&a&m----------------------------------------------------");
        jails.forEach((player, jail) -> {
            Location jLoc = jail.getLocation();

            String msg = String.format("%s", messages.getString("jail.list"));
            msg = msg.replace("%sc_world%", jLoc.getWorld().getName());
            msg = msg.replace("%sc_player%", player.getName());

            m.sendMessage(p, PlaceholderAPI.setPlaceholders(p, msg));
        });

        if (jails.isEmpty())
            m.sendMessage(p, PlaceholderAPI.setPlaceholders(p, messages.getString("jail.not-exists")));

        m.sendMessage(p, "&a&m----------------------------------------------------");
    }

    @Subcommand("teleport|tp") @CommandPermission("servercore.jail.teleport") @Syntax("<player>") @CommandCompletion("@jails")
    public void onTeleport(Player p, @Flags("other") Player target) {
        JailStructure jail = m.getJails().get(target);

        if (jail == null) {
            m.sendMessage(p, PlaceholderAPI.setPlaceholders(p, messages.getString("jail.not-found")));
            return;
        }

        Location location = jail.getLocation();
        p.teleport(new Location(location.getWorld(), location.getX() - 3, location.getY() + 1, location.getZ(), location.getYaw(), location.getPitch()));
        m.sendMessage(p, PlaceholderAPI.setPlaceholders(p, messages.getString("jail.teleport")
                .replace("%sc_player%", target.getName())));
    }

    private List<Location> getBlockLocations(Player target, int cageheight) {
        List<Location> locations = new ArrayList<>();

        World world = target.getWorld();
        double x = target.getLocation().getX();
        double z = target.getLocation().getZ();

        // Base
        locations.add(new Location(world, x, cageheight, z));
        // Right
        locations.add(new Location(world, x, cageheight + 1, z - 1));
        // Left
        locations.add(new Location(world, x, cageheight + 1, z + 1));
        // Front
        locations.add(new Location(world, x - 1, cageheight + 1, z));
        // Back
        locations.add(new Location(world, x + 1, cageheight + 1, z));
        // Top
        locations.add(new Location(world, x, cageheight + 3, z));

        return locations;
    }

    private Location getJailLocation(Player target, int cageheight) {
        return new Location(target.getWorld(), target.getLocation().getX(), cageheight + 1, target.getLocation().getZ());
    }
}
