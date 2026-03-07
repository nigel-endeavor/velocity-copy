package com.vertek.corporate.qto.service.cyberView;

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

@Stateless
public class ServiceCyberViewManager extends StandardManager<ServiceCyberView> {

    /**
     * Persistence tier for ServiceView.
     */
    @Inject
    private ServiceCyberViewJpaDao dao;

    @Inject
    private OrderManager orderManager;

    @Inject
    private ServiceManager serviceManager;

    @Inject
    private SubjectManager subjectManager;

    @Override
    protected ServiceCyberViewJpaDao getDao() {
        return dao;
    }

    @Override
    public ServiceCyberView edit(final ServiceCyberView entity) {
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

    public PaginatedResult<ServiceCyberView> findBySearchCriteria(final ServiceCyberViewSearchCriteria criteria) {
        return getDao().findBySearchCriteria(criteria);
    }

    public PaginatedResult<ServiceCyberView> getServiceViewsForLink(ServiceCyberViewSearchCriteria criteria) {
        return getDao().getserviceCyberViewsForLink(criteria);
    }

    public PaginatedResult<ServiceCyberView> getServiceViewsForBundle(ServiceCyberViewSearchCriteria criteria) {
        return getDao().getserviceCyberViewsForBundle(criteria);
    }
}
