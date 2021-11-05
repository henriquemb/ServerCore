package com.github.henriquemb.servercore.database.controller;

import com.github.henriquemb.servercore.Main;
import com.github.henriquemb.servercore.database.model.Jail;
import com.github.henriquemb.servercore.database.model.PlayerJail;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

public class PlayerJailController {
    private static final EntityManager em = Main.getMain().getDbConnection();

    public void create(String player, Jail jail, boolean active) {
        try {
            Jail j = em.find(Jail.class, jail.getId());
            PlayerJail playerJail = new PlayerJail(null, player, jail, active);
            em.getTransaction().begin();
            em.persist(playerJail);
            em.getTransaction().commit();
        }
        catch (Exception e) {
            System.out.println("Algo inesperado ocorreu.");
            e.printStackTrace();
        }
    }

    public void delete(long id) {
        try {
            PlayerJail playerJail = em.find(PlayerJail.class, id);
            em.getTransaction().begin();
            em.remove(playerJail);
            em.getTransaction().commit();
        }
        catch (Exception e) {
            System.out.println("Algo inesperado ocorreu.");
            e.printStackTrace();
        }
    }

    public PlayerJail findById(long id) {
        PlayerJail playerJail = null;

        try {
            String sql = "SELECT pj FROM PlayerJail pj WHERE pj.id = :id";
            TypedQuery<PlayerJail> tq = em.createQuery(sql, PlayerJail.class);
            tq.setParameter("id", id);

            playerJail = tq.getSingleResult();
        }
        catch (NoResultException ignored) {}
        catch (Exception e) {
            System.out.println("Algo inesperado ocorreu.");
            e.printStackTrace();
        }

        return playerJail;
    }

    public List<PlayerJail> findByPlayer(String player) {
        List<PlayerJail> playerJails = null;

        try {
            String sql = "SELECT pj FROM PlayerJail pj WHERE pj.player = :player";
            TypedQuery<PlayerJail> tq = em.createQuery(sql, PlayerJail.class);
            tq.setParameter("player", player);

            playerJails = tq.getResultList();
        }
        catch (NoResultException ignored) {}
        catch (Exception e) {
            System.out.println("Algo inesperado ocorreu.");
            e.printStackTrace();
        }

        return playerJails;
    }

    public PlayerJail findByPlayerAndActive(String player) {
        PlayerJail playerJail = null;

        try {
            String sql = "SELECT pj FROM PlayerJail pj WHERE pj.player = :player and pj.isActive = :active";
            TypedQuery<PlayerJail> tq = em.createQuery(sql, PlayerJail.class);
            tq.setParameter("player", player);
            tq.setParameter("active", true);

            playerJail = tq.getSingleResult();
        }
        catch (NoResultException ignored) {}
        catch (Exception e) {
            System.out.println("Algo inesperado ocorreu.");
            e.printStackTrace();
        }

        return playerJail;
    }

    public List<PlayerJail> findAllActive() {
        List<PlayerJail> playerJails = null;

        try {
            String sql = "SELECT pj FROM PlayerJail pj WHERE pj.isActive = :active";
            TypedQuery<PlayerJail> tq = em.createQuery(sql, PlayerJail.class);
            tq.setParameter("active", true);

            playerJails = tq.getResultList();
        }
        catch (NoResultException ignored) {}
        catch (Exception e) {
            System.out.println("Algo inesperado ocorreu.");
            e.printStackTrace();
        }

        return playerJails;
    }

    public List<PlayerJail> findByJail(long jailId) {
        List<PlayerJail> playerJails = null;

        try {
            Jail jail = em.find(Jail.class, jailId);
            String sql = "SELECT pj FROM PlayerJail pj WHERE pj.jailId = :id";

            TypedQuery<PlayerJail> tq = em.createQuery(sql, PlayerJail.class);
            tq.setParameter("id", jail);

            playerJails = tq.getResultList();
        }
        catch (NoResultException ignored) {}
        catch (Exception e) {
            System.out.println("Algo inesperado ocorreu.");
            e.printStackTrace();
        }

        return playerJails;
    }

    public PlayerJail findByJailAndActive(long id) {
        PlayerJail playerJail = null;

        try {
            String sql = "SELECT pj FROM PlayerJail pj WHERE pj.jailId.id = :jail and pj.isActive = :active";
            TypedQuery<PlayerJail> tq = em.createQuery(sql, PlayerJail.class);
            tq.setParameter("jail", id);
            tq.setParameter("active", true);

            playerJail = tq.getSingleResult();
        }
        catch (NoResultException ignored) {}
        catch (Exception e) {
            System.out.println("Algo inesperado ocorreu.");
            e.printStackTrace();
        }

        return playerJail;
    }

    public List<PlayerJail> findAll() {
        List<PlayerJail> playerJails = null;

        try {
            String sql = "SELECT pj FROM PlayerJail pj";

            TypedQuery<PlayerJail> tq = em.createQuery(sql, PlayerJail.class);

            playerJails = tq.getResultList();
        }
        catch (NoResultException ignored) {}
        catch (Exception e) {
            System.out.println("Algo inesperado ocorreu.");
            e.printStackTrace();
        }

        return playerJails;
    }

    public void update(PlayerJail playerJail) {
        try {
            em.getTransaction().begin();
            PlayerJail pj = em.find(PlayerJail.class, playerJail.getId());
            pj.setPlayer(playerJail.getPlayer());
            pj.setJailId(playerJail.getJailId());
            pj.setIsActive(playerJail.getIsActive());

            em.persist(pj);
            em.getTransaction().commit();
        }
        catch (Exception e) {
            System.out.println("Algo inesperado ocorreu.");
            e.printStackTrace();
        }
    }

    public void toggleActive(long id) {
        PlayerJail pj = findById(id);
        pj.setIsActive(!pj.getIsActive());
        update(pj);
    }
}
