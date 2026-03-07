package com.vertek.corporate.qto.milestone;

import com.vertek.corporate.qto.common.StandardManager;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.List;

@Stateless
public class MilestoneDisplaySetIncludeManager extends StandardManager<MilestoneDisplaySetInclude> {

    @Inject
    private MilestoneDisplaySetIncludeJpaDao dao;

    @Override
    protected MilestoneDisplaySetIncludeJpaDao getDao() {
        return dao;
    }
}
