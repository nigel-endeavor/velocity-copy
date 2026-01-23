package com.vertek.corporate.qto.milestone;

import com.vertek.corporate.qto.common.StandardManager;

import javax.ejb.Stateless;
import javax.inject.Inject;
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
