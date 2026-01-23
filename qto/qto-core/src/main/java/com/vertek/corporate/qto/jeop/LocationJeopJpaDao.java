package com.vertek.corporate.qto.jeop;


import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.vertek.corporate.qto.cdi.QtoDatabase;
import com.vertek.corporate.qto.common.AbstractJpaDao;
import com.vertek.corporate.qto.common.PreconditionsUtil;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static com.vertek.corporate.qto.jeop.QLocationJeop.locationJeop;

/**
 * @author llevit
 */
@Stateless
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
