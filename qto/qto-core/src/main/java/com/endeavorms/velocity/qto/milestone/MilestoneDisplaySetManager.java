package com.endeavorms.velocity.qto.milestone;

import com.endeavorms.velocity.qto.common.StandardManager;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;

@Component
public class MilestoneDisplaySetManager extends StandardManager<MilestoneDisplaySet> {

    @Inject
    private MilestoneDisplaySetJpaDao dao;

    @Override
    protected MilestoneDisplaySetJpaDao getDao() {
        return dao;
    }

    public MilestoneDisplaySet getByDisplayGroup(final String displayGroup) {
        return dao.getByDisplayGroup(displayGroup);
    }

    /**
     * Get the MilestoneDisplaySet for the given displayType.
     * @param displayType
     * @return
     */
    public MilestoneDisplaySet getByDisplayType(final String displayType) {
        return dao.getByDisplayType(displayType);
    }
}
