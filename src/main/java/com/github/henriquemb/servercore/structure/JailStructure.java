package com.github.henriquemb.servercore.structure;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class JailStructure {
    @Getter @Setter
    private Player staffer;

    @Getter
    private final long timestamp;

    @Getter @Setter
    private Location location;

    @Getter @Setter
    private Location lastPlayerLocation;

    @Getter @Setter
    private List<Location> blockLocations = new ArrayList<>();

    public JailStructure(Player staffer, Location lastPlayerLocation, Location location) {
        this.staffer = staffer;
        this.lastPlayerLocation = lastPlayerLocation;
        this.location = location;
        this.timestamp = System.currentTimeMillis();
    }
}
