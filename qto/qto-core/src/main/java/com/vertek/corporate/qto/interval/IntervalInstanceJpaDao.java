package com.vertek.corporate.qto.interval;

import com.querydsl.jpa.impl.JPAQuery;
import com.vertek.corporate.qto.cdi.QtoDatabase;
import com.vertek.corporate.qto.common.AbstractJpaDao;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.util.List;

import static com.vertek.corporate.qto.interval.QIntervalInstance.intervalInstance;

/**
 * @author rcasey
 * @since 3/13/2023
 */
@Stateless
public class IntervalInstanceJpaDao extends AbstractJpaDao<IntervalInstance, Long> {
    @Override
    @Inject
    protected void setEntityManager(@QtoDatabase final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<IntervalInstance> findByMilestoneInstanceId(final Long milestoneInstanceId) {
        return new JPAQuery<IntervalInstance>(entityManager)
                .from(intervalInstance)
                .where(intervalInstance.openMilestoneInstanceId.eq(milestoneInstanceId)
                        .or(intervalInstance.closeMilestoneInstanceId.eq(milestoneInstanceId)))
                .fetch();
    }
}
