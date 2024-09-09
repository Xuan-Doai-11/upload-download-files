package com.example.uploaddownloadfile.config;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
@NoArgsConstructor
public class ApplicationEventHandled {

    private final Map<Integer, EntityManagerFactory> sysEntityManagerFactories = new ConcurrentHashMap<>();

    @Bean(name = "entityManagerFactories")
    public Map<Integer, EntityManagerFactory> entityManagerFactories() {
        return this.sysEntityManagerFactories;
    }

    void loaded() {

        log.info("Loaded application config");

        int fileId = 1;
        Map<String, String> persistenceProperties = new HashMap<>();

        persistenceProperties.put("javax.persistence.jdbc.url", "jdbc:mysql://localhost:3306/file_db?useSSL=false");
        persistenceProperties.put("javax.persistence.jdbc.user", "root");
        persistenceProperties.put("javax.persistence.jdbc.password", "12345678");
        persistenceProperties.put("javax.persistence.jdbc.driver", "com.mysql.jdbc.Driver");
        persistenceProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");

        this.sysEntityManagerFactories.put(fileId, Persistence.createEntityManagerFactory("master", persistenceProperties));

    }


    @EventListener
    public void run (ApplicationEvent event) {
        loaded();
    }
}
