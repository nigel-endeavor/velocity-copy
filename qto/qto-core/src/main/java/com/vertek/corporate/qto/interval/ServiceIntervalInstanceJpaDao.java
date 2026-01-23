package com.vertek.corporate.qto.interval;

import com.querydsl.jpa.impl.JPAQuery;
import com.vertek.corporate.qto.cdi.QtoDatabase;
import com.vertek.corporate.qto.common.AbstractJpaDao;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import java.util.List;

import static com.vertek.corporate.qto.interval.QServiceIntervalInstance.serviceIntervalInstance;

/**
 * @author rcasey
 * @since 3/10/2023
 */
@Stateless
public class ServiceIntervalInstanceJpaDao extends AbstractJpaDao<ServiceIntervalInstance, Long> {
    @Override
    @Inject
    protected void setEntityManager(@QtoDatabase final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public ServiceIntervalInstance getServiceInterval(final Long serviceId, final Long intervalTypeId) {
        return new JPAQuery<ServiceIntervalInstance>(entityManager)
                .from(serviceIntervalInstance)
                .where(serviceIntervalInstance.serviceId.eq(serviceId)
                        .and(serviceIntervalInstance.intervalTypeId.eq(intervalTypeId)))
                .fetchOne();
    }

    public List<ServiceIntervalInstance> findByServiceId(Long serviceId) {
        return new JPAQuery<ServiceIntervalInstance>(entityManager)
                .from(serviceIntervalInstance)
                .where(serviceIntervalInstance.serviceId.eq(serviceId))
                .fetch();
    }
}
