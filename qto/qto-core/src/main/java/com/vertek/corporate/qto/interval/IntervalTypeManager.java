package com.vertek.corporate.qto.interval;

import com.vertek.corporate.qto.common.StandardManager;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.List;

/**
 * @author rcasey
 * @since 3/9/2023
 */
@Stateless
public class IntervalTypeManager extends StandardManager<IntervalType> {

    @Inject
    private IntervalTypeJpaDao dao;

    @Override
    public IntervalTypeJpaDao getDao() {
        return dao;
    }

    public List<IntervalType> findByMilestoneId(final Long milestoneId) {
        return getDao().findByMilestoneId(milestoneId);
    }

    public IntervalType findByCode(final String code) {
        return getDao().findByCode(code);
    }
}
