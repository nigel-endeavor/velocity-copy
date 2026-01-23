package com.vertek.corporate.qto.invoicing.surcharge.service;

import com.vertek.corporate.qto.common.BaseSearchCriteria;

import javax.ws.rs.QueryParam;

/**
 * Search criteria used for filtering service surcharges.
 * @author fcurran
 * @since 7/13/2023
 */
public class ServiceSurchargeSearchCriteria extends BaseSearchCriteria<ServiceSurcharge> {
    /** The service ID to filter by. */
    @QueryParam("serviceId")
    private Long serviceId;

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(final Long serviceId) {
        this.serviceId = serviceId;
    }
}
