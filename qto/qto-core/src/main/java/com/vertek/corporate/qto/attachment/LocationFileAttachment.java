package com.vertek.corporate.qto.attachment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

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
