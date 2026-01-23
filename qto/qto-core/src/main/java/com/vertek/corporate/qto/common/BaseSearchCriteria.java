package com.vertek.corporate.qto.common;

import com.querydsl.core.types.Order;

import java.io.Serializable;
import javax.ws.rs.QueryParam;

/**
 * from vertek-commons.
 * @param <T>
 */
public abstract class BaseSearchCriteria<T extends BaseEntity<? extends Serializable>> extends PaginatedResult<T> {
    @QueryParam("sortField")
    protected String sortField;
    @QueryParam("sortDir")
    protected Order sortDirection;
    @QueryParam("format")
    private String format;
    @QueryParam("fields")
    private String fields;
    @QueryParam("headers")
    private String headers;

    public BaseSearchCriteria() {
        this.sortDirection = Order.ASC;
    }

    public String getSortField() {
        return this.sortField;
    }

    public void setSortField(String var1) {
        this.sortField = var1;
    }

    public Order getSortDirection() {
        return this.sortDirection;
    }

    public void setSortDirection(Order var1) {
        this.sortDirection = var1;
    }

    public String getFormat() {
        return this.format;
    }

    public void setFormat(String var1) {
        this.format = var1;
    }

    public String getFields() {
        return this.fields;
    }

    public void setFields(String var1) {
        this.fields = var1;
    }

    public String getHeaders() {
        return this.headers;
    }

    public void setHeaders(String var1) {
        this.headers = var1;
    }
}
