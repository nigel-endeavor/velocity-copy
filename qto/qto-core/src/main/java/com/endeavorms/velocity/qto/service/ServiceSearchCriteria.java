package com.endeavorms.velocity.qto.service;

import com.endeavorms.velocity.qto.common.BaseSearchCriteria;

import jakarta.ws.rs.QueryParam;

public class ServiceSearchCriteria extends BaseSearchCriteria<Service> {

    @QueryParam("locationId")
    private Long locationId;

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(final Long locationId) {
        this.locationId = locationId;
    }
}
