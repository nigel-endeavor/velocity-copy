package com.vertek.corporate.qto.cdi;

import com.vertek.corporate.qto.common.PlatformDatabase;

import javax.ejb.DependsOn;
import javax.ejb.Startup;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


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
