package com.vertek.corporate.qto.interval;

import com.google.common.base.Strings;
import com.vertek.corporate.qto.common.BussinessDaysUtils;
import com.vertek.corporate.qto.common.StandardManager;
import com.vertek.corporate.qto.jeop.ServiceJeop;
import com.vertek.corporate.qto.jeop.ServiceJeopManager;
import com.vertek.corporate.qto.milestone.MilestoneInstance;
import com.vertek.corporate.qto.milestone.ServiceMilestoneInstanceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author rcasey
 * @since 1.0.0
 */
@Stateless
public class ServiceIntervalInstanceManager extends StandardManager<ServiceIntervalInstance> {

    /** Private Logger for this class. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceIntervalInstanceManager.class);

    @Inject
    private ServiceIntervalInstanceJpaDao dao;

    @Inject
    private ServiceMilestoneInstanceManager sMilestoneIntanceManager;

    @Inject
    private ServiceJeopManager sJeopManager;

    @Override
    public ServiceIntervalInstanceJpaDao getDao() {
        return dao;
    }

    public ServiceIntervalInstance getServiceInterval(final Long serviceId, final Long intervalTypeId) {
        return getDao().getServiceInterval(serviceId, intervalTypeId);
    }

    public void openServiceInterval(Long serviceId, Long milestoneInstanceId,
                                    Long intervalTypeId) {
        ServiceIntervalInstance openInterval = getServiceInterval(serviceId, intervalTypeId);

        //interval doesn't exist, create it
        if (openInterval == null) {
            ServiceIntervalInstance instance = new ServiceIntervalInstance();
            instance.setServiceId(serviceId);
            instance.setOpenMilestoneInstanceId(milestoneInstanceId);
            instance.setIntervalTypeId(intervalTypeId);
            create(instance);
        } else {
            //if interval exists, but is not closed. Do nothing.
            //if interval exists, and is closed. Update interval time fields.
            if (openInterval.getCloseMilestoneInstanceId() != null) {
                setCalculatedFields(openInterval);
                edit(openInterval);
            }
        }
    }

    public void closeServiceInterval(Long serviceId, Long milestoneInstanceId, Long intervalTypeId) {
        ServiceIntervalInstance interval = getServiceInterval(serviceId, intervalTypeId);
        if (interval != null && interval.getCloseMilestoneInstanceId() == null) {
            interval.setCloseMilestoneInstanceId(milestoneInstanceId);
            setCalculatedFields(interval);
            edit(interval);
        }
    }

    public List<ServiceIntervalInstance> findByServiceId(final Long serviceId) {
        return getDao().findByServiceId(serviceId);
    }

    private void setCalculatedFields(final ServiceIntervalInstance interval) {
        MilestoneInstance openMilestone = sMilestoneIntanceManager.retrieve(interval.getOpenMilestoneInstanceId());
        MilestoneInstance closeMilestone = sMilestoneIntanceManager.retrieve(interval.getCloseMilestoneInstanceId());
        Date openDate = openMilestone.getMilestoneDate();
        Date closeDate = closeMilestone.getMilestoneDate();

        interval.setBusinessDayIntervalTime(BigDecimal.valueOf(BussinessDaysUtils.getBusinessDaysCount(openDate, closeDate)));
        interval.setCalendarDayIntervalTime(BigDecimal.valueOf(BussinessDaysUtils.getCalendarDaysCount(openDate, closeDate)));

        List<ServiceJeop> jeops = sJeopManager.getIntervalJeops(interval.getServiceId(), openDate, closeDate);
        for (ServiceJeop jeop : jeops) {
            Integer jeopBusinessDayTime;
            Integer jeopCalendarDayTime;

            if (jeop.getStartDate().after(openDate) && jeop.getEndDate().before(closeDate)) {
                //Jeop starts and ends during the interval
                jeopBusinessDayTime = BussinessDaysUtils.getBusinessDaysCount(jeop.getStartDate(), jeop.getEndDate());
                jeopCalendarDayTime = BussinessDaysUtils.getCalendarDaysCount(jeop.getStartDate(), jeop.getEndDate());
            } else if (jeop.getStartDate().after(openDate) && jeop.getEndDate().after(closeDate)) {
                //Jeop starts during the interval and ends after it closes
                jeopBusinessDayTime = BussinessDaysUtils.getBusinessDaysCount(jeop.getStartDate(), closeDate);
                jeopCalendarDayTime = BussinessDaysUtils.getCalendarDaysCount(jeop.getStartDate(), closeDate);
            } else if (jeop.getStartDate().before(openDate) && jeop.getEndDate().before(closeDate)) {
                //Jeop starts before the interval and closes during the interval
                jeopBusinessDayTime = BussinessDaysUtils.getBusinessDaysCount(openDate, jeop.getEndDate());
                jeopCalendarDayTime = BussinessDaysUtils.getCalendarDaysCount(openDate, jeop.getEndDate());
            } else {
                //Jeop starts before the interval and closes after the interval (spans the whole interval)
                jeopBusinessDayTime = BussinessDaysUtils.getBusinessDaysCount(openDate, closeDate);
                jeopCalendarDayTime = BussinessDaysUtils.getCalendarDaysCount(openDate, closeDate);
            }

            if (!Strings.isNullOrEmpty(jeop.getResponsibility())) {
                switch (jeop.getResponsibility()) {
                    case "Client":
                        interval.setClientBusinessDayDeductTime(interval.getClientBusinessDayDeductTime().add(new BigDecimal(jeopBusinessDayTime)));
                        interval.setClientCalendarDayDeductTime(interval.getClientCalendarDayDeductTime().add(new BigDecimal(jeopCalendarDayTime)));
                        break;
                    case "End Customer":
                        interval.setCustomerBusinessDayDeductTime(interval.getCustomerBusinessDayDeductTime().add(new BigDecimal(jeopBusinessDayTime)));
                        interval.setCustomerCalendarDayDeductTime(interval.getCustomerCalendarDayDeductTime().add(new BigDecimal(jeopCalendarDayTime)));
                        break;
                    case "Provider":
                        interval.setProviderBusinessDayDeductTime(interval.getProviderBusinessDayDeductTime().add(new BigDecimal(jeopBusinessDayTime)));
                        interval.setProviderCalendarDayDeductTime(interval.getProviderCalendarDayDeductTime().add(new BigDecimal(jeopCalendarDayTime)));
                        break;
                }
            }
        }
    }
}
