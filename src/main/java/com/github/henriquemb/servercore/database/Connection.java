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
    private final Main pl = Main.getMain();
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

        String url;
        String dialect;

        if (config.getBoolean("database.mysql")) {
            String host = config.getString("database.host");
            String port = config.getString("database.port");
            String name = config.getString("database.name");
            String username = config.getString("database.username");
            String password = config.getString("database.password");
            Boolean ssl = config.getBoolean("database.ssl");
            url = String.format("jdbc:mysql://%s:%s/%s?useSSL=%s&amp;serverTimezone=America/Sao_Paulo",
                    host, port, name, ssl);
            dialect = "org.hibernate.dialect.MySQL8Dialect";
            emfConfig.put("javax.persistence.jdbc.user", username);
            emfConfig.put("javax.persistence.jdbc.password", password);
        }
        else {
            dialect = "org.hibernate.dialect.SQLiteDialect";
            url = String.format("jdbc:sqlite:%s/database.db", pl.getDataFolder().getAbsolutePath());
        }

        emfConfig.put("hibernate.dialect", dialect);
        emfConfig.put("javax.persistence.jdbc.url", url);

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
