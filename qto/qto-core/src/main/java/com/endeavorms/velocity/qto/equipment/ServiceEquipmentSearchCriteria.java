package com.endeavorms.velocity.qto.equipment;

import com.endeavorms.velocity.qto.common.BaseSearchCriteria;


/**
 * Service Equipment search criteria.
 */
public class ServiceEquipmentSearchCriteria extends BaseSearchCriteria<ServiceEquipment> {
    private Long serviceId;

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(final Long serviceId) {
        this.serviceId = serviceId;
    }
}
