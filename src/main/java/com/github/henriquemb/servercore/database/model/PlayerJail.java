package com.github.henriquemb.servercore.database.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class PlayerJail implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String player;

    @ManyToOne
    @JoinColumn(name = "jailId")
    private Jail jailId;

    @Column(nullable = false)
    private Date timestamp;

    private boolean isActive;

    public PlayerJail() {
    }

    public PlayerJail(Long id, String player, Jail jailId, boolean isActive) {
        this.id = id;
        this.player = player;
        this.jailId = jailId;
        this.isActive = isActive;
        this.timestamp = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public Jail getJailId() {
        return jailId;
    }

    public void setJailId(Jail jailId) {
        this.jailId = jailId;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean active) {
        isActive = active;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
