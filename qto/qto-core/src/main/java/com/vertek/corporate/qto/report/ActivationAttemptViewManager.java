package com.vertek.corporate.qto.report;

import com.vertek.corporate.qto.common.StandardManager;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.List;

@Stateless
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
