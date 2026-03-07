package com.vertek.corporate.qto.milestone;

import com.querydsl.jpa.impl.JPAQuery;
import com.vertek.corporate.qto.cdi.QtoDatabase;
import com.vertek.corporate.qto.common.AbstractJpaDao;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import static com.vertek.corporate.qto.milestone.QMilestoneDisplaySet.milestoneDisplaySet;

@Stateless
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
