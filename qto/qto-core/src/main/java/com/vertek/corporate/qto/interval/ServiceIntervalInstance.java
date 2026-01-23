package com.vertek.corporate.qto.interval;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author rcasey
 * @since 3/9/2023
 */
@Entity
@Table(name = "service_interval_instance")
public class ServiceIntervalInstance extends IntervalInstance {

    @Column(name = "service_id")
    private Long serviceId;

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(final Long serviceId) {
        this.serviceId = serviceId;
    }
}
