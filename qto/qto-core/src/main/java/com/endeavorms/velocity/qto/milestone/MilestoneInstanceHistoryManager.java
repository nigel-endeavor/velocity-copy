package com.endeavorms.velocity.qto.milestone;

import com.endeavorms.velocity.qto.common.StandardManager;

import jakarta.inject.Inject;
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
