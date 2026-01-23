package com.vertek.corporate.qto.service.inventoryview;

import com.google.common.base.Strings;
import com.vertek.corporate.qto.common.PaginatedResult;
import com.vertek.corporate.qto.common.StandardManager;
import com.vertek.corporate.qto.location.inventoryview.InventoryWorklistMeta;
import com.vertek.corporate.qto.order.Order;
import com.vertek.corporate.qto.order.OrderManager;
import com.vertek.corporate.qto.subject.Subject;
import com.vertek.corporate.qto.subject.SubjectManager;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

/**
 * @author rcasey
 * @since 2/23/2023
 */
@Stateless
public class ServiceInventoryViewManager extends StandardManager<ServiceInventoryView> {

    /**
     * Persistence tier for ServiceView.
     */
    @Inject
    private ServiceInventoryViewJpaDao dao;

    @Inject
    private OrderManager orderManager;

    @Inject
    private SubjectManager subjectManager;

    @Override
    protected ServiceInventoryViewJpaDao getDao() {
        return dao;
    }

    @Override
    public ServiceInventoryView edit(final ServiceInventoryView entity) {
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

    public PaginatedResult<ServiceInventoryView> findInventoryBySearchCriteria(final ServiceInventoryViewSearchCriteria criteria) {
        return getDao().findInventoryBySearchCriteria(criteria);
    }

    public PaginatedResult<ServiceInventoryView> findBySearchCriteriaForLink(final ServiceInventoryViewSearchCriteria criteria) {
        return getDao().findBySearchCriteriaForLink(criteria);
    }

    public InventoryWorklistMeta getInventoryWorklistMeta(final ServiceInventoryViewSearchCriteria criteria) {
        return getDao().getInventoryWorklistMeta(criteria);
    }

    public PaginatedResult<ServiceInventoryView> getServiceInventoryViewsForLink(ServiceInventoryViewSearchCriteria criteria) {
        return getDao().getServiceInventoryViewsForLink(criteria);
    }

    public PaginatedResult<ServiceInventoryView> getServiceInventoryViewsForBundle(ServiceInventoryViewSearchCriteria criteria) {
        return getDao().getServiceInventoryViewsForBundle(criteria);
    }

    public List<String> findServiceTypes(){
        return getDao().findServiceTypes();
    }
}
