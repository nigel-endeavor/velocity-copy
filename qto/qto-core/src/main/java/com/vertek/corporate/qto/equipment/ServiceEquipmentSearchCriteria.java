package com.vertek.corporate.qto.equipment;

import com.vertek.corporate.qto.common.BaseSearchCriteria;

import jakarta.ws.rs.QueryParam;

/**
 * Service Equipment search criteria.
 */
public class ServiceEquipmentSearchCriteria extends BaseSearchCriteria<ServiceEquipment> {
    @QueryParam("serviceId")
    private Long serviceId;

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(final Long serviceId) {
        this.serviceId = serviceId;
    }
}
