package com.vertek.corporate.qto.cdi;

import com.vertek.corporate.qto.common.PlatformDatabase;

import jakarta.ejb.DependsOn;
import jakarta.ejb.Startup;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;


/**
 * Provides resources for dependency injection.
 * @author fcurran
 * @since 1.0.0
 */
@Singleton
@Startup
@DependsOn("LiquibaseStartupBean")
public class ApplicationResources {

    /** The QTO Database EntityManager.*/
    @PersistenceContext(unitName = "qto")
    private EntityManager qtoEntityManager;

    @PersistenceContext(unitName = "platform")
    private EntityManager platformEntityManager;

    @Produces
    @QtoDatabase
    public EntityManager getQtoEntityManager() {
        return qtoEntityManager;
    }

    @Produces
    @PlatformDatabase
    public EntityManager getPlatformEntityManager() {
        return platformEntityManager;
    }

}
