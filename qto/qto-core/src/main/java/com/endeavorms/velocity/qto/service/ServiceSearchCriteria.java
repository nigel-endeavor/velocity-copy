package com.endeavorms.velocity.qto.service;

import com.endeavorms.velocity.qto.common.BaseSearchCriteria;


public class ServiceSearchCriteria extends BaseSearchCriteria<Service> {

    private Long locationId;

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(final Long locationId) {
        this.locationId = locationId;
    }
}
