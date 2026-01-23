package com.vertek.corporate.qto.milestone;

import com.vertek.corporate.qto.cdi.QtoDatabase;
import com.vertek.corporate.qto.common.AbstractJpaDao;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

/**
 * @author rcasey
 * @since 2/17/2023
 */
public class MilestoneInstanceHistoryJpaDao extends AbstractJpaDao<MilestoneInstanceHistory, Long> {
    @Override
    @Inject
    protected void setEntityManager(@QtoDatabase final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<MilestoneInstanceHistory> findByMilestoneInstanceId(Long milestoneInstanceId) {
        return entityManager.createQuery("SELECT m FROM MilestoneInstanceHistory m WHERE m.milestoneInstanceId = :milestoneInstanceId", MilestoneInstanceHistory.class)
                .setParameter("milestoneInstanceId", milestoneInstanceId)
                .getResultList();
    }
}
