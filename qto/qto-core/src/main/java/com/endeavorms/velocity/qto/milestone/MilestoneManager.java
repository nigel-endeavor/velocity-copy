package com.endeavorms.velocity.qto.milestone;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;

/**
 * Business methods for Milestones.
 * @author <a href="rconnolly@vertek.com">rconnolly@vertek.com</a>.
 * @since 5.0.0 - 3/17/15
 */
@Component
public class MilestoneManager extends AbstractMilestoneManager<Milestone> {

    /** Data access for Milestones.*/
    @Inject
    private MilestoneJpaDao dao;


    @Override
    public MilestoneJpaDao getDao() {
        return dao;
    }
}