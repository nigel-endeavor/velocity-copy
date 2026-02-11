package com.endeavorms.velocity.qto.interval;

import com.endeavorms.velocity.qto.common.StandardManager;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import java.util.List;

/**
 * @author rcasey
 * @since 3/9/2023
 */
@Component
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
