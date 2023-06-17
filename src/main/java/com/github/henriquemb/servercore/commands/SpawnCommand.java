package com.github.henriquemb.servercore.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.github.henriquemb.servercore.Main;
import com.github.henriquemb.servercore.Model;
import lombok.SneakyThrows;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Objects;

@CommandAlias("spawn")
public class SpawnCommand extends BaseCommand {
    private final Model m = Main.getModel();
    private final Main pl = Main.getMain();
    private static final FileConfiguration config = Main.getMain().getMessages();
    private static final FileConfiguration locales = Main.getMain().getLocations();

    @Default @CatchUnknown @CommandCompletion("@players") @Syntax("[jogador]")
    public void onCommand(Player s, @Flags("other") @Optional Player t) {
        Player p = (t != null && s.hasPermission("servercore.spawn.other")) ? t : s;

        try {
            final World w = Bukkit.getServer().getWorld(Objects.requireNonNull(
                    locales.getString("spawn.world")));
            final double x = locales.getDouble("spawn.x");
            final double y = locales.getDouble("spawn.y");
            final double z = locales.getDouble("spawn.z");
            final double yaw = locales.getDouble("spawn.yaw");
            final double pitch = locales.getDouble("spawn.pitch");
            final Location spawn = new Location(w, x, y, z, (float) yaw, (float) pitch);

            p.teleport(spawn);
        } catch (Exception e) {
            m.sendMessage(p, PlaceholderAPI.setPlaceholders(p, config.getString("spawn.undefined")));
            e.printStackTrace();
        }
    }

    @Subcommand("set") @CommandPermission("servercore.spawn.set") @SneakyThrows
    public void onSetSpawn(Player p) {
        Location l = p.getLocation();

        locales.set("spawn.world",
                Objects.requireNonNull(l.getWorld()).getName());
        locales.set("spawn.x", l.getX());
        locales.set("spawn.y", l.getY());
        locales.set("spawn.z", l.getZ());
        locales.set("spawn.yaw", l.getYaw());
        locales.set("spawn.pitch", l.getPitch());
        locales.save(pl.getLocalesFile());
        m.sendMessage(p, PlaceholderAPI.setPlaceholders(p, config.getString("spawn.defined")));
    }
}
