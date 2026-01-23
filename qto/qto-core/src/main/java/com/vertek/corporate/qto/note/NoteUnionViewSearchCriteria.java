package com.vertek.corporate.qto.note;

import com.vertek.corporate.qto.common.BaseSearchCriteria;

import javax.ws.rs.QueryParam;

public class NoteUnionViewSearchCriteria extends BaseSearchCriteria<NoteUnionView> {

    @QueryParam("locationId")
    private Long locationId;

    @QueryParam("serviceId")
    private Long serviceId;

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(final Long locationId) {
        this.locationId = locationId;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(final Long serviceId) {
        this.serviceId = serviceId;
    }
}
