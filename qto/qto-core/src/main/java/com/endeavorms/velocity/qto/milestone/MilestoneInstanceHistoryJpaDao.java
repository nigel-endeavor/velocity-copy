package com.endeavorms.velocity.qto.milestone;

import com.endeavorms.velocity.qto.cdi.QtoDatabase;
import com.endeavorms.velocity.qto.common.AbstractJpaDao;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * @author rcasey
 * @since 2/17/2023
 */

@Repository
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
