package com.endeavorms.velocity.qto.note;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * @author llevit
 * @since 1/16/2023
 */
@Entity
@Table(name = "order_note")
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderNote extends Note {

    @Column(name = "order_id")
    private Long orderId;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(final Long orderId) {
        this.orderId = orderId;
    }
}
