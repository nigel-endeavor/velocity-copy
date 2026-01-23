package com.vertek.corporate.qto.interval;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author rcasey
 * @since 3/9/2023
 */
@Entity
@Table(name = "location_interval_instance")
public class LocationIntervalInstance extends IntervalInstance {

    @Column(name = "location_id")
    private Long locationId;

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(final Long locationId) {
        this.locationId = locationId;
    }
}
