package com.github.henriquemb.servercore.database;

import com.github.henriquemb.servercore.Main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

public class Connection {
    private static final FileConfiguration config = Main.getMain().getConfig();
    private EntityManagerFactory emf = null;
    private EntityManager em = null;

    public Connection() {
        createConnection();
    }

    private void createConnection() {
        this.emf = Persistence.createEntityManagerFactory("manager", getConfig());;
        this.em = emf.createEntityManager();
    }

    private Map<String, Object> getConfig() {
        Map<String, Object> emfConfig = new HashMap<>();
        String host = config.getString("database.host");
        String port = config.getString("database.port");
        String name = config.getString("database.name");
        String username = config.getString("database.username");
        String password = config.getString("database.password");
        Boolean ssl = config.getBoolean("database.ssl");

        String url = String.format("jdbc:mysql://%s:%s/%s?useSSL=%s&amp;serverTimezone=America/Sao_Paulo",
                host, port, name, ssl);
        emfConfig.put("javax.persistence.jdbc.url", url);
        emfConfig.put("javax.persistence.jdbc.user", username);
        emfConfig.put("javax.persistence.jdbc.password", password);

        return emfConfig;
    }

    public EntityManagerFactory getFactory() {
        return this.emf;
    }

    public EntityManager getManager() {
        return this.em;
    }

    public void close() {
        this.em.close();
        this.emf.close();
    }
}
