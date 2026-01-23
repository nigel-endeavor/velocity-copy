package com.vertek.corporate.qto.disconnect;

import com.google.common.base.Strings;
import com.vertek.corporate.qto.common.PaginatedResult;
import com.vertek.corporate.qto.common.StandardManager;
import com.vertek.corporate.qto.order.Order;
import com.vertek.corporate.qto.order.OrderManager;
import com.vertek.corporate.qto.subject.Subject;
import com.vertek.corporate.qto.subject.SubjectManager;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

/**
 * @author fcurran
 * @since 1.3.0
 */
@Stateless
public class DisconnectViewManager extends StandardManager<DisconnectView> {

    /**
     * Persistence tier for DisconnectViews.
     */
    @Inject
    private DisconnectViewJpaDao dao;

    @Inject
    private OrderManager orderManager;

    @Inject
    private SubjectManager subjectManager;

    @Override
    protected DisconnectViewJpaDao getDao() {
        return dao;
    }

    public PaginatedResult<DisconnectView> findBySearchCriteria(final DisconnectViewSearchCriteria criteria) {
        return getDao().findBySearchCriteria(criteria);
    }

    public DisconnectWorklistMeta getWorklistMeta(final DisconnectViewSearchCriteria criteria) {
        return getDao().getWorklistMeta(criteria);
    }

    @Override
    public DisconnectView edit(final DisconnectView entity) {
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

    public List<String> findServiceTypes(){
        return getDao().findServiceTypes();
    }
}
