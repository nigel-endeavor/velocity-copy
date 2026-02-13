package com.endeavorms.velocity.qto.costhistory;

import com.endeavorms.velocity.qto.common.BaseSearchCriteria;

import java.util.List;

/**
 * @author rcasey
 * @since 11/1/2023
 */
public class CostHistorySearchCriteria extends BaseSearchCriteria<CostHistory> {

    private Long serviceId;

    private Long locationId;

    private List<String> costType;

    private List<Long> serviceIds;

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

    public List<Long> getServiceIds() {
        return serviceIds;
    }

    public void setServiceIds(final List<Long> serviceIds) {
        this.serviceIds = serviceIds;
    }

    public List<String> getCostType() {
        return costType;
    }

    public void setCostType(final List<String> costType) {
        this.costType = costType;
    }
}
