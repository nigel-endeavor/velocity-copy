package com.endeavorms.velocity.qto.cdi;

import com.endeavorms.velocity.qto.common.PlatformDatabase;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Bean;


/**
 * Provides resources for dependency injection.
 * @author fcurran
 * @since 1.0.0
 */
@Configuration
@DependsOn("LiquibaseStartupBean")
public class ApplicationResources {

    /** The QTO Database EntityManager.*/
    @PersistenceContext(unitName = "qto")
    private EntityManager qtoEntityManager;

    @PersistenceContext(unitName = "platform")
    private EntityManager platformEntityManager;

    @Bean(name = "qto")
    @QtoDatabase
    public EntityManager qto() {
        return qtoEntityManager;
    }

    @Bean(name = "platform")
    @PlatformDatabase
    public EntityManager platform() {
        return platformEntityManager;
    }

}
