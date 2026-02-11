package com.endeavorms.velocity.qto.subject;

import com.endeavorms.velocity.qto.common.BaseSearchCriteria;

import jakarta.ws.rs.QueryParam;

/**
 * Search criteria for subjects.
 */
public class SubjectSearchCriteria extends BaseSearchCriteria<Subject> {
    /** Order ID, used to determine tenant when provided. */
    @QueryParam("orderId")
    private Long orderId;

    /** The name of the subject to filter by. */
    @QueryParam("name")
    private String name;

    @QueryParam("isInventoryWrite")
    private Boolean isInventoryWrite;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(final Long orderId) {
        this.orderId = orderId;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Boolean getIsInventoryWrite() {
        return isInventoryWrite;
    }

    public void setIsInventoryWrite(final Boolean isInventoryWrite) {
        this.isInventoryWrite = isInventoryWrite;
    }
}
