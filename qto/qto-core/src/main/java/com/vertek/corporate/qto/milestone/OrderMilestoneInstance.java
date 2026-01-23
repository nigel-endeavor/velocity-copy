package com.vertek.corporate.qto.milestone;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Represents the relationship from a milestone instance to an order.
 */
@Entity
@Table(name = "order_milestone_instance")
public class OrderMilestoneInstance extends MilestoneInstance {
    /** The related order's ID. */
    @Column(name = "order_id")
    private Long orderId;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(final Long orderId) {
        this.orderId = orderId;
    }
}
