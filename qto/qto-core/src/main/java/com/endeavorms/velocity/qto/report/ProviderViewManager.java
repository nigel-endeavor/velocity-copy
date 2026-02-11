package com.endeavorms.velocity.qto.report;

import com.endeavorms.velocity.qto.common.StandardManager;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import java.util.List;

@Component
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
