package com.vertek.corporate.qto.milestone;

import com.vertek.corporate.qto.common.StandardManager;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

@Stateless
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
