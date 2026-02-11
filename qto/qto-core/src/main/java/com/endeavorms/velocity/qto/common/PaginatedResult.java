package com.endeavorms.velocity.qto.common;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;
import com.querydsl.core.QueryResults;

import java.util.List;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.QueryParam;

@JacksonXmlRootElement
public class PaginatedResult<T extends BaseEntity> {
    private static final int DEFAULT_PAGE_SIZE = 25;
    /** @deprecated */
    @Deprecated
    private int page = 0;
    @QueryParam("offset")
    @DefaultValue("0")
    private int offset = 0;
    @QueryParam("limit")
    @DefaultValue("25")
    private int limit = 25;
    private int total = 0;
    @JacksonXmlElementWrapper(
            localName = "items"
    )
    @JacksonXmlProperty(
            localName = "item"
    )
    private List<T> collection = Lists.newArrayList();

    public PaginatedResult() {
    }

    public PaginatedResult(PaginatedResult<?> var1, List<T> var2) {
        this.setLimit(var1.getLimit());
        this.setOffset(var1.getOffset());
        this.setPage(var1.getPage());
        this.setTotal(var1.getTotal());
        this.collection = var2;
    }

    public PaginatedResult(QueryResults<T> var1) {
        int var2 = (int)var1.getLimit();
        this.limit = var2 == -1 ? 25 : var2;
        this.offset = (int)var1.getOffset();
        this.total = (int)var1.getTotal();
        this.collection = var1.getResults();
    }

    /** @deprecated */
    @Deprecated
    public int getPage() {
        return this.page;
    }

    /** @deprecated */
    @Deprecated
    public void setPage(int var1) {
        this.page = var1;
    }

    public int getLimit() {
        return this.limit;
    }

    public void setLimit(int var1) {
        this.limit = var1;
    }

    public int getOffset() {
        return this.offset;
    }

    public void setOffset(int var1) {
        this.offset = var1;
    }

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int var1) {
        this.total = var1;
    }

    public List<T> getCollection() {
        return this.collection;
    }

    public void setCollection(List<T> var1) {
        this.collection = var1;
    }

    public String toString() {
        return MoreObjects.toStringHelper(this.getClass()).add("offset", this.offset).add("limit", this.limit).add("page", this.page).add("total", this.total).add("collection", this.collection).toString();
    }
}
