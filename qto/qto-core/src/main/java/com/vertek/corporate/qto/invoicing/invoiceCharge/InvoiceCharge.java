package com.vertek.corporate.qto.invoicing.invoiceCharge;

import com.vertek.corporate.qto.common.AbstractTenantOwnedEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author mwelicka
 * @since 7/30/2023
 */
@Entity
@Table(name = "invoice_charge")
public class InvoiceCharge extends AbstractTenantOwnedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invoice_charge_id")
    private Long id;

    @Column(name = "invoice_id")
    private Long invoiceId;

    @Column(name = "location_id")
    private Long locationId;

    @Column(name = "charge_credit")
    private String chargeCredit;

    @Column(name = "unit_cost")
    private BigDecimal unitCost;


    @Column(name = "previously_billed")
    private BigDecimal previouslyBilled;

    @Column(name = "invoiced_amount")
    private BigDecimal invoicedAmount;

    @Column(name = "item_desc")
    private String itemDesc;

    @Column(name = "charge_desc")
    private String chargeDesc;

    @Column(name = "charge_type")
    private String chargeType;

    @Column(name = "charge_level")
    private String chargeLevel;

    @Column(name = "master_customer_name")
    private String masterCustomerName;

    @Column(name = "end_customer_name")
    private String endCustomerName;

    @Column(name = "billable_event_milestone_description")
    private String billableEventMilestoneDescription;

    @Column(name = "billable_event_date")
    private Date billableEventDate;

    @Column(name = "milestone_instance_id")
    private Long milestoneInstanceId;

    @Override
    public Long getId() {
        return id;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(final Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(final Long locationId) {
        this.locationId = locationId;
    }

    public String getChargeCredit() {
        return chargeCredit;
    }

    public void setChargeCredit(final String chargeCredit) {
        this.chargeCredit = chargeCredit;
    }

    public BigDecimal getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(final BigDecimal unitCost) {
        this.unitCost = unitCost;
    }

    public BigDecimal getPreviouslyBilled() {
        return previouslyBilled;
    }

    public void setPreviouslyBilled(final BigDecimal previouslyBilled) {
        this.previouslyBilled = previouslyBilled;
    }

    public BigDecimal getInvoicedAmount() {
        return invoicedAmount;
    }

    public void setInvoicedAmount(final BigDecimal invoicedAmount) {
        this.invoicedAmount = invoicedAmount;
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

    public void setChargeDesc(String chargeDesc) {
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

    public String getMasterCustomerName() {
        return masterCustomerName;
    }

    public void setMasterCustomerName(final String masterCustomerName) {
        this.masterCustomerName = masterCustomerName;
    }

    public String getEndCustomerName() {
        return endCustomerName;
    }

    public void setEndCustomerName(final String endCustomerName) {
        this.endCustomerName = endCustomerName;
    }

    public String getBillableEventMilestoneDescription() {
        return billableEventMilestoneDescription;
    }

    public void setBillableEventMilestoneDescription(final String billableEventMilestoneDescription) {
        this.billableEventMilestoneDescription = billableEventMilestoneDescription;
    }

    public Date getBillableEventDate() {
        return billableEventDate;
    }

    public void setBillableEventDate(final Date billableEventDate) {
        this.billableEventDate = billableEventDate;
    }

    public Long getMilestoneInstanceId() {
        return milestoneInstanceId;
    }

    public void setMilestoneInstanceId(final Long milestoneInstanceId) {
        this.milestoneInstanceId = milestoneInstanceId;
    }
}
