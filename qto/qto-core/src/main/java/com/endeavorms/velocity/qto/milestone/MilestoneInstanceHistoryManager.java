package com.endeavorms.velocity.qto.milestone;

import com.endeavorms.velocity.qto.common.StandardManager;

import jakarta.inject.Inject;
import java.util.List;

/**
 * @author rcasey
 * @since 2/17/2023
 */

import com.endeavorms.velocity.qto.common.StandardManager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MilestoneInstanceHistoryManager extends StandardManager<MilestoneInstanceHistory> {

    @Autowired
    private MilestoneInstanceHistoryJpaDao dao;

    @Override
    protected MilestoneInstanceHistoryJpaDao getDao() {
        return dao;
    }

    public List<MilestoneInstanceHistory> findByMilestoneInstanceId(final Long milestoneInstanceId) {
        return getDao().findByMilestoneInstanceId(milestoneInstanceId);
    }
}
