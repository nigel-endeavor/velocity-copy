package com.vertek.corporate.qto.invoicing.surcharge.service;

import com.vertek.corporate.qto.common.PaginatedResult;
import com.vertek.corporate.qto.common.SecurityUtils;
import com.vertek.corporate.qto.common.StandardManager;
import com.vertek.corporate.qto.invoicing.surchargeType.SurchargeType;
import com.vertek.corporate.qto.invoicing.surchargeType.SurchargeTypeManager;
import com.vertek.corporate.qto.service.Service;
import com.vertek.corporate.qto.service.ServiceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.Date;
import java.util.List;

/**
 * Business logic layer for ServiceSurcharges.
 */
@Stateless
public class ServiceSurchargeManager extends StandardManager<ServiceSurcharge> {
    /**
     * Logging Facade.
     */
    protected static final Logger LOGGER = LoggerFactory.getLogger(ServiceSurchargeManager.class);

    @Inject
    private ServiceManager serviceManager;
    /** Business logic for surcharge types. */
    @Inject
    private SurchargeTypeManager surchargeTypeManager;
    /** Persistence layer for ServiceSurcharges. */
    @Inject
    private ServiceSurchargeJpaDao dao;
    @Override
    protected ServiceSurchargeJpaDao getDao() {
        return dao;
    }

    /**
     * Returns service surcharges that match the provided search criteria.
     * @param criteria what to match on.
     * @return the matching entities, if any.
     */
    public PaginatedResult<ServiceSurcharge> findBySearchCriteria(final ServiceSurchargeSearchCriteria criteria) {
        return getDao().findBySearchCriteria(criteria);
    }

    /**
     * Find a ServiceSurcharge by serviceId and typeId.
     * @param serviceId service id.
     * @param typeId type id.
     * @return ServiceSurcharge.
     */
    public ServiceSurcharge findByServiceIdAndType(final Long serviceId, final Long typeId) {
        return dao.findByServiceIdAndType(serviceId, typeId);
    }

            /**
     * Find a ServiceSurcharge by serviceId and typeId.
     * @param serviceId service id.
     * @param type type.
     * @return ServiceSurcharge.
     */
    public List<ServiceSurcharge> findByServiceIdAndTypeName(final Long serviceId, final String type) {
        return dao.findByServiceIdAndTypeName(serviceId, type);
    }

    public List<ServiceSurcharge> findByServiceId(final Long serviceId) {
        return dao.findByServiceId(serviceId);
    }

    /**
     * Create a surcharge for a service.
     * @param service service.
     * @param type surcharge type.
     */
    public void createServiceSurcharge(final Service service, final String type) {
        SurchargeType surchargeType = surchargeTypeManager.findByType(type, service.getTenantId());
        createServiceSurcharge(service, surchargeType);
    }

    /**
     * Create a surcharge for a service.
     * @param service service.
     * @param surchargeType surcharge type.
     */
    public void createServiceSurcharge(final Service service, final SurchargeType surchargeType) {
        ServiceSurcharge surcharge = new ServiceSurcharge();
        surcharge.setServiceId(service.getId());
        surcharge.setAddedBy(SecurityUtils.getLoggedInUser());
        surcharge.setSurchargeDate(new Date());
        surcharge.setTenantId(service.getTenantId());
        surcharge.setMasterCustomerId(service.getMasterCustomerId());
        surcharge.setSurchargeType(surchargeType);
        create(surcharge);
    }

    @Override
    public ServiceSurcharge create(final ServiceSurcharge surcharge) {
        Service service = serviceManager.retrieve(surcharge.getServiceId());
        surcharge.setTenantId(service.getTenantId());
        surcharge.setMasterCustomerId(service.getMasterCustomerId());
        return super.create(surcharge);
    }

        /**
     * Find a ServiceSurcharge by invoiceId.
     * @param invoiceId inoviceid.
     * @return surcharge.
     */
    public ServiceSurcharge findByInvoiceID(final Long invoiceId) {
        return dao.findByInvoiceID(invoiceId);
    }

        /**
     * Find all ServiceSurcharges to be invoiced.
     * @param endDate invoice end date + 1 day.
     * @return surcharges list of surcharges.
     */
    public List<ServiceSurcharge> findForInvoice(final Date endDate, final Long tenantId) {
        return dao.findForInvoice(endDate, tenantId);
    }
}
