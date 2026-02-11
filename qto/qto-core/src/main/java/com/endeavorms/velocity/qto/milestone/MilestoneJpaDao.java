package com.endeavorms.velocity.qto.milestone;

import com.endeavorms.velocity.qto.cdi.QtoDatabase;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

/**
 * Data access methods for Milestones.
 * @author <a href="rconnolly@vertek.com">rconnolly@vertek.com</a>.
 * @since 5.0.0 - 3/17/15
 */
@Component
public class MilestoneJpaDao extends AbstractMilestoneJpaDao<Milestone> {

    @Override
    @Inject
    protected void setEntityManager(@QtoDatabase final EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}