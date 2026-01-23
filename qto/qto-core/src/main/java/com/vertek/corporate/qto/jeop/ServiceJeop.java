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
