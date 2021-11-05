package com.github.henriquemb.servercore.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.github.henriquemb.servercore.Main;
import com.github.henriquemb.servercore.Model;
import com.github.henriquemb.servercore.database.controller.JailController;
import com.github.henriquemb.servercore.database.controller.PlayerJailController;
import com.github.henriquemb.servercore.database.model.Jail;
import com.github.henriquemb.servercore.database.model.PlayerJail;
import com.github.henriquemb.servercore.utils.CommandCompletions;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Date;
import java.util.List;

@CommandAlias("jail")
public class JailCommand extends BaseCommand {
    private final JailController jController = new JailController();
    private final PlayerJailController pjController = new PlayerJailController();

    private final Main pl = Main.getMain();
    private final Model m = Main.getModel();

    private static final FileConfiguration config = Main.getMain().getMessages();

    private String diff(PlayerJail pj) {
        long diff = new Date(System.currentTimeMillis()).getTime() - pj.getTimestamp().getTime();
        long s = diff / 1000 % 60;
        long min = diff / (60 * 1000) % 60;
        long h = diff / (60 * 60 * 1000) % 24;

        String time = "";

        if (h == 1) time += String.format("%d %s ", h, config.getString("jail.time.hour"));
        if (h > 1) time += String.format("%d %s ", h, config.getString("jail.time.hours"));

        if (min == 1) time += String.format("%d %s ", h, config.getString("jail.time.minute"));
        if (min > 1) time += String.format("%d %s ", h, config.getString("jail.time.minutes"));

        if (s > 0) time += String.format("%d %s", s, config.getString("jail.time.seconds"));

        return time;
    }

    @Default @CatchUnknown @CommandPermission("servercore.jail.use")
    @CommandCompletion("@players") @Syntax("<player>")
    public void onCommand(Player p, @Flags("other") Player t) {
        PlayerJail pj = pjController.findByPlayerAndActive(t.getName());

        if (p == t && pj == null) {
            m.sendMessage(p, PlaceholderAPI.setPlaceholders(p, config.getString("permission.cannot-execute-youself")));
            return;
        }

        if (t.hasPermission("servercore.jail.bypass") && p != t) {
            m.sendMessage(p, PlaceholderAPI.setPlaceholders(p, config.getString("permission.bypass")));
            return;
        }

        if (pj != null) {
            pjController.toggleActive(pj.getId());

            Bukkit.getOnlinePlayers().forEach(player -> {
                if (player.hasPermission("servercore.jail.use")) {
                    String msg = config.getString("jail.unjail");
                    msg = msg.replace("%sc_owner%", p.getName());
                    msg = msg.replace("%sc_time%", diff(pj));

                    m.sendMessage(player, PlaceholderAPI.setPlaceholders(t, msg));
                    p.performCommand("spawn");
                    t.performCommand("spawn");
                }
            });
            return;
        }

        List<Jail> jEmptyList = jController.findEmpty();

        if (jEmptyList.isEmpty()) {
            m.sendMessage(p, PlaceholderAPI.setPlaceholders(p, config.getString("jail.unavailable")));
            return;
        }

        Bukkit.getOnlinePlayers().forEach(player -> {
            if (player.hasPermission("servercore.jail.use")) {
                String msg = config.getString("jail.jailed");
                msg = msg.replace("%sc_owner%", p.getName());

                m.sendMessage(player, PlaceholderAPI.setPlaceholders(t, msg));
            }
        });

        Jail jail = jEmptyList.get(0);
        pjController.create(t.getName(), jail, true);

        t.getInventory().clear();
        t.setHealth(0);

        Location jLoc = jail.getLocation();
        t.teleport(jLoc);
        p.teleport(new Location(jLoc.getWorld(), jLoc.getX(), jLoc.getY() + 3, jLoc.getZ(), jLoc.getYaw(), jLoc.getPitch()));

        for (int i = 0; i < 2; i++) {
            for (String msg : config.getStringList("jail.jailed-alert")) {
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

    @Subcommand("create") @CommandPermission("servercore.jail.create")
    public void onCreate(Player p) {
        Location loc = p.getLocation();

        jController.create(new Jail(null, loc));

        new CommandCompletions(pl);
        m.sendMessage(p, PlaceholderAPI.setPlaceholders(p, config.getString("jail.create")));
    }

    @Subcommand("list") @CommandPermission("servercore.jail.list")
    public void onList(Player p) {
        List<Jail> jails = jController.findAll();

        m.sendMessage(p, "&a&m----------------------------------------------------");
        for (Jail jail : jails) {
            Location jLoc = jail.getLocation();
            PlayerJail pj = pjController.findByJailAndActive(jail.getId());

            String msg = String.format("[%s", config.getString("jail.list"));
            msg = msg.replace("%sc_id%", String.valueOf(jail.getId()));
            msg = msg.replace("%sc_world%", jLoc.getWorld().getName());
            msg = msg.replace("%sc_player%", pj == null ? config.getString("jail.empty") : pj.getPlayer());
            msg += String.format("](/jail tp %d hover=&aX: &e%.2f, &aY: &e%.2f, &aZ: &e%.2f)",
                    jail.getId(), jLoc.getX(), jLoc.getY(), jLoc.getZ());

            m.sendMessage(p, PlaceholderAPI.setPlaceholders(p, msg));
        }

        if (jails.isEmpty())
            m.sendMessage(p, PlaceholderAPI.setPlaceholders(p, config.getString("jail.not-exists")));

        m.sendMessage(p, "&a&m----------------------------------------------------");
    }

    @Subcommand("remove") @CommandPermission("servercore.jail.remove") @Syntax("<id>") @CommandCompletion("@jails")
    public void onRemove(Player p, int id) {
        Jail jail = jController.findById(id);

        if (jail == null) {
            m.sendMessage(p, PlaceholderAPI.setPlaceholders(p, config.getString("jail.not-found")));
            return;
        }

        jController.delete(id);

        new CommandCompletions(pl);
        m.sendMessage(p, PlaceholderAPI.setPlaceholders(p, config.getString("jail.remove")
                .replace("%sc_id%", String.valueOf(id))));
    }

    @Subcommand("teleport|tp") @CommandPermission("servercore.jail.teleport") @Syntax("<id>") @CommandCompletion("@jails")
    public void onTeleport(Player p, int id) {
        Jail jail = jController.findById(id);

        if (jail == null) {
            m.sendMessage(p, PlaceholderAPI.setPlaceholders(p, config.getString("jail.not-found")));
            return;
        }

        Location location = jail.getLocation();
        p.teleport(new Location(location.getWorld(), location.getX(), location.getY() + 3, location.getZ(), location.getYaw(), location.getPitch()));
        m.sendMessage(p, PlaceholderAPI.setPlaceholders(p, config.getString("jail.teleport")
                .replace("%sc_id%", String.valueOf(id))));
    }
}
