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
