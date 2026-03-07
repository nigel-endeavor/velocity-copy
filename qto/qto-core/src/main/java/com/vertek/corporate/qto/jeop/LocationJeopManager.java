package com.vertek.corporate.qto.jeop;

import com.vertek.corporate.qto.common.BussinessDaysUtils;
import com.vertek.corporate.qto.common.PaginatedResult;
import com.vertek.corporate.qto.common.StandardManager;
import com.vertek.corporate.qto.location.Location;
import com.vertek.corporate.qto.location.LocationManager;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.List;


/**
 * @author llevit
 */
@Stateless
public class LocationJeopManager extends StandardManager<LocationJeop> {

    /**
     * Persistence tier for LocationJeop.
     */
    @Inject
    private LocationJeopJpaDao dao;

    @Inject
    private JeopUnionViewJpaDao jeopUnionViewJpaDao;

    @Inject
    private LocationManager locationManager;

    @Override
    protected LocationJeopJpaDao getDao() {
        return dao;
    }

    @Override
    public LocationJeop create(final LocationJeop jeop) {
        Location location = locationManager.retrieve(jeop.getLocationId());
        jeop.setTenantId(location.getTenantId());
        jeop.setMasterCustomerId(location.getMasterCustomerId());
        if (jeop.getEndDate() != null) {
            jeop.setBusinessDaysOpen(BussinessDaysUtils.getBusinessDaysCount(jeop.getStartDate(), jeop.getEndDate()));
            jeop.setCalendarDaysOpen(BussinessDaysUtils.getCalendarDaysCount(jeop.getStartDate(), jeop.getEndDate()));
        }
        return this.getDao().create(jeop);
    }

    @Override
    public LocationJeop edit(final LocationJeop jeop) {
        Location location = locationManager.retrieve(jeop.getLocationId());
        jeop.setTenantId(location.getTenantId());
        jeop.setMasterCustomerId(location.getMasterCustomerId());
        if (jeop.getEndDate() != null) {
            jeop.setBusinessDaysOpen(BussinessDaysUtils.getBusinessDaysCount(jeop.getStartDate(), jeop.getEndDate()));
            jeop.setCalendarDaysOpen(BussinessDaysUtils.getCalendarDaysCount(jeop.getStartDate(), jeop.getEndDate()));
        }
        if (jeop.getVersion() == null) {
            LocationJeop realJeop = this.getDao().retrieve(jeop.getId());
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
     * Gets list of all open jeops for an location.
     * @param locationId Id of the location.
     * @return List of open jeops.
     */
    public List<LocationJeop> getOpen(final Long locationId) {
        return dao.getOpen(locationId);
    }

            /**
     * Gets list of all open jeops for an location.
     *
     * @param locationId of the location.
     * @return List of open jeops.
     */
    public List<LocationJeop> getByLocationId(final Long locationId) {
        return dao.getByLocationId(locationId);
    }
}
