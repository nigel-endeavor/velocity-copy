package com.endeavorms.velocity.qto.milestone;

import com.querydsl.jpa.impl.JPAQuery;
import com.endeavorms.velocity.qto.cdi.QtoDatabase;
import com.endeavorms.velocity.qto.common.AbstractJpaDao;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import static com.endeavorms.velocity.qto.milestone.QMilestoneDisplaySet.milestoneDisplaySet;

@Component
public class MilestoneDisplaySetJpaDao extends AbstractJpaDao<MilestoneDisplaySet, Long> {

    @Override
    @Inject
    protected void setEntityManager(@QtoDatabase final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public MilestoneDisplaySet getByDisplayGroup(final String displayGroup) {
        return new JPAQuery<MilestoneDisplaySet>(entityManager)
                .from(milestoneDisplaySet)
                .where(milestoneDisplaySet.displayGroup.eq(displayGroup))
                .fetchOne();
    }


    /**
     * Get the MilestoneDisplaySet for the given displayType.
     * @param displayType
     * @return
     */
    public MilestoneDisplaySet getByDisplayType(final String displayType) {
        return new JPAQuery<MilestoneDisplaySet>(entityManager)
                .from(milestoneDisplaySet)
                .where(milestoneDisplaySet.displayType.eq(displayType))
                .fetchOne();
    }
}
