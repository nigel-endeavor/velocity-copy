package com.vertek.corporate.qto.invoicing.invoice;

import com.vertek.corporate.qto.common.BaseSearchCriteria;
import com.vertek.corporate.qto.common.DateRangeType;

import jakarta.ws.rs.QueryParam;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InvoiceSearchCriteria extends BaseSearchCriteria<Invoice> {

    @QueryParam("search")
    private String search;

    @QueryParam("tenantName")
    private List<String> tenantNames = new ArrayList<>();

    @QueryParam("invoiceId")
    private Long invoiceId;

    @QueryParam("invoiceNumber")
    private String invoiceNumber;

    @QueryParam("invoiceStatus")
    private String invoiceStatus;

    @QueryParam("invoiceStartDate")
    private List<Date> invoiceStartDate;

    @QueryParam("invoiceStartDate-comparison")
    private List<DateRangeType> invoiceStartDateTimeRange;

    @QueryParam("invoiceEndDate")
    private List<Date> invoiceEndDate;

    @QueryParam("invoiceEndDate-comparison")
    private List<DateRangeType> invoiceEndDateTimeRange;

    @QueryParam("generatedBy")
    private String generatedBy;

    @QueryParam("generatedDate")
    private List<Date> generatedDate;

    @QueryParam("generatedDate-comparison")
    private List<DateRangeType> generatedDateTimeRange;

    public String getSearch() {
        return search;
    }

    public void setSearch(final String search) {
        this.search = search;
    }

    public List<String> getTenantNames() {
        return tenantNames;
    }

    public void setTenantNames(final List<String> tenantNames) {
        this.tenantNames = tenantNames;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(final Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(final String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(final String invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    public List<Date> getInvoiceStartDate() {
        return invoiceStartDate;
    }

    public void setInvoiceStartDate(final List<Date> invoiceStartDate) {
        this.invoiceStartDate = invoiceStartDate;
    }

    public List<DateRangeType> getInvoiceStartDateTimeRange() {
        return invoiceStartDateTimeRange;
    }

    public void setInvoiceStartDateTimeRange(final List<DateRangeType> invoiceStartDateTimeRange) {
        this.invoiceStartDateTimeRange = invoiceStartDateTimeRange;
    }

    public List<Date> getInvoiceEndDate() {
        return invoiceEndDate;
    }

    public void setInvoiceEndDate(final List<Date> invoiceEndDate) {
        this.invoiceEndDate = invoiceEndDate;
    }

    public List<DateRangeType> getInvoiceEndDateTimeRange() {
        return invoiceEndDateTimeRange;
    }

    public void setInvoiceEndDateTimeRange(final List<DateRangeType> invoiceEndDateTimeRange) {
        this.invoiceEndDateTimeRange = invoiceEndDateTimeRange;
    }

    public String getGeneratedBy() {
        return generatedBy;
    }

    public void setGeneratedBy(final String generatedBy) {
        this.generatedBy = generatedBy;
    }

    public List<Date> getGeneratedDate() {
        return generatedDate;
    }

    public void setGeneratedDate(final List<Date> generatedDate) {
        this.generatedDate = generatedDate;
    }

    public List<DateRangeType> getGeneratedDateTimeRange() {
        return generatedDateTimeRange;
    }

    public void setGeneratedDateTimeRange(final List<DateRangeType> generatedDateTimeRange) {
        this.generatedDateTimeRange = generatedDateTimeRange;
    }
}
