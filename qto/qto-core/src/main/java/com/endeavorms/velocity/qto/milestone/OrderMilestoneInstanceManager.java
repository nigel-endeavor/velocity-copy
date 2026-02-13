package com.endeavorms.velocity.qto.milestone;

import com.endeavorms.velocity.qto.events.handlers.BaseEventHandler;
import com.endeavorms.velocity.qto.order.Order;
import com.endeavorms.velocity.qto.order.OrderManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import java.util.List;

/**
 * @author fcurran
 */
@Component
public class OrderMilestoneInstanceManager extends AbstractMilestoneInstanceManager<OrderMilestoneInstance> {

    /** Logging. */
    protected static final Logger LOGGER = LoggerFactory.getLogger(OrderMilestoneInstanceManager.class);


    /** Data access for Order Milestone instances. */
    @Inject
    private OrderMilestoneInstanceJpaDao dao;

    /** Business methods for Milestones.*/
    @Inject
    private MilestoneManager milestoneManager;

    /** Business methods for Orders.*/
    @Inject
    private OrderManager orderManager;

    @Inject
    private BaseEventHandler baseEventHandler;

    @Override
    public OrderMilestoneInstanceJpaDao getDao() {
        return dao;
    }

    @Override
    protected MilestoneManager getMilestoneManager() {
        return milestoneManager;
    }

    @Override
    public OrderMilestoneInstance create(final OrderMilestoneInstance entity) {
        Order order = orderManager.retrieve(entity.getOrderId());
        entity.setTenantId(order.getTenantId());
        entity.setMasterCustomerId(order.getMasterCustomerId());
        return super.create(entity);
    }

    @Override
    public OrderMilestoneInstance edit(final OrderMilestoneInstance entity) {
        Order order = orderManager.retrieve(entity.getOrderId());
        entity.setTenantId(order.getTenantId());
        entity.setMasterCustomerId(order.getMasterCustomerId());
        return super.edit(entity);
    }

    @Override
    protected void handleMilestoneEvent(final OrderMilestoneInstance milestoneInstance) {
        Order order = orderManager.retrieve(milestoneInstance.getOrderId());

        baseEventHandler.handleMilestoneEvent(milestoneInstance, order);
    }

    @Override
    protected OrderMilestoneInstance newMilestoneInstance(final Long orderId) {
        Order order = orderManager.retrieve(orderId);
        OrderMilestoneInstance orderMilestoneInstance = new OrderMilestoneInstance();
        orderMilestoneInstance.setOrderId(orderId);
        orderMilestoneInstance.setTenantId(order.getTenantId());
        orderMilestoneInstance.setMasterCustomerId(order.getMasterCustomerId());
        return orderMilestoneInstance;
    }

    @Override
    protected void setRecordId(final OrderMilestoneInstance orderMilestoneInstance, final Long orderId) {
        orderMilestoneInstance.setOrderId(orderId);
    }

        /**
     * Checks if an instance exists for the given location and milestone code.
     * @param orderId order id.
     * @param milestoneCode milestone code.
     * @return True if an instance already exists, false otherwise.
     */
    public boolean doesMilestoneExist(final Long orderId, final String milestoneCode) {
        return dao.doesMilestoneExist(orderId, milestoneCode);
    }

            /**
     * Retrieves current milestone by name.
     * @param id service id.
     * @param milestoneCode milestone code.
     * @return current order milestone instance.
     */
    public OrderMilestoneInstance retrieveCurrentMilestoneByCode(final Long id, final String milestoneCode) {
        return dao.retrieveCurrentMilestoneByCode(id, milestoneCode);
    }

        /**
     * Retrieves current milestone by Order ID.
     * @param orderId invoice id.
     * @return  milestone instances.
     */
    public List<OrderMilestoneInstance> findByOrderId(final Long orderId) {
        return dao.findByOrderId(orderId);
    }
}
