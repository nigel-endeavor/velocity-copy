package com.vertek.corporate.qto.service.view;

import com.google.common.base.Strings;
import com.vertek.corporate.qto.common.PaginatedResult;
import com.vertek.corporate.qto.common.StandardManager;
import com.vertek.corporate.qto.order.Order;
import com.vertek.corporate.qto.order.OrderManager;
import com.vertek.corporate.qto.service.Service;
import com.vertek.corporate.qto.service.ServiceManager;
import com.vertek.corporate.qto.subject.Subject;
import com.vertek.corporate.qto.subject.SubjectManager;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.List;

/**
 * @author rcasey
 * @since 2/23/2023
 */
@Stateless
public class ServiceViewManager extends StandardManager<ServiceView> {

    /**
     * Persistence tier for ServiceView.
     */
    @Inject
    private ServiceViewJpaDao dao;

    @Inject
    private OrderManager orderManager;

    @Inject
    private ServiceManager serviceManager;

    @Inject
    private SubjectManager subjectManager;

    @Override
    protected ServiceViewJpaDao getDao() {
        return dao;
    }

    @Override
    public ServiceView edit(final ServiceView entity) {
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
        Service service = serviceManager.retrieve(entity.getId());
        service.setFollowUpDate(entity.getFollowUpDate());
        return retrieve(entity.getId());
    }

    public PaginatedResult<ServiceView> findBySearchCriteria(final ServiceViewSearchCriteria criteria) {
        return getDao().findBySearchCriteria(criteria);
    }

    public PaginatedResult<ServiceView> getServiceViewsForLink(ServiceViewSearchCriteria criteria) {
        return getDao().getServiceViewsForLink(criteria);
    }

    public PaginatedResult<ServiceView> getServiceViewsForBundle(ServiceViewSearchCriteria criteria) {
        return getDao().getServiceViewsForBundle(criteria);
    }

    public List<String> findServiceTypes(){
        return getDao().findServiceTypes();
    }
}
