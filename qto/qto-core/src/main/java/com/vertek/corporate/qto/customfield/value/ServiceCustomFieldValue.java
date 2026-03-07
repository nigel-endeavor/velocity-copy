package com.vertek.corporate.qto.customfield.value;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "service_custom_field_value")
public class ServiceCustomFieldValue extends CustomFieldValue {

    @Column(name = "service_id")
    private Long serviceId;

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(final Long serviceId) {
        this.serviceId = serviceId;
    }
}
