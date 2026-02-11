package com.endeavorms.velocity.qto.note;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * @author llevit
 * @since 1/16/2023
 */
@Entity
@Table(name = "location_note")
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationNote extends Note {

    @Column(name = "location_id")
    private Long locationId;

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(final Long locationId) {
        this.locationId = locationId;
    }
}
