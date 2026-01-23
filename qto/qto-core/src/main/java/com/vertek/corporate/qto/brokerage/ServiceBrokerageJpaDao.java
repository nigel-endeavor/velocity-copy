package com.vertek.corporate.qto.brokerage;

import com.querydsl.jpa.impl.JPAQuery;
import com.vertek.corporate.qto.cdi.QtoDatabase;
import com.vertek.corporate.qto.common.AbstractMasterCustomerJpaDao;
import com.vertek.corporate.qto.common.PlatformDatabase;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import static com.vertek.corporate.qto.brokerage.QServiceBrokerage.serviceBrokerage;

/**
 * @author fcurran
 * @since 6/19/2024
 */
@Stateless
public class ServiceBrokerageJpaDao extends AbstractMasterCustomerJpaDao<ServiceBrokerage> {

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

    /**
     * Retrieves the ServiceBrokerage by serviceId.
     * @param serviceId the serviceId of the desired entity.
     * @return the matching entity if it exists.
     */
    public ServiceBrokerage findByServiceId(final Long serviceId) {
        return new JPAQuery<ServiceBrokerage>(entityManager)
                .from(serviceBrokerage)
                .where(serviceBrokerage.service.id.eq(serviceId))
                .fetchOne();
    }
}
