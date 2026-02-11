package com.endeavorms.velocity.qto.jeop;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.endeavorms.velocity.qto.HierarchyLevel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

/**
 * @author llevit
 */
@Entity
@Table(name = "service_jeop_instance")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ServiceJeop extends Jeop {

    @Column(name = "service_id")
    private Long serviceId;

    @PrePersist
    void prePersist() {
        setLevel(HierarchyLevel.SERVICE.getName());
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(final Long serviceId) {
        this.serviceId = serviceId;
    }
}
