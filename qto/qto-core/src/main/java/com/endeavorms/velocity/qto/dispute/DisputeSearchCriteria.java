package com.endeavorms.velocity.qto.dispute;

import com.endeavorms.velocity.qto.common.BaseSearchCriteria;
import jakarta.ws.rs.QueryParam;

public class DisputeSearchCriteria extends BaseSearchCriteria<Dispute> {

    @QueryParam("search")
    private String search;

    @QueryParam("serviceId")
    private Long serviceId;

    @QueryParam("locationId")
    private Long locationId;

    @QueryParam("disputeOpen")
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
