package com.vertek.corporate.qto.dispute;

import com.vertek.corporate.qto.common.AbstractMasterCustomerOwnedEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author fcurran
 * @since 9/18/2023
 */
@Entity
@Table(name = "v_manage_disputes")
public class DisputeView extends AbstractMasterCustomerOwnedEntity {
    
    @Id
    @Column(name = "dispute_id")
    private Long id;

    @Column(name = "service_id")
    private Long serviceId;

    @Column(name = "location_id")
    private Long locationId;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "parent_company_name")
    private String parentCompanyName;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "parent_company_client_id")
    private String parentCompanyClientId;

    @Column(name = "end_customer_client_id")
    private String endCustomerClientId;

    @Column (name = "address")
    private String address;

    @Column(name = "address_1")
    private String address1;

    @Column(name = "address_2")
    private String address2;

    @Column(name = "city")
    private String city;

    @Column(name = "state_province")
    private String stateProvince;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "service_type")
    private String serviceType;

    @Column(name = "dispute_status")
    private String disputeStatus;

    @Column(name = "dispute_type")
    private String disputeType;

    @Column(name = "provider")
    private String provider;

    @Column(name = "amount_disputed_mrc")
    private BigDecimal amountDisputedMrc;

    @Column(name = "amount_disputed_nrc")
    private BigDecimal amountDisputedNrc;

    @Column(name = "provider_circuit_id")
    private String providerCircuitId;

    @Column(name = "summary_bill")
    private String summaryBill;

    @Column(name = "service_billed_to")
    private String serviceBilledTo;

    @Column(name = "open_date")
    private Date openDate;

    @Column(name = "dispute_follow_up_date")
    private Date disputeFollowUpDate;

    @Column(name = "credit_recognized")
    private String creditRecognized;

    @Column(name = "billing_review_complete_date")
    private Date billingReviewCompleteDate;

    @Column(name = "dispute_closed_date")
    private Date disputeClosedDate;

    @Column(name = "invoice_num")
    private String invoiceNum;

    @Column(name = "vendor_tracking_num")
    private String vendorTrackingNum;

    @Column(name = "service_mrc")
    private BigDecimal serviceMrc;

    @Column(name = "service_nrc")
    private BigDecimal serviceNrc;

    @Column(name = "speed")
    private String speed;

    @Column(name = "has_icb")
    private boolean hasIcb;

    @Column(name = "active")
    private boolean serviceActive;

    @Column(name = "latest_note")
    private String latestNote;

    @Column(name = "realized_credit")
    private BigDecimal realizedCredit;

    @Column(name = "realized_mrc_adjustment")
    private BigDecimal realizedMrcAdjustment;

    @Column(name = "annualized_mrc_save")
    private BigDecimal annualizedMrcSave;

    @Column(name = "show_dispute_follow_up_icon")
    private boolean showDisputeFollowUpIcon;

    @Column(name = "dispute_assignment")
    private String disputeAssignment;

    @Column(name = "client_location_id")
    private String clientLocationId;

    @Column(name = "client_service_id")
    private String clientServiceId;

    @Override
    public Long getId() {
        return id;
    }

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

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(final Long orderId) {
        this.orderId = orderId;
    }

    public String getParentCompanyName() {
        return parentCompanyName;
    }

    public void setParentCompanyName(final String parentCompanyName) {
        this.parentCompanyName = parentCompanyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(final String companyName) {
        this.companyName = companyName;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(final Long companyId) {
        this.companyId = companyId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(final String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(final String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    public String getStateProvince() {
        return stateProvince;
    }

    public void setStateProvince(final String stateProvince) {
        this.stateProvince = stateProvince;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(final String postalCode) {
        this.postalCode = postalCode;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(final String serviceType) {
        this.serviceType = serviceType;
    }

    public String getDisputeStatus() {
        return disputeStatus;
    }

    public void setDisputeStatus(final String disputeStatus) {
        this.disputeStatus = disputeStatus;
    }

    public String getDisputeType() {
        return disputeType;
    }

    public void setDisputeType(final String disputeType) {
        this.disputeType = disputeType;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(final String provider) {
        this.provider = provider;
    }

    public BigDecimal getAmountDisputedMrc() {
        return amountDisputedMrc;
    }

    public void setAmountDisputedMrc(final BigDecimal amountDisputedMrc) {
        this.amountDisputedMrc = amountDisputedMrc;
    }

    public BigDecimal getAmountDisputedNrc() {
        return amountDisputedNrc;
    }

    public void setAmountDisputedNrc(final BigDecimal amountDisputedNrc) {
        this.amountDisputedNrc = amountDisputedNrc;
    }

    public String getProviderCircuitId() {
        return providerCircuitId;
    }

    public void setProviderCircuitId(final String providerCircuitId) {
        this.providerCircuitId = providerCircuitId;
    }

    public String getSummaryBill() {
        return summaryBill;
    }

    public void setSummaryBill(final String summaryBill) {
        this.summaryBill = summaryBill;
    }

    public Date getOpenDate() {
        return openDate;
    }

    public void setOpenDate(final Date openDate) {
        this.openDate = openDate;
    }

    public Date getDisputeFollowUpDate() {
        return disputeFollowUpDate;
    }

    public void setDisputeFollowUpDate(final Date disputeFollowUpDate) {
        this.disputeFollowUpDate = disputeFollowUpDate;
    }

    public String getCreditRecognized() {
        return creditRecognized;
    }

    public void setCreditRecognized(final String creditRecognized) {
        this.creditRecognized = creditRecognized;
    }

    public Date getBillingReviewCompleteDate() {
        return billingReviewCompleteDate;
    }

    public void setBillingReviewCompleteDate(final Date billingReviewCompleteDate) {
        this.billingReviewCompleteDate = billingReviewCompleteDate;
    }

    public Date getDisputeClosedDate() {
        return disputeClosedDate;
    }

    public void setDisputeClosedDate(final Date disputeClosedDate) {
        this.disputeClosedDate = disputeClosedDate;
    }

    public String getInvoiceNum() {
        return invoiceNum;
    }

    public void setInvoiceNum(final String invoiceNum) {
        this.invoiceNum = invoiceNum;
    }

    public String getVendorTrackingNum() {
        return vendorTrackingNum;
    }

    public void setVendorTrackingNum(final String vendorTrackingNum) {
        this.vendorTrackingNum = vendorTrackingNum;
    }

    public BigDecimal getServiceMrc() {
        return serviceMrc;
    }

    public void setServiceMrc(final BigDecimal serviceMrc) {
        this.serviceMrc = serviceMrc;
    }

    public BigDecimal getServiceNrc() {
        return serviceNrc;
    }

    public void setServiceNrc(final BigDecimal serviceNrc) {
        this.serviceNrc = serviceNrc;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(final String speed) {
        this.speed = speed;
    }

    public boolean isHasIcb() {
        return hasIcb;
    }

    public void setHasIcb(final boolean hasIcb) {
        this.hasIcb = hasIcb;
    }

    public boolean isServiceActive() {
        return serviceActive;
    }

    public void setServiceActive(final boolean serviceActive) {
        this.serviceActive = serviceActive;
    }

    public String getLatestNote() {
        return latestNote;
    }

    public void setLatestNote(final String latestNote) {
        this.latestNote = latestNote;
    }

    public BigDecimal getRealizedCredit() {
        return realizedCredit;
    }

    public void setRealizedCredit(final BigDecimal realizedCredit) {
        this.realizedCredit = realizedCredit;
    }

    public BigDecimal getRealizedMrcAdjustment() {
        return realizedMrcAdjustment;
    }

    public void setRealizedMrcAdjustment(final BigDecimal realizedMrcAdjustment) {
        this.realizedMrcAdjustment = realizedMrcAdjustment;
    }

    public BigDecimal getAnnualizedMrcSave() {
        return annualizedMrcSave;
    }

    public void setAnnualizedMrcSave(final BigDecimal annualizedMrcSave) {
        this.annualizedMrcSave = annualizedMrcSave;
    }

    public boolean isShowDisputeFollowUpIcon() {
        return showDisputeFollowUpIcon;
    }

    public void setShowDisputeFollowUpIcon(final boolean showDisputeFollowUpIcon) {
        this.showDisputeFollowUpIcon = showDisputeFollowUpIcon;
    }

    public String getDisputeAssignment() {
        return disputeAssignment;
    }

    public void setDisputeAssignment(final String disputeAssignment) {
        this.disputeAssignment = disputeAssignment;
    }

    public String getServiceBilledTo() {
        return serviceBilledTo;
    }

    public void setServiceBilledTo(final String serviceBilledTo) {
        this.serviceBilledTo = serviceBilledTo;
    }

    public String getClientLocationId() {
        return clientLocationId;
    }

    public void setClientLocationId(final String clientLocationId) {
        this.clientLocationId = clientLocationId;
    }

    public String getClientServiceId() {
        return clientServiceId;
    }

    public void setClientServiceId(final String clientServiceId) {
        this.clientServiceId = clientServiceId;
    }

    public String getParentCompanyClientId() {
        return parentCompanyClientId;
    }

    public void setParentCompanyClientId(final String parentCompanyClientId) {
        this.parentCompanyClientId = parentCompanyClientId;
    }

    public String getEndCustomerClientId() {
        return endCustomerClientId;
    }

    public void setEndCustomerClientId(final String endCustomerClientId) {
        this.endCustomerClientId = endCustomerClientId;
    }
}
