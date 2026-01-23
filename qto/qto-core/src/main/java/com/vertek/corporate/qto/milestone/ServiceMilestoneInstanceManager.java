package com.vertek.corporate.qto.milestone;

import com.vertek.corporate.qto.events.handlers.BaseEventHandler;
import com.vertek.corporate.qto.location.Location;
import com.vertek.corporate.qto.location.LocationManager;
import com.vertek.corporate.qto.service.Service;
import com.vertek.corporate.qto.service.ServiceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * @author fcurran
 */
@Stateless
public class ServiceMilestoneInstanceManager extends AbstractMilestoneInstanceManager<ServiceMilestoneInstance> {

    /** Logging. */
    protected static final Logger LOGGER = LoggerFactory.getLogger(ServiceMilestoneInstanceManager.class);


    /** Data access for Service Milestone instances. */
    @Inject
    private ServiceMilestoneInstanceJpaDao dao;

    /** Business methods for Milestones.*/
    @Inject
    private MilestoneManager milestoneManager;

    /** Business methods for Services. */
    @Inject
    private ServiceManager serviceManager;

    /** Business methods for Locations. */
    @Inject
    private LocationManager locationManager;

    @Override
    public ServiceMilestoneInstanceJpaDao getDao() {
        return dao;
    }

    @Override
    protected MilestoneManager getMilestoneManager() {
        return milestoneManager;
    }

    @Override
    public ServiceMilestoneInstance create(final ServiceMilestoneInstance entity) {
        ServiceMilestoneInstance existing = retrieveCurrentMilestoneByCode(entity.getServiceId(), entity.getMilestone().getCode());
        if (existing != null) {
            existing.setMilestoneDate(entity.getMilestoneDate());
            return edit(existing);
        } else {
            Service service = serviceManager.retrieve(entity.getServiceId());
            entity.setTenantId(service.getTenantId());
            entity.setMasterCustomerId(service.getMasterCustomerId());
            if (entity.getUseExistingInventoryLocationAddress() != null
                    && entity.getUseExistingInventoryLocationAddress()) {
                Location location = locationManager.retrieve(service.getLocationId());
                Location inventoryLocation
                    = locationManager.findInvByClientLocIdAndTenant(location.getClientLocationId(),
                location.getTenantId());
                location.setAddress1(inventoryLocation.getAddress1());
                location.setAddress2(inventoryLocation.getAddress2());
                location.setCity(inventoryLocation.getCity());
                location.setState(inventoryLocation.getState());
                location.setPostalCode(inventoryLocation.getPostalCode());
                location.setCountry(inventoryLocation.getCountry());
                locationManager.edit(location);
            }
            return super.create(entity);
        }
    }

    @Override
    public ServiceMilestoneInstance edit(final ServiceMilestoneInstance entity) {
        Service service = serviceManager.retrieve(entity.getServiceId());
        entity.setTenantId(service.getTenantId());
        entity.setMasterCustomerId(service.getMasterCustomerId());
        if (entity.getUseExistingInventoryLocationAddress() != null
                && entity.getUseExistingInventoryLocationAddress()) {
            Location location = locationManager.retrieve(service.getLocationId());
            Location inventoryLocation
                    = locationManager.findInvByClientLocIdAndTenant(location.getClientLocationId(),
                    location.getTenantId());
            location.setAddress1(inventoryLocation.getAddress1());
            location.setAddress2(inventoryLocation.getAddress2());
            location.setCity(inventoryLocation.getCity());
            location.setState(inventoryLocation.getState());
            location.setPostalCode(inventoryLocation.getPostalCode());
            location.setCountry(inventoryLocation.getCountry());
            locationManager.edit(location);
        }
        return super.edit(entity);
    }

    @Override
    protected void handleMilestoneEvent(final ServiceMilestoneInstance milestoneInstance) {
        Service service = serviceManager.retrieve(milestoneInstance.getServiceId());

        BaseEventHandler handler = BaseEventHandler
                .omsEventHandlerFactory("com.vertek.corporate.qto.events.handlers.BaseEventHandler");
        handler.handleMilestoneEvent(milestoneInstance, service);
    }

    @Override
    protected ServiceMilestoneInstance newMilestoneInstance(final Long serviceId) {
        Service service = serviceManager.retrieve(serviceId);
        ServiceMilestoneInstance serviceMilestoneInstance = new ServiceMilestoneInstance();
        serviceMilestoneInstance.setServiceId(serviceId);
        serviceMilestoneInstance.setTenantId(service.getTenantId());
        serviceMilestoneInstance.setMasterCustomerId(service.getMasterCustomerId());
        return serviceMilestoneInstance;
    }

    @Override
    protected void setRecordId(final ServiceMilestoneInstance serviceMilestoneInstance, final Long serviceId) {
        serviceMilestoneInstance.setServiceId(serviceId);
    }

    public List<ServiceMilestoneInstance> listByRecord(final Long serviceId) {
        return dao.listByRecord(serviceId);
    }

       /**
     * Checks if an instance exists for the given site and milestone code.
     * @param serviceId order id.
     * @param milestoneCode milestone code.
     * @return True if an instance already exists, false otherwise.
     */
    public boolean doesMilestoneExist(final Long serviceId, final String milestoneCode) {
        return dao.doesMilestoneExist(serviceId, milestoneCode);
    }

    /**
     * Retrieves current milestone instance by identifiers.
     * @param serviceId service id
     * @param milestoneId milestone id
     * @return current service milestone instance
     */
    public ServiceMilestoneInstance retrieveCurrentMilestone(final Long serviceId, final Long milestoneId) {
        return getDao().retrieveCurrentMilestone(serviceId, milestoneId);
    }

        /**
     * Retrieves current milestone by Code.
     * @param serviceId service id.
     * @param milestoneCode milestone code.
     * @return current order milestone instance.
     */
    public ServiceMilestoneInstance retrieveCurrentMilestoneByCode(final Long serviceId, final String milestoneCode) {
        return dao.retrieveCurrentMilestoneByCode(serviceId, milestoneCode);
    }

        /**
     * Retrieves current Billable Milestones.
     *
     * @param tenantId            Tenant id.
     * @param milestoneCode milestone code.
     * @param endDate       end date.
     * @return current order milestone instance.
     */
    public List<ServiceMilestoneInstance> retrieveBillableMilestones(final Long tenantId, final String milestoneCode,
                                                                     final Date endDate) {
        return dao.retrieveBillableMilestones(tenantId, milestoneCode, endDate);
    }

      /**
     * Retrieves current milestone by Invoice ID.
     * @param invoiceId invoice id.
     * @return  milestone instance.
     */
    public List<ServiceMilestoneInstance> findByInvoiceId(final Long invoiceId) {
        return dao.findByInvoiceId(invoiceId);
    }

    public List<ServiceMilestoneInstance> findByServiceId(Long serviceId) {
        return dao.findByServiceId(serviceId);
    }
}
