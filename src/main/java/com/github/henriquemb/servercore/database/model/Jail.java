package com.github.henriquemb.servercore.database.model;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Jail implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String word;

    @Column(nullable = false)
    private String coord;

    public Jail() {
    }

    public Jail(Long id, String word, String coord) {
        this.id = id;
        this.word = word;
        this.coord = coord;
    }

    public Jail(Long id, Location loc) {
        this.id = id;
        this.word = loc.getWorld().getName();
        this.coord = String.format("%f;%f;%f;%f;%f", loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Location getLocation() {
        String[] data = this.coord.split(";");

        return new Location(
                Bukkit.getWorld(this.word),
                Double.parseDouble(data[0]),
                Double.parseDouble(data[1]),
                Double.parseDouble(data[2]),
                Float.parseFloat(data[3]),
                Float.parseFloat(data[4])
        );
    }

    public void setLocation(Location loc) {
        this.coord = String.format("%f;%f;%f;%f;%f", loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
    }
}
