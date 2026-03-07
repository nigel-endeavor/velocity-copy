package com.vertek.corporate.qto.jeop;

import com.vertek.corporate.qto.common.BussinessDaysUtils;
import com.vertek.corporate.qto.common.PaginatedResult;
import com.vertek.corporate.qto.common.StandardManager;
import com.vertek.corporate.qto.service.Service;
import com.vertek.corporate.qto.service.ServiceManager;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author llevit
 */
@Stateless
public class ServiceJeopManager extends StandardManager<ServiceJeop> {

    /**
     * Persistence tier for ServiceJeop.
     */
    @Inject
    private ServiceJeopJpaDao dao;

    @Inject
    private JeopUnionViewJpaDao jeopUnionViewJpaDao;

    @Inject
    private ServiceManager serviceManager;

    @Override
    protected ServiceJeopJpaDao getDao() {
        return dao;
    }

    @Override
    public ServiceJeop create(final ServiceJeop jeop) {
        Service service = serviceManager.retrieve(jeop.getServiceId());
        jeop.setTenantId(service.getTenantId());
        jeop.setMasterCustomerId(service.getMasterCustomerId());
        if (jeop.getEndDate() != null) {
            jeop.setBusinessDaysOpen(BussinessDaysUtils.getBusinessDaysCount(jeop.getStartDate(), jeop.getEndDate()));
            jeop.setCalendarDaysOpen(BussinessDaysUtils.getCalendarDaysCount(jeop.getStartDate(), jeop.getEndDate()));
        }
        return this.getDao().create(jeop);
    }

    @Override
    public ServiceJeop edit(final ServiceJeop jeop) {
        if (jeop.getEndDate() != null && jeop.getStartDate().after(jeop.getEndDate())) {
            DateFormat outputDf = new SimpleDateFormat("MM/dd/yyyy");
            throw new RuntimeException(outputDf.format(jeop.getEndDate()) + " is before the start date "
                    + outputDf.format(jeop.getStartDate()) + " of jeopardy " + jeop.getDescription());
        }
        Service service = serviceManager.retrieve(jeop.getServiceId());
        jeop.setTenantId(service.getTenantId());
        jeop.setMasterCustomerId(service.getMasterCustomerId());
        if (jeop.getEndDate() != null) {
            jeop.setBusinessDaysOpen(BussinessDaysUtils.getBusinessDaysCount(jeop.getStartDate(), jeop.getEndDate()));
            jeop.setCalendarDaysOpen(BussinessDaysUtils.getCalendarDaysCount(jeop.getStartDate(), jeop.getEndDate()));
        }
        if (jeop.getVersion() == null) {
            ServiceJeop realJeop = this.getDao().retrieve(jeop.getId());
            if (realJeop.getVersion() != jeop.getViewVersion()) {
                throw new IllegalArgumentException("This Jeop has been modified by another user. Please refresh and try again.");
            }
            realJeop.setDescription(jeop.getDescription());
            realJeop.setLevel(jeop.getLevel());
            realJeop.setStartDate(jeop.getStartDate());
            realJeop.setEndDate(jeop.getEndDate());
            realJeop.setNote(jeop.getNote());
            realJeop.setResponsibility(jeop.getResponsibility());
            realJeop.setOriginator(jeop.getOriginator());
            realJeop.setBusinessDaysOpen(jeop.getBusinessDaysOpen());
            realJeop.setCalendarDaysOpen(jeop.getCalendarDaysOpen());
            realJeop.setAssignedTo(jeop.getAssignedTo());
            realJeop.setBusinessDaysOpen(jeop.getBusinessDaysOpen());
            return this.getDao().edit(realJeop);
        } else {
            return this.getDao().edit(jeop);
        }
    }

    public PaginatedResult<JeopUnionView> findBySearchCriteria(final JeopUnionViewSearchCriteria criteria) {
        return jeopUnionViewJpaDao.findBySearchCriteria(criteria);
    }

    /**
     * Gets list of all open jeops for service.
     *
     * @param serviceId of the serviceId.
     * @return List of open jeops.
     */
    public List<ServiceJeop> getOpen(final Long serviceId) {
        return dao.getOpen(serviceId);
    }

    /**
     * Gets list of closed jeops where the jeop start or end date (or both) falls between the intervalOpen and intervalClose dates.
     * @params serviceId service id
     * @param intervalOpen interval open date
     * @param intervalClose interval close date
     * @return list of ServiceJeops
     */
    public List<ServiceJeop> getIntervalJeops(final Long serviceId, final Date intervalOpen, final Date intervalClose) {
        return dao.getIntervalJeops(serviceId, intervalOpen, intervalClose);
    }

    /**
     * Gets list of all open jeops for service filtered by description.
     *
     * @param serviceId of the serviceId.
     * @return List of open jeops.
     */
    public List<ServiceJeop> findOpenByDescription(final Long serviceId, final String description) {
        return dao.findOpenByDescription(serviceId, description);
    }

    public List<ServiceJeop> findByServiceId(final Long serviceId) {
        return dao.findByServiceId(serviceId);
    }


}
