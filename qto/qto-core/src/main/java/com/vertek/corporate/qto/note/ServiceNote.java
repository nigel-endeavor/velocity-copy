package com.vertek.corporate.qto.note;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

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
