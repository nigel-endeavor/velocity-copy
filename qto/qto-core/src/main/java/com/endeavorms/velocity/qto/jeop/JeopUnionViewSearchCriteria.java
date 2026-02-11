package com.endeavorms.velocity.qto.jeop;

import com.endeavorms.velocity.qto.common.BaseSearchCriteria;

import jakarta.ws.rs.QueryParam;

public class JeopUnionViewSearchCriteria extends BaseSearchCriteria<JeopUnionView> {

    @QueryParam("orderId")
    private Long orderId;

    @QueryParam("locationId")
    private Long locationId;

    @QueryParam("serviceId")
    private Long serviceId;

    @QueryParam("isOpen")
    private Boolean isOpen;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(final Long orderId) {
        this.orderId = orderId;
    }

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

    public Boolean isOpen() {
        return isOpen;
    }

    public void setOpen(final Boolean open) {
        isOpen = open;
    }
}
