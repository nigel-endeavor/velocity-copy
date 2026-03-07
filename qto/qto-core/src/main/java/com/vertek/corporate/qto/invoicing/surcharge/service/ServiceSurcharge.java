package com.vertek.corporate.qto.invoicing.surcharge.service;

import com.vertek.corporate.qto.invoicing.surcharge.Surcharge;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Models a service surcharge.
 * @author fcurran
 * @since 7/13/2023
 */
@Entity
@Table(name = "service_surcharge")
public class ServiceSurcharge extends Surcharge {
    /** The service this surcharge is associated with. */
    @Column(name = "service_id")
    private Long serviceId;

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(final Long serviceId) {
        this.serviceId = serviceId;
    }
}
