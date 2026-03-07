package com.vertek.corporate.qto.milestone;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

/**
 * Business methods for Milestones.
 * @author <a href="rconnolly@vertek.com">rconnolly@vertek.com</a>.
 * @since 5.0.0 - 3/17/15
 */
@Stateless
public class MilestoneManager extends AbstractMilestoneManager<Milestone> {

    /** Data access for Milestones.*/
    @Inject
    private MilestoneJpaDao dao;


    @Override
    public MilestoneJpaDao getDao() {
        return dao;
    }
}