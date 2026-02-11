package com.endeavorms.velocity.qto.report;

import com.endeavorms.velocity.qto.common.StandardManager;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import java.util.List;

@Component
public class ActivationAttemptViewManager extends StandardManager<ActivationAttemptView> {

    @Inject
    private ActivationAttemptViewJpaDao dao;

    @Override
    protected ActivationAttemptViewJpaDao getDao() {
        return dao;
    }



    public List<ActivationAttemptView> getServiceIntervals(DashboardSearchCriteria criteria, final Integer numOfMonths) {
        return getDao().getServiceIntervals(criteria, numOfMonths);
    }

}
