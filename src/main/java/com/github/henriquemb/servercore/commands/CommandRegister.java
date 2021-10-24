package com.github.henriquemb.servercore.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.PaperCommandManager;
import com.github.henriquemb.servercore.Main;
import lombok.SneakyThrows;

public class CommandRegister {
    private final Main pl = Main.getMain();
    private final PaperCommandManager manager = pl.getManager();

    @SneakyThrows
    private void onActive(BaseCommand command, String path) {
        if (path == null || pl.getCommands().getBoolean(path)) manager.registerCommand(command);
    }

    public CommandRegister() {
        onActive(new BroadcastCommand(), "broadcast");
        onActive(new ClearChatCommand(), "clearchat");
        onActive(new ClearCommand(), "clear");
        onActive(new CpsCommand(), "cps");
        onActive(new CraftCommand(), "craftingtable");
        onActive(new CrashCommand(), "crash");
        onActive(new EnchantCommand(), "enchant");
        onActive(new EnderChestCommand(), "enderchest");
        onActive(new FlyCommand(), "fly");
        onActive(new GameModeCommand(), "gamemode");
        onActive(new GodCommand(), "god");
        onActive(new HatCommand(), "hat");
        onActive(new InfoPlayerCommand(), "infoplayer");
        onActive(new InvSeeCommand(), "invsee");
        onActive(new ListCommand(), "list");
        onActive(new NightCommand(), "night");
        onActive(new SayCommand(), "say");
        onActive(new SpawnCommand(), "spawn");
        onActive(new SpeedCommand(), "speed");
        onActive(new SudoCommand(), "sudo");
        onActive(new TeleportCommand(), "teleport.tp");
        onActive(new TeleportHereCommand(), "teleport.tphere");
        onActive(new TeleportLocationCommand(), "teleport.tploc");
        onActive(new ThorCommand(), "thor");
        onActive(new VanishCommand(), "vanish");

        if (pl.getDbCoonection() != null)
            onActive(new JailCommand(), "jail");

        if (pl.getServer().getPluginManager().getPlugin("Vulcan") != null)
            onActive(new AdminModeCommand(), "adminmode");
    }
}
