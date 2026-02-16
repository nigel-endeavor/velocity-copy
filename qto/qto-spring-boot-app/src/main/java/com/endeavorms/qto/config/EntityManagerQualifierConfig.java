package com.endeavorms.qto.config;

import com.endeavorms.velocity.qto.cdi.QtoDatabase;
import com.endeavorms.velocity.qto.common.PlatformDatabase;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EntityManagerQualifierConfig {

    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    @QtoDatabase
    public EntityManager qtoEntityManager() {
        return entityManager;
    }

    @Bean
    @PlatformDatabase
    public EntityManager platformEntityManager() {
        return entityManager;
    }
}
