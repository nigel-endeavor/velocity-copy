package com.vertek.corporate.qto.milestone;

import com.vertek.corporate.qto.cdi.QtoDatabase;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

/**
 * Data access methods for Milestones.
 * @author <a href="rconnolly@vertek.com">rconnolly@vertek.com</a>.
 * @since 5.0.0 - 3/17/15
 */
@Stateless
public class MilestoneJpaDao extends AbstractMilestoneJpaDao<Milestone> {

    @Override
    @Inject
    protected void setEntityManager(@QtoDatabase final EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}