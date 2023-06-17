package com.github.henriquemb.servercore;

import co.aikar.commands.PaperCommandManager;
import com.github.henriquemb.servercore.commands.CommandRegister;
import com.github.henriquemb.servercore.events.EventRegister;
import com.github.henriquemb.servercore.placeholders.api.PlaceholderRegister;
import com.github.henriquemb.servercore.utils.CommandCompletions;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Locale;

public final class Main extends JavaPlugin {
    @Getter @Setter
    private static Model model;

    @Getter @Setter
    private static Main main;

    @Getter @Setter
    private static CommandSender sender;

    @Getter @Setter
    private PaperCommandManager manager;

    @Getter
    private final File localesFile = new File(getDataFolder(), "locales.yml");
    @Getter
    private final FileConfiguration locations = YamlConfiguration.loadConfiguration(getLocalesFile());
    @Getter @Setter
    private File commandsFile = new File(getDataFolder(), "commands.yml");
    @Getter @Setter
    private FileConfiguration commands = YamlConfiguration.loadConfiguration(getCommandsFile());
    @Getter @Setter
    private File messagesFile = new File(getDataFolder(), "messages.yml");
    @Getter @Setter
    private FileConfiguration messages = YamlConfiguration.loadConfiguration(getMessagesFile());

    @Override
    public void onEnable() {
        Locale.setDefault(Locale.US);

        setSender(Bukkit.getConsoleSender());
        setMain(this);
        setModel(new Model());
        setManager(new PaperCommandManager(this));

        getMain().getConfig().options().copyDefaults(true);
        getMain().saveConfig();

        if (!getLocalesFile().exists())
            saveResource("locales.yml", false);
        if (!getCommandsFile().exists())
            saveResource("commands.yml", false);
        if (!getMessagesFile().exists())
            saveResource("messages.yml", false);

        new CommandCompletions(this);
        new CommandRegister();
        new PlaceholderRegister();
        new EventRegister(this);
    }

    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelTasks(this);
    }
}
