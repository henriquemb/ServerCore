package com.github.henriquemb.servercore.database.controller;

import com.github.henriquemb.servercore.Main;
import com.github.henriquemb.servercore.database.model.Jail;
import com.github.henriquemb.servercore.database.model.PlayerJail;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

public class JailController {
    private static final EntityManager em = Main.getMain().getDbConnection();

    public void create(Jail jail) {
        try {
            // Inicia a transação
            em.getTransaction().begin();

            //n Salva os dados o banco de dados
            em.persist(jail);

            // Confirma as alterações
            em.getTransaction().commit();
        }
        catch (Exception e) {
            System.out.println("Algo inesperado ocorreu.");
            e.printStackTrace();
        }
    }

    public void delete(long id) {
        final PlayerJailController pjController = new PlayerJailController();
        try {
            em.getTransaction().begin();
            Jail jail = em.find(Jail.class, id);

            for (PlayerJail pj : pjController.findByJail(jail.getId())) {
                pjController.delete(pj.getId());
            }

            em.remove(jail);
            em.getTransaction().commit();
        }
        catch (Exception e) {
            System.out.println("Algo inesperado ocorreu.");
            e.printStackTrace();
        }
    }

    public List<Jail> findAll() {
        List<Jail> jails = null;

        try {
            String sql = "SELECT j FROM Jail j";
            TypedQuery<Jail> tq = em.createQuery(sql, Jail.class);
            jails = tq.getResultList();
        }
        catch (NoResultException ignored) {}
        catch (Exception e) {
            System.out.println("Algo inesperado ocorreu.");
            e.printStackTrace();
        }

        return jails;
    }

    public Jail findById(long id) {
        Jail jail = null;

        try {
            String sql = "SELECT j FROM Jail j WHERE j.id = :id";
            TypedQuery<Jail> tq = em.createQuery(sql, Jail.class);
            tq.setParameter("id", id);
            jail = tq.getSingleResult();
        }
        catch (NoResultException ignored) {}
        catch (Exception e) {
            System.out.println("Algo inesperado ocorreu.");
            e.printStackTrace();
        }

        return jail;
    }

    public List<Jail> findEmpty() {
        List<Jail> jails = null;

        try {
            String sql = "SELECT j FROM Jail j WHERE j.id NOT IN (SELECT pj.jailId FROM PlayerJail pj WHERE pj.isActive = :active)";
            TypedQuery<Jail> tq = em.createQuery(sql, Jail.class);
            tq.setParameter("active", true);
            jails = tq.getResultList();
        }
        catch (NoResultException ignored) {}
        catch (Exception e) {
            System.out.println("Algo inesperado ocorreu.");
            e.printStackTrace();
        }

        return jails;
    }
}
