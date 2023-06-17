package com.github.henriquemb.servercore.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CatchUnknown;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import com.github.henriquemb.servercore.Main;
import com.github.henriquemb.servercore.Model;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@CommandAlias("list|listar")
public class ListCommand extends BaseCommand {
    private final Model m = Main.getModel();
    private final Main pl = Main.getMain();

    @Default @CatchUnknown
    public void onList(Player p) {
        List<String> ignore = pl.getConfig().getStringList("list.ignore").stream()
                .map(String::toLowerCase)
                .collect(Collectors.toList());
        long on = Bukkit.getOnlinePlayers().stream()
                .filter(player -> !m.getVanish().contains(player))
                .filter(player -> !ignore.contains(player.getName().toLowerCase())).count();

        m.sendMessage(p, "&a&m----------------------------------------------------");
        m.sendMessage(p, String.format("&bAtualmente temos &f%s&b jogadores online.", on));
        Set<String> listed = new HashSet<>();
        Objects.requireNonNull(pl.getConfig().getConfigurationSection("list.roles")).getKeys(false)
                .forEach(key -> {
                    String permission = pl.getConfig().getString("list.roles." + key + ".permission");
                    List<String> names = Bukkit.getOnlinePlayers().stream()
                            .filter(player -> !m.getVanish().contains(player))
                            .filter(player -> !ignore.contains(player.getName().toLowerCase()))
                            .filter(player -> !listed.contains(player.getName()))
                            .filter(player -> player.hasPermission(Objects.requireNonNull(permission)))
                            .map(Player::getName)
                            .collect(Collectors.toList());

                    listed.addAll(names);
                    String group = pl.getConfig().getString("list.roles." + key + ".name");

                    if (pl.getConfig().getBoolean("list.show-empty")) {
                        m.sendMessage(p, names.isEmpty()
                                ? String.format("&cNão tem nenhum %s online.", group)
                                : String.format("&e%s(s): &9%s", group,
                                names.stream().map(Object::toString)
                                        .collect(Collectors.joining(", "))
                        ));
                    } else {
                        if (!names.isEmpty())
                            m.sendMessage(p, String.format("&e%s(s): &9%s", group,
                                    names.stream().map(Object::toString)
                                            .collect(Collectors.joining(", "))
                            ));
                    }

                });
        m.sendMessage(p, "&a&m----------------------------------------------------");
    }
}
