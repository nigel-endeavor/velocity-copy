package com.endeavorms.velocity.qto.dispute;

import com.endeavorms.velocity.qto.common.BaseSearchCriteria;

public class DisputeSearchCriteria extends BaseSearchCriteria<Dispute> {

    private String search;

    private Long serviceId;

    private Long locationId;

    private boolean disputeOpen;

    public String getSearch() {
        return search;
    }

    public void setSearch(final String search) {
        this.search = search;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(final Long serviceId) {
        this.serviceId = serviceId;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(final Long locationId) {
        this.locationId = locationId;
    }

    public boolean isDisputeOpen() {
        return disputeOpen;
    }

    public void setDisputeOpen(final boolean disputeOpen) {
        this.disputeOpen = disputeOpen;
    }
}
