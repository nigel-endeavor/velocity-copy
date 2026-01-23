package com.vertek.corporate.qto.equipment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Service Equipment entity.
 * @author fcurran
 * @since 1.15.0
 */
@Entity
@Table(name = "service_equipment")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ServiceEquipment extends Equipment {

    @Column(name = "service_id")
    private Long serviceId;

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(final Long serviceId) {
        this.serviceId = serviceId;
    }
}
