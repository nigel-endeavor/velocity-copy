package com.vertek.corporate.qto.interval;

import com.querydsl.jpa.impl.JPAQuery;
import com.vertek.corporate.qto.cdi.QtoDatabase;
import com.vertek.corporate.qto.common.AbstractJpaDao;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import java.util.List;

import static com.vertek.corporate.qto.interval.QIntervalType.intervalType;

/**
 * @author rcasey
 * @since 3/9/2023
 */
@Stateless
public class IntervalTypeJpaDao extends AbstractJpaDao<IntervalType, Long> {

    @Override
    @Inject
    protected void setEntityManager(@QtoDatabase final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<IntervalType> findByMilestoneId(final Long milestoneId) {
        return new JPAQuery<IntervalType>(entityManager)
                .from(intervalType)
                .where(intervalType.openMilestoneId.eq(milestoneId)
                        .or(intervalType.closeMilestoneId.eq(milestoneId)))
                .fetch();
    }

    public IntervalType findByCode(final String code) {
        return new JPAQuery<IntervalType>(entityManager)
                .from(intervalType)
                .where(intervalType.code.equalsIgnoreCase(code))
                .fetchOne();
    }
}
