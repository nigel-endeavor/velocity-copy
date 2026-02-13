package com.endeavorms.velocity.qto.invoicing.invoiceCharge;

import com.endeavorms.velocity.qto.common.BaseSearchCriteria;
import com.endeavorms.velocity.qto.common.DateRangeType;

import java.util.Date;
import java.util.List;

public class InvoiceChargeSearchCriteria extends BaseSearchCriteria<InvoiceCharge> {
    private String search;

    private Long invoiceId;

    private String itemDesc;

    private String chargeDesc;

    private String chargeType;

    private String chargeLevel;

    private String masterCustomer;

    private String endCustomer;

    private String billableEvent;

    private List<Date> billableEventDate;

    private List<DateRangeType> billableEventDateRange;

    public String getSearch() {
        return search;
    }

    public void setSearch(final String search) {
        this.search = search;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(final Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(final String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public String getChargeDesc() {
        return chargeDesc;
    }

    public void setChargeDesc(final String chargeDesc) {
        this.chargeDesc = chargeDesc;
    }

    public String getChargeType() {
        return chargeType;
    }

    public void setChargeType(final String chargeType) {
        this.chargeType = chargeType;
    }

    public String getChargeLevel() {
        return chargeLevel;
    }

    public void setChargeLevel(final String chargeLevel) {
        this.chargeLevel = chargeLevel;
    }

    public String getMasterCustomer() {
        return masterCustomer;
    }

    public void setMasterCustomer(final String masterCustomer) {
        this.masterCustomer = masterCustomer;
    }

    public String getEndCustomer() {
        return endCustomer;
    }

    public void setEndCustomer(final String endCustomer) {
        this.endCustomer = endCustomer;
    }

    public String getBillableEvent() {
        return billableEvent;
    }

    public void setBillableEvent(final String billableEvent) {
        this.billableEvent = billableEvent;
    }

    public List<Date> getBillableEventDate() {
        return billableEventDate;
    }

    public void setBillableEventDate(final List<Date> billableEventDate) {
        this.billableEventDate = billableEventDate;
    }

    public List<DateRangeType> getBillableEventDateRange() {
        return billableEventDateRange;
    }

    public void setBillableEventDateRange(final List<DateRangeType> billableEventDateRange) {
        this.billableEventDateRange = billableEventDateRange;
    }
}
