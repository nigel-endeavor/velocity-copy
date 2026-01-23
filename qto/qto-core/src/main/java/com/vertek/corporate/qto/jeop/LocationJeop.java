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
@Table(name = "location_jeop_instance")
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationJeop extends Jeop {

    @Column(name = "location_id")
    private Long locationId;

    @PrePersist
    void prePersist() {
        setLevel(HierarchyLevel.LOCATION.getName());
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(final Long locationId) {
        this.locationId = locationId;
    }
}
