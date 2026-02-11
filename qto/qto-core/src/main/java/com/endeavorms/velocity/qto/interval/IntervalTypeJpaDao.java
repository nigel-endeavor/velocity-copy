package com.endeavorms.velocity.qto.interval;

import com.querydsl.jpa.impl.JPAQuery;
import com.endeavorms.velocity.qto.cdi.QtoDatabase;
import com.endeavorms.velocity.qto.common.AbstractJpaDao;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.List;

import static com.endeavorms.velocity.qto.interval.QIntervalType.intervalType;

/**
 * @author rcasey
 * @since 3/9/2023
 */
@Component
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
