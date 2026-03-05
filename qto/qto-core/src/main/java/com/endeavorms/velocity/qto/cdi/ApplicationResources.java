package com.endeavorms.velocity.qto.cdi;

import com.endeavorms.velocity.qto.common.PlatformDatabase;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;


/**
 * Provides resources for dependency injection.
 * @author fcurran
 * @since 1.0.0
 */
@Configuration
public class ApplicationResources {

    /** The single EntityManager used for both QTO and Platform databases.*/
    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    @Primary
    @QtoDatabase
    public EntityManager getQtoEntityManager() {
        return entityManager;
    }

    @Bean
    @PlatformDatabase
    public EntityManager getPlatformEntityManager() {
        return entityManager;
    }

}
