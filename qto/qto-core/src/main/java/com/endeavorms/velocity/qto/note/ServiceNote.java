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
@Table(name = "service_note")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ServiceNote extends Note {

    @Column(name = "service_id")
    private Long serviceId;

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(final Long serviceId) {
        this.serviceId = serviceId;
    }
}
