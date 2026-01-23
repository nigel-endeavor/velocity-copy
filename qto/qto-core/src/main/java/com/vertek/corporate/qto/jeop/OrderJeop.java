package com.vertek.corporate.qto.jeop;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vertek.corporate.qto.HierarchyLevel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.Table;

/**
 * @author llevit
 */
@Entity
@Table(name = "order_jeop_instance")
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderJeop extends Jeop {

    @Column(name = "order_id")
    private Long orderId;

    @PrePersist
    void prePersist() {
        setLevel(HierarchyLevel.ORDER.getName());
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(final Long orderId) {
        this.orderId = orderId;
    }
}
