package com.endeavorms.velocity.qto.service.cyberView;

import com.google.common.base.Strings;
import com.endeavorms.velocity.qto.common.PaginatedResult;
import com.endeavorms.velocity.qto.common.StandardManager;
import com.endeavorms.velocity.qto.order.Order;
import com.endeavorms.velocity.qto.order.OrderManager;
import com.endeavorms.velocity.qto.service.Service;
import com.endeavorms.velocity.qto.service.ServiceManager;
import com.endeavorms.velocity.qto.subject.Subject;
import com.endeavorms.velocity.qto.subject.SubjectManager;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;

@Component
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
