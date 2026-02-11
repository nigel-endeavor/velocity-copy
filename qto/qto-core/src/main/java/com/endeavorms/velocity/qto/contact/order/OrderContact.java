package com.endeavorms.velocity.qto.contact.order;

import com.endeavorms.velocity.qto.contact.Contact;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Models an order contact.
 * @author fcurran
 * @since 1/26/2023
 */
@Entity
@Table(name = "order_contact")
public class OrderContact extends Contact {
    /** The order this contact is associated with. */
    @Column(name = "order_id")
    private Long orderId;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(final Long orderId) {
        this.orderId = orderId;
    }
}
