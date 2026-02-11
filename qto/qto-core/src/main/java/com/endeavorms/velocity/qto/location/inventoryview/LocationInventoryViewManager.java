package com.endeavorms.velocity.qto.location.inventoryview;

import com.google.common.base.Strings;
import com.endeavorms.velocity.qto.common.PaginatedResult;
import com.endeavorms.velocity.qto.common.StandardManager;
import com.endeavorms.velocity.qto.order.Order;
import com.endeavorms.velocity.qto.order.OrderManager;
import com.endeavorms.velocity.qto.subject.Subject;
import com.endeavorms.velocity.qto.subject.SubjectManager;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author rcasey
 * @since 1/9/2023
 */
@Component
public class LocationInventoryViewManager extends StandardManager<LocationInventoryView> {

    /**
     * Persistence tier for LocationView.
     */
    @Inject
    private LocationInventoryViewJpaDao dao;

    @Inject
    private OrderManager orderManager;

    @Inject
    private SubjectManager subjectManager;

    @Override
    protected LocationInventoryViewJpaDao getDao() {
        return dao;
    }

    @Override
    public LocationInventoryView edit(final LocationInventoryView entity) {
        Order order = orderManager.retrieve(entity.getOrderId());
        if (!Strings.isNullOrEmpty(entity.getProvisioner())) {
            if ("Unassigned".equals(entity.getProvisioner())) {
                orderManager.handleProvisionerChange(order, null, false);
            } else {
                Subject subject = subjectManager.findByDisplayName(entity.getProvisioner());
                if (!subject.getId().equals(order.getProvisioner())) {
                    orderManager.handleProvisionerChangeEmail(order, order.getProvisioner(), subject.getId());
                    orderManager.handleProvisionerChange(order, subject.getId(), true);
                }
            }
        }
        return retrieve(entity.getId());
    }

    /**
     * Retrieves location views matching the given criteria, for populating the Network Inventory worklist.
     * @param criteria the criteria to filter by.
     * @return matching location views.
     */
    public PaginatedResult<LocationInventoryView> findInventoryBySearchCriteria(final LocationInventoryViewSearchCriteria criteria) {
        return getDao().findInventoryBySearchCriteria(criteria);
    }

    public PaginatedResult<LocationInventoryView> findBySearchCriteriaForLink(final LocationInventoryViewSearchCriteria criteria) {
        return getDao().findBySearchCriteriaForLink(criteria);
    }

    public InventoryWorklistMeta getInventoryWorklistMeta(final LocationInventoryViewSearchCriteria criteria) {
        return getDao().getInventoryWorklistMeta(criteria);
    }

    /**
     * Retrieves location views for the inventory service relocation.
     * @param criteria the criteria to filter by.
     * @return matching location views.
     */
    public PaginatedResult<LocationInventoryView> findBySearchCriteriaForServiceRelocate(final LocationInventoryViewSearchCriteria criteria) {
        return getDao().findBySearchCriteriaForServiceRelocate(criteria);
    }

    public List<String> findServiceTypes() {
        // Get service types from the DAO
        List<String> serviceTypes = getDao().findServiceTypes();
        // Use a Set to collect distinct types
        Set<String> distinctTypes = new HashSet<>();

        // Loop through service types and split each by comma
        for (String serviceType : serviceTypes) {
            // Split service type by comma and add to distinctTypes
            if (!Strings.isNullOrEmpty(serviceType)) {
                String[] parts = serviceType.split(",");
                for (String part : parts) {
                    distinctTypes.add(part.trim());
                }
            }
        }

        // Convert the Set to a List and return
        return new ArrayList<>(distinctTypes);
    }
}
