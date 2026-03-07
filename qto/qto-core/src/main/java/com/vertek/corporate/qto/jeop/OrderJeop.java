package com.vertek.corporate.qto.jeop;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vertek.corporate.qto.HierarchyLevel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

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
