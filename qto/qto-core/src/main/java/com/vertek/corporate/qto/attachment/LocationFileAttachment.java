package com.vertek.corporate.qto.attachment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * @author rcasey
 * @since 1/24/2023
 */
@Entity
@Table(name = "location_file_attachment")
public class LocationFileAttachment extends FileAttachment {

    @Column(name = "location_id")
    private Long locationId;

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(final Long locationId) {
        this.locationId = locationId;
    }
}
