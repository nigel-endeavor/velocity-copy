package com.endeavorms.velocity.qto.interval;

import com.querydsl.jpa.impl.JPAQuery;
import com.endeavorms.velocity.qto.cdi.QtoDatabase;
import com.endeavorms.velocity.qto.common.AbstractJpaDao;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.List;

import static com.endeavorms.velocity.qto.interval.QServiceIntervalInstance.serviceIntervalInstance;

/**
 * @author rcasey
 * @since 3/10/2023
 */
@Component
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
