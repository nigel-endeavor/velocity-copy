package com.vertek.corporate.qto.note;

import com.vertek.corporate.qto.common.PaginatedResult;
import com.vertek.corporate.qto.common.StandardManager;
import com.vertek.corporate.qto.order.Order;
import com.vertek.corporate.qto.order.OrderManager;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.List;

/**
 * @author llevit
 * @since 1/16/2023
 */
@Stateless
public class OrderNoteManager extends StandardManager<OrderNote> {

    /**
     * Persistence tier for OrderNote.
     */
    @Inject
    private OrderNoteJpaDao dao;

    @Inject
    private OrderManager orderManager;

    @Override
    protected OrderNoteJpaDao getDao() {
        return dao;
    }

    public PaginatedResult<OrderNote> findBySearchCriteria(final OrderNoteSearchCriteria criteria) {
        return getDao().findBySearchCriteria(criteria);
    }

    @Override
    public OrderNote create(final OrderNote entity) {
        Order order =  orderManager.retrieve(entity.getOrderId());
        entity.setTenantId(order.getTenantId());
        entity.setMasterCustomerId(order.getMasterCustomerId());
        return super.create(entity);
    }

    @Override
    public OrderNote edit(final OrderNote entity) {
        Order order =  orderManager.retrieve(entity.getOrderId());
        entity.setTenantId(order.getTenantId());
        entity.setMasterCustomerId(order.getMasterCustomerId());
        return super.edit(entity);
    }

            /**
     * Find all notes for a order.
     *
     * @param orderId the order id
     * @return the list of notes
     */
    public List<OrderNote> findByOrderId(Long orderId) {
        return getDao().findByOrderId(orderId);
    }
}
