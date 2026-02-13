package com.endeavorms.velocity.qto.note;

import com.endeavorms.velocity.qto.common.BaseSearchCriteria;


public class NoteUnionViewSearchCriteria extends BaseSearchCriteria<NoteUnionView> {

    private Long locationId;

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
