package com.vertek.corporate.qto.note;

import com.vertek.corporate.qto.common.BaseSearchCriteria;

import jakarta.ws.rs.QueryParam;

public class OrderNoteSearchCriteria extends BaseSearchCriteria<OrderNote> {

    @QueryParam("orderId")
    private Long orderId;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(final Long orderId) {
        this.orderId = orderId;
    }
}
