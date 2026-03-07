package com.vertek.corporate.qto.service;

import com.vertek.corporate.qto.cdi.QtoDatabase;
import com.vertek.corporate.qto.common.AbstractMasterCustomerJpaDao;
import com.vertek.corporate.qto.common.PlatformDatabase;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

/**
 * @author rcasey
 * @since 2/10/2023
 * @param <T> Service model
 */
public abstract class AbstractServiceJpaDao<T extends Service> extends AbstractMasterCustomerJpaDao<T> {
    @Override
    @Inject
    protected void setEntityManager(@QtoDatabase final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Inject
    protected void setPlatformEntityManager(@PlatformDatabase final EntityManager platformEntityManager) {
        this.platformEntityManager = platformEntityManager;
    }
}
