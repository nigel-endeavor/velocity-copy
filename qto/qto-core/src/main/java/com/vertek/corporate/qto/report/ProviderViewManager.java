package com.vertek.corporate.qto.report;

import com.vertek.corporate.qto.common.StandardManager;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.List;

@Stateless
public class ProviderViewManager extends StandardManager<ProviderIntervalsView> {

    @Inject
    private ProviderIntervalsJpaDao dao;

    @Override
    protected ProviderIntervalsJpaDao getDao() {
        return dao;
    }

    public List<ProviderIntervalsView> getProviderIntervals(final DashboardSearchCriteria criteria, final String intervalTypeCode,
                                                            final Integer numOfMonths) {
        return getDao().getProviderIntervals(criteria, intervalTypeCode, numOfMonths);
    }
}
