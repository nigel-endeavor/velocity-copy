package com.endeavorms.velocity.qto.order.view;

import com.endeavorms.velocity.qto.common.BaseSearchCriteria;

import java.util.List;

public class OrderViewSearchCriteria extends BaseSearchCriteria<OrderView> {

    private String search;

    private List<String> status;

    public String getSearch() {
        return search;
    }

    public void setSearch(final String search) {
        this.search = search;
    }

    public List<String> getStatus() {
        return status;
    }

    public void setStatus(final List<String> status) {
        this.status = status;
    }
}
