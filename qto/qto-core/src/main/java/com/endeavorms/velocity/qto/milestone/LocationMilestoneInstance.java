package com.endeavorms.velocity.qto.milestone;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Represents the relationship from a milestone instance to a location.
 */
@Entity
@Table(name = "location_milestone_instance")
public class LocationMilestoneInstance extends MilestoneInstance {
    /** The related location's ID. */
    @Column(name = "location_id")
    private Long locationId;

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(final Long locationId) {
        this.locationId = locationId;
    }
}
