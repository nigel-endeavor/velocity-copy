package com.endeavorms.velocity.qto.jeop;


import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.endeavorms.velocity.qto.cdi.QtoDatabase;
import com.endeavorms.velocity.qto.common.AbstractJpaDao;
import com.endeavorms.velocity.qto.common.PreconditionsUtil;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.util.List;

import static com.endeavorms.velocity.qto.jeop.QLocationJeop.locationJeop;

/**
 * @author llevit
 */
@Component
public class LocationJeopJpaDao extends AbstractJpaDao<LocationJeop, Long> {

    @Override
    @Inject
    protected void setEntityManager(@QtoDatabase final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Gets list of all open jeops for an location.
     *
     * @param locationId of the location.
     * @return List of open jeops.
     */
    public List<LocationJeop> getOpen(final Long locationId) {
        PreconditionsUtil.checkArgument(locationId, "A locationId is required");
        JPQLQuery<LocationJeop> query = new JPAQuery<LocationJeop>(entityManager)
                .from(locationJeop)
                .where(locationJeop.locationId.eq(locationId)
                        .and(locationJeop.endDate.isNull()));
        return query.fetch();
    }

        /**
     * Gets list of all open jeops for an location.
     *
     * @param locationId of the location.
     * @return List of open jeops.
     */
    public List<LocationJeop> getByLocationId(final Long locationId) {
        PreconditionsUtil.checkArgument(locationId, "A locationId is required");
        JPQLQuery<LocationJeop> query = new JPAQuery<LocationJeop>(entityManager)
                .from(locationJeop)
                .where(locationJeop.locationId.eq(locationId));
        return query.fetch();
    }
}
