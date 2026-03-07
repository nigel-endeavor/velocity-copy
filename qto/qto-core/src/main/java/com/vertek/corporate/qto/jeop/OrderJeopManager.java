package com.vertek.corporate.qto.jeop;

import com.vertek.corporate.qto.common.BussinessDaysUtils;
import com.vertek.corporate.qto.common.PaginatedResult;
import com.vertek.corporate.qto.common.StandardManager;
import com.vertek.corporate.qto.order.Order;
import com.vertek.corporate.qto.order.OrderManager;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.List;

/**
 * @author llevit
 */
@Stateless
public class OrderJeopManager extends StandardManager<OrderJeop> {

    /**
     * Persistence tier for OrderJeop.
     */
    @Inject
    private OrderJeopJpaDao dao;

    @Inject
    private JeopUnionViewJpaDao jeopUnionViewJpaDao;

    @Inject
    private OrderManager orderManager;

    @Override
    protected OrderJeopJpaDao getDao() {
        return dao;
    }

    @Override
    public OrderJeop create(final OrderJeop jeop) {
        Order order = orderManager.retrieve(jeop.getOrderId());
        jeop.setTenantId(order.getTenantId());
        jeop.setMasterCustomerId(order.getMasterCustomerId());
        if (jeop.getEndDate() != null) {
            jeop.setBusinessDaysOpen(BussinessDaysUtils.getBusinessDaysCount(jeop.getStartDate(), jeop.getEndDate()));
            jeop.setCalendarDaysOpen(BussinessDaysUtils.getCalendarDaysCount(jeop.getStartDate(), jeop.getEndDate()));
        }
        return this.getDao().create(jeop);
    }

    @Override
    public OrderJeop edit(final OrderJeop jeop) {
        Order order = orderManager.retrieve(jeop.getOrderId());
        jeop.setTenantId(order.getTenantId());
        jeop.setMasterCustomerId(order.getMasterCustomerId());
        if (jeop.getEndDate() != null) {
            jeop.setBusinessDaysOpen(BussinessDaysUtils.getBusinessDaysCount(jeop.getStartDate(), jeop.getEndDate()));
            jeop.setCalendarDaysOpen(BussinessDaysUtils.getCalendarDaysCount(jeop.getStartDate(), jeop.getEndDate()));
        }
        if (jeop.getVersion() == null) {
            OrderJeop realJeop = this.getDao().retrieve(jeop.getId());
            if (realJeop.getVersion() != jeop.getViewVersion()) {
                throw new IllegalArgumentException("This Jeop has been modified by another user. Please refresh and try again.");
            }
            realJeop.setDescription(jeop.getDescription());
            realJeop.setLevel(jeop.getLevel());
            realJeop.setStartDate(jeop.getStartDate());
            realJeop.setEndDate(jeop.getEndDate());
            realJeop.setNote(jeop.getNote());
            realJeop.setResponsibility(jeop.getResponsibility());
            realJeop.setOriginator(jeop.getOriginator());
            realJeop.setBusinessDaysOpen(jeop.getBusinessDaysOpen());
            realJeop.setCalendarDaysOpen(jeop.getCalendarDaysOpen());
            realJeop.setAssignedTo(jeop.getAssignedTo());
            realJeop.setBusinessDaysOpen(jeop.getBusinessDaysOpen());
            return this.getDao().edit(realJeop);
        } else {
            return this.getDao().edit(jeop);
        }
    }

    public PaginatedResult<JeopUnionView> findBySearchCriteria(final JeopUnionViewSearchCriteria criteria) {
        return jeopUnionViewJpaDao.findBySearchCriteria(criteria);
    }

        /**
     * Gets list of all open jeops for an Order.
     *
     * @param orderId of the Order.
     * @return List of open jeops.
     */
    public List<OrderJeop> getOpen(final Long orderId) {
        return dao.getOpen(orderId);
    }


        /**
     * Gets list of all jeops for an Order.
     *
     * @param orderId of the Order.
     * @return List of open jeops.
     */
    public List<OrderJeop> findByOrderId(final Long orderId) {
        return dao.findByOrderId(orderId);
    }
}
