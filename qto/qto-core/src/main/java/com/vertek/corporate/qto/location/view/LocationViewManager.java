package com.vertek.corporate.qto.location.view;

import com.google.common.base.Strings;
import com.vertek.corporate.qto.common.PaginatedResult;
import com.vertek.corporate.qto.common.StandardManager;
import com.vertek.corporate.qto.order.Order;
import com.vertek.corporate.qto.order.OrderManager;
import com.vertek.corporate.qto.subject.Subject;
import com.vertek.corporate.qto.subject.SubjectManager;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author rcasey
 * @since 1/9/2023
 */
@Stateless
public class LocationViewManager extends StandardManager<LocationView> {

    /**
     * Persistence tier for LocationView.
     */
    @Inject
    private LocationViewJpaDao dao;

    @Inject
    private OrderManager orderManager;

    @Inject
    private SubjectManager subjectManager;

    @Override
    protected LocationViewJpaDao getDao() {
        return dao;
    }

    @Override
    public LocationView edit(final LocationView entity) {
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

    public PaginatedResult<LocationView> findBySearchCriteria(final LocationViewSearchCriteria criteria) {
        return getDao().findBySearchCriteria(criteria);
    }


    public PaginatedResult<LocationView> findBySearchCriteriaForServiceRelocate(final LocationViewSearchCriteria criteria){
        return getDao().findBySearchCriteriaForServiceRelocate(criteria);
    }

    public List<String> findServiceTypes() {
        List<String> serviceTypes = getDao().findServiceTypes();
        Set<String> distinctTypes = new HashSet<>();

        // Loop through service types and split each by comma
        for (String serviceType : serviceTypes) {
            if (serviceType == null) {
                continue;
            }
            String[] parts = serviceType.split(",");
            for (String part : parts) {
                distinctTypes.add(part.trim());
            }
        }
        return new ArrayList<>(distinctTypes);
    }
}
