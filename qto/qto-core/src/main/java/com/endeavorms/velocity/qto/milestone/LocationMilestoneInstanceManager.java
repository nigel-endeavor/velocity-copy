package com.endeavorms.velocity.qto.milestone;

import com.endeavorms.velocity.qto.events.handlers.BaseEventHandler;
import com.endeavorms.velocity.qto.location.Location;
import com.endeavorms.velocity.qto.location.LocationManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * @author fcurran
 */
@Component
public class LocationMilestoneInstanceManager extends AbstractMilestoneInstanceManager<LocationMilestoneInstance> {

    /** Logging. */
    protected static final Logger LOGGER = LoggerFactory.getLogger(LocationMilestoneInstanceManager.class);


    /** Data access for Location Milestone instances. */
    @Inject
    private LocationMilestoneInstanceJpaDao dao;

    /** Business methods for Milestones.*/
    @Inject
    private MilestoneManager milestoneManager;

    /** Business methods for Locations.*/
    @Inject
    private LocationManager locationManager;

    @Inject
    private BaseEventHandler baseEventHandler;

    @Override
    public LocationMilestoneInstanceJpaDao getDao() {
        return dao;
    }

    @Override
    protected MilestoneManager getMilestoneManager() {
        return milestoneManager;
    }

    @Override
    public LocationMilestoneInstance create(final LocationMilestoneInstance entity) {
        Location location = locationManager.retrieve(entity.getLocationId());
        entity.setTenantId(location.getTenantId());
        entity.setMasterCustomerId(location.getMasterCustomerId());
        return super.create(entity);
    }

    @Override
    public LocationMilestoneInstance edit(final LocationMilestoneInstance entity) {
        Location location = locationManager.retrieve(entity.getLocationId());
        entity.setTenantId(location.getTenantId());
        entity.setMasterCustomerId(location.getMasterCustomerId());
        return super.edit(entity);
    }

    @Override
    protected void handleMilestoneEvent(final LocationMilestoneInstance milestoneInstance) {
        Location location = locationManager.retrieve(milestoneInstance.getLocationId());

        baseEventHandler.handleMilestoneEvent(milestoneInstance, location);
    }

    @Override
    protected LocationMilestoneInstance newMilestoneInstance(final Long locationId) {
        Location location = locationManager.retrieve(locationId);
        LocationMilestoneInstance locationMilestoneInstance = new LocationMilestoneInstance();
        locationMilestoneInstance.setLocationId(locationId);
        locationMilestoneInstance.setTenantId(location.getTenantId());
        locationMilestoneInstance.setMasterCustomerId(location.getMasterCustomerId());
        return locationMilestoneInstance;
    }

    @Override
    protected void setRecordId(final LocationMilestoneInstance locationMilestoneInstance, final Long locationId) {
        locationMilestoneInstance.setLocationId(locationId);
    }

    public List<LocationMilestoneInstance> listByRecord(final Long locationId) {
        return dao.listByRecord(locationId);
    }

         /**
     * Checks if an instance exists for the given location and milestone code.
     * @param locationId order id.
     * @param milestoneCode milestone code.
     * @return True if an instance already exists, false otherwise.
     */
    public boolean doesMilestoneExist(Long locationId, String milestoneCode) {
                return dao.doesMilestoneExist(locationId, milestoneCode);
    }

            /**
     * Retrieves current milestone by name.
     * @param id service id.
     * @param milestoneCode milestone code.
     * @return current order milestone instance.
     */
    public LocationMilestoneInstance retrieveCurrentMilestoneByCode(final Long id, final String milestoneCode) {
        return dao.retrieveCurrentMilestoneByCode(id, milestoneCode);
    }

       /**
     * Retrieves current Billable Milestones.
     *
     * @param tenantId            Tenant id.
     * @param milestoneCode milestone code.
     * @param endDate       end date.
     * @return current order milestone instance.
     */
    public List<LocationMilestoneInstance> retrieveBillableMilestones(final Long tenantId, final String milestoneCode,
                                                                     final Date endDate) {
        return dao.retrieveBillableMilestones(tenantId, milestoneCode, endDate);
    }

          /**
     * Retrieves current milestone by Invoice ID.
     * @param invoiceId invoice id.
     * @return  milestone instance.
     */
    public List<LocationMilestoneInstance> findByInvoiceId(final Long invoiceId) {
        return dao.findByInvoiceId(invoiceId);
    }

        /**
     * Retrieves current milestone by Location ID.
     * @param locationId location id.
     * @return  milestone instances.
     */
    public List<LocationMilestoneInstance> findByLocationId(final Long locationId) {
        return dao.findByLocationId(locationId);
    }
}
