package com.vertek.corporate.qto.milestone;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Represents the relationship from a milestone instance to a service.
 */
@Entity
@Table(name = "service_milestone_instance")
public class ServiceMilestoneInstance extends MilestoneInstance {
    /** The related service's ID. */
    @Column(name = "service_id")
    private Long serviceId;

    @Transient
    private Boolean useExistingInventoryLocationAddress;

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(final Long serviceId) {
        this.serviceId = serviceId;
    }

    public Boolean getUseExistingInventoryLocationAddress() {
        return useExistingInventoryLocationAddress;
    }

    public void setUseExistingInventoryLocationAddress(final Boolean useExistingInventoryLocationAddress) {
        this.useExistingInventoryLocationAddress = useExistingInventoryLocationAddress;
    }
}
