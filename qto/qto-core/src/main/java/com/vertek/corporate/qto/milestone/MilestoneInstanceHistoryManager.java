package com.vertek.corporate.qto.milestone;

import com.vertek.corporate.qto.common.StandardManager;

import javax.inject.Inject;
import java.util.List;

/**
 * @author rcasey
 * @since 2/17/2023
 */
public class MilestoneInstanceHistoryManager extends StandardManager<MilestoneInstanceHistory> {

    @Inject
    private MilestoneInstanceHistoryJpaDao dao;

    @Override
    protected MilestoneInstanceHistoryJpaDao getDao() {
        return dao;
    }

    public List<MilestoneInstanceHistory> findByMilestoneInstanceId(final Long milestoneInstanceId) {
        return getDao().findByMilestoneInstanceId(milestoneInstanceId);
    }
}
