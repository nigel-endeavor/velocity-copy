package com.vertek.corporate.qto.interval;

import com.vertek.corporate.qto.common.StandardManager;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author rcasey
 * @since 3/13/2023
 */
@Stateless
public class IntervalInstanceManager extends StandardManager<IntervalInstance> {

    @Inject
    private IntervalInstanceJpaDao dao;

    @Override
    public IntervalInstanceJpaDao getDao() {
        return dao;
    }

    public List<IntervalInstance> findByMilestoneInstanceId(final Long milestoneInstanceId) {
        return getDao().findByMilestoneInstanceId(milestoneInstanceId);
    }

    public void reopenIntervalInstance(final IntervalInstance intervalInstance) {
        intervalInstance.setCloseMilestoneInstanceId(null);
        intervalInstance.setBusinessDayIntervalTime(BigDecimal.ZERO);
        intervalInstance.setCalendarDayIntervalTime(BigDecimal.ZERO);
        intervalInstance.setClientBusinessDayDeductTime(BigDecimal.ZERO);
        intervalInstance.setClientCalendarDayDeductTime(BigDecimal.ZERO);
        intervalInstance.setCustomerBusinessDayDeductTime(BigDecimal.ZERO);
        intervalInstance.setCustomerCalendarDayDeductTime(BigDecimal.ZERO);
        intervalInstance.setProviderBusinessDayDeductTime(BigDecimal.ZERO);
        intervalInstance.setProviderCalendarDayDeductTime(BigDecimal.ZERO);
        edit(intervalInstance);
    }
}
