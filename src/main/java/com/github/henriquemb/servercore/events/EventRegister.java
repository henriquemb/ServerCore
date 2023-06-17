package com.github.henriquemb.servercore.events;

import com.github.henriquemb.servercore.Main;
import org.bukkit.plugin.PluginManager;

public class EventRegister {
    public EventRegister(Main plugin) {
        PluginManager pm = plugin.getServer().getPluginManager();

        pm.registerEvents(new PlayerQuitListener(), plugin);
        pm.registerEvents(new PlayerJoinListener(), plugin);
        pm.registerEvents(new PlayerFlyListener(), plugin);
        pm.registerEvents(new GodListener(), plugin);
        pm.registerEvents(new CpsListener(), plugin);
        pm.registerEvents(new JailListener(), plugin);

        if (Main.getMain().getServer().getPluginManager().getPlugin("Vulcan") != null)
            pm.registerEvents(new AdminModeListener(), plugin);
    }
}
