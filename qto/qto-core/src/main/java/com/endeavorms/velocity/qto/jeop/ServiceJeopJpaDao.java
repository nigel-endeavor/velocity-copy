package com.endeavorms.velocity.qto.jeop;

import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.endeavorms.velocity.qto.cdi.QtoDatabase;
import com.endeavorms.velocity.qto.common.AbstractJpaDao;
import com.endeavorms.velocity.qto.common.PreconditionsUtil;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.util.Date;
import java.util.List;

import static com.endeavorms.velocity.qto.jeop.QServiceJeop.serviceJeop;

/**
 * @author llevit
 */
@Component
public class ServiceJeopJpaDao extends AbstractJpaDao<ServiceJeop, Long> {

    @Override
    @Inject
    protected void setEntityManager(@QtoDatabase final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Gets list of all open jeops for service.
     *
     * @param serviceId of the serviceId.
     * @return List of open jeops.
     */
    public List<ServiceJeop> getOpen(final Long serviceId) {
        PreconditionsUtil.checkArgument(serviceId, "A locationId is required");
        JPQLQuery<ServiceJeop> query = new JPAQuery<ServiceJeop>(entityManager)
                .from(serviceJeop)
                .where(serviceJeop.serviceId.eq(serviceId)
                        .and(serviceJeop.endDate.isNull()));
        return query.fetch();
    }

    /**
     * Gets list of all open jeops for service filtered by description.
     *
     * @param serviceId of the serviceId.
     * @return List of open jeops.
     */
    public List<ServiceJeop> findOpenByDescription(final Long serviceId, final String description) {
        PreconditionsUtil.checkArgument(serviceId, "A locationId is required");
        JPQLQuery<ServiceJeop> query = new JPAQuery<ServiceJeop>(entityManager)
                .from(serviceJeop)
                .where(serviceJeop.serviceId.eq(serviceId)
                        .and(serviceJeop.description.eq(description))
                        .and(serviceJeop.endDate.isNull()));
        return query.fetch();
    }

    /**
     * Gets list of closed jeops where the jeop start or end date (or both) falls between the intervalOpen and intervalClose dates.
     * @param serviceId service id
     * @param intervalOpen interval open date
     * @param intervalClose interval close date
     * @return list of ServiceJeops
     */
    public List<ServiceJeop> getIntervalJeops(final Long serviceId, final Date intervalOpen, final Date intervalClose) {
        return new JPAQuery<ServiceJeop>(entityManager)
                .from(serviceJeop)
                .where(serviceJeop.serviceId.eq(serviceId)
                                .and(serviceJeop.endDate.isNotNull())
                                .and((serviceJeop.startDate.between(intervalOpen, intervalClose))
                                        .or(serviceJeop.endDate.between(intervalOpen, intervalClose))))
                .fetch();
    }

    public List<ServiceJeop> findByServiceId(Long serviceId) {
        return new JPAQuery<ServiceJeop>(entityManager)
                .from(serviceJeop)
                .where(serviceJeop.serviceId.eq(serviceId))
                .fetch();
    }
}
