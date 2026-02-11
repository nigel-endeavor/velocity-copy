package com.endeavorms.velocity.qto.contact.location;

import com.endeavorms.velocity.qto.contact.Contact;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Models a location contact.
 * @author fcurran
 * @since 3/31/2023
 */
@Entity
@Table(name = "location_contact")
public class LocationContact extends Contact {
    /** The location this contact is associated with. */
    @Column(name = "location_id")
    private Long locationId;

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(final Long locationId) {
        this.locationId = locationId;
    }
}
