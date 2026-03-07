package com.vertek.corporate.qto.ftdi;

import com.vertek.corporate.qto.cdi.QtoDatabase;
import com.vertek.corporate.qto.common.AbstractJpaDao;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@Stateless
public class FtdiCustomFieldValueJpaDao extends AbstractJpaDao<FtdiCustomFieldValue, Long> {

    @Override
    @Inject
    protected void setEntityManager(@QtoDatabase final EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
