package com.endeavorms.velocity.qto.disconnect;

import com.endeavorms.velocity.qto.common.AbstractMasterCustomerOwnedEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author fcurran
 * @since 1.3.0
 */
@Entity
@Table(name = "v_manage_disconnects")
public class DisconnectView extends AbstractMasterCustomerOwnedEntity {
    
    @Id
    @Column(name = "service_id")
    private Long id;

    @Column(name = "location_id")
    private Long locationId;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "client_location_id")
    private String clientLocationId;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "parent_company_name")
    private String parentCompanyName;

    @Column(name = "parent_company_client_id")
    private String parentCompanyClientId;

    @Column(name = "end_customer_client_id")
    private String endCustomerClientId;

    @Column(name = "service_type")
    private String type;

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

    @Column(name = "provisioner")
    private String provisioner;

    @Column(name = "disconnect_reason")
    private String disconnectReason;

    @Column(name = "service_status")
    private String status;

    @Column(name = "service_billed_to")
    private String serviceBilledTo;

    @Column (name = "progress_percentage")
    private Long progressPercentage;

    @Column(name = "provider")
    private String provider;

    @Column(name = "provider_order_submitted")
    private Date providerOrderSubmitted;

    @Column(name = "provider_order_num")
    private String providerOrderNumber;

    @Column(name = "customer_requested_disconnect")
    private Date customerRequestedDisconnect;

    @Column(name = "network_provider_foc")
    private Date networkProviderFoc;

    @Column(name = "complete")
    private Date complete;

    @Column(name = "created")
    private Date created;

    @Column(name = "status_age")
    private Long statusAge;

    @Column(name = "client_service_id")
    private String clientServiceId;

    @Column(name = "service_mrc")
    private BigDecimal mrc;

    @Column(name = "service_mrr")
    private BigDecimal mrr;

    @Column(name = "early_termination_fee")
    private BigDecimal earlyTerminationFee;

    @Column(name = "billing_review_complete")
    private Date billingReviewComplete;

    @Column(name = "latest_note")
    private String latestNote;

    @Column(name = "order_type")
    private String orderType;

    @Column(name= "active")
    private boolean active;

    @Column(name = "project_name")
    private String projectName;

    @Column(name = "linked")
    private boolean linked;

    @Column(name = "bundled")
    private boolean bundled;

    @Column(name = "show_note_icon")
    private boolean showNoteIcon;

    @Column(name = "show_jeop_icon")
    private boolean showJeopIcon;

    @Column(name = "show_open_disconnect_icon")
    private boolean showOpenDisconnectIcon;

    @Column(name = "show_open_mac_icon")
    private boolean showOpenMacIcon;

    @Override
    public Long getId() {
        return id;
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

    public String getClientLocationId() {
        return clientLocationId;
    }

    public void setClientLocationId(final String clientLocationId) {
        this.clientLocationId = clientLocationId;
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

    public String getParentCompanyName() {
        return parentCompanyName;
    }

    public void setParentCompanyName(final String parentCompanyName) {
        this.parentCompanyName = parentCompanyName;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
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

    public String getProvisioner() {
        return provisioner;
    }

    public void setProvisioner(final String provisioner) {
        this.provisioner = provisioner;
    }

    public String getDisconnectReason() {
        return disconnectReason;
    }

    public void setDisconnectReason(final String disconnectReason) {
        this.disconnectReason = disconnectReason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public Long getProgressPercentage() {
        return progressPercentage;
    }

    public void setProgressPercentage(final Long progressPercentage) {
        this.progressPercentage = progressPercentage;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(final String provider) {
        this.provider = provider;
    }

    public Date getProviderOrderSubmitted() {
        return providerOrderSubmitted;
    }

    public void setProviderOrderSubmitted(final Date providerOrderSubmitted) {
        this.providerOrderSubmitted = providerOrderSubmitted;
    }

    public String getProviderOrderNumber() {
        return providerOrderNumber;
    }

    public void setProviderOrderNumber(final String providerOrderNumber) {
        this.providerOrderNumber = providerOrderNumber;
    }

    public Date getCustomerRequestedDisconnect() {
        return customerRequestedDisconnect;
    }

    public void setCustomerRequestedDisconnect(final Date customerRequestedDisconnect) {
        this.customerRequestedDisconnect = customerRequestedDisconnect;
    }

    public Date getNetworkProviderFoc() {
        return networkProviderFoc;
    }

    public void setNetworkProviderFoc(final Date networkProviderFoc) {
        this.networkProviderFoc = networkProviderFoc;
    }

    public Date getComplete() {
        return complete;
    }

    public void setComplete(final Date complete) {
        this.complete = complete;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(final Date created) {
        this.created = created;
    }

    public Long getStatusAge() {
        return statusAge;
    }

    public void setStatusAge(final Long statusAge) {
        this.statusAge = statusAge;
    }

    public String getClientServiceId() {
        return clientServiceId;
    }

    public void setClientServiceId(final String clientServiceId) {
        this.clientServiceId = clientServiceId;
    }

    public BigDecimal getMrc() {
        return mrc;
    }

    public void setMrc(final BigDecimal mrc) {
        this.mrc = mrc;
    }

    public BigDecimal getMrr() {
        return mrr;
    }

    public void setMrr(final BigDecimal mrr) {
        this.mrr = mrr;
    }

    public BigDecimal getEarlyTerminationFee() {
        return earlyTerminationFee;
    }

    public void setEarlyTerminationFee(final BigDecimal earlyTerminationFee) {
        this.earlyTerminationFee = earlyTerminationFee;
    }

    public Date getBillingReviewComplete() {
        return billingReviewComplete;
    }

    public void setBillingReviewComplete(final Date billingReviewComplete) {
        this.billingReviewComplete = billingReviewComplete;
    }

    public String getLatestNote() {
        return latestNote;
    }

    public void setLatestNote(final String latestNote) {
        this.latestNote = latestNote;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(final String orderType) {
        this.orderType = orderType;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(final boolean active) {
        this.active = active;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(final String projectName) {
        this.projectName = projectName;
    }

    public boolean isLinked() {
        return linked;
    }

    public void setLinked(final boolean linked) {
        this.linked = linked;
    }

    public boolean isBundled() {
        return bundled;
    }

    public void setBundled(final boolean bundled) {
        this.bundled = bundled;
    }

    public boolean isShowNoteIcon() {
        return showNoteIcon;
    }

    public void setShowNoteIcon(final boolean showNoteIcon) {
        this.showNoteIcon = showNoteIcon;
    }

    public boolean isShowJeopIcon() {
        return showJeopIcon;
    }

    public void setShowJeopIcon(final boolean showJeopIcon) {
        this.showJeopIcon = showJeopIcon;
    }

    public String getServiceBilledTo() {
        return serviceBilledTo;
    }

    public void setServiceBilledTo(final String serviceBilledTo) {
        this.serviceBilledTo = serviceBilledTo;
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

    public boolean isShowOpenDisconnectIcon() {
        return showOpenDisconnectIcon;
    }

    public void setShowOpenDisconnectIcon(final boolean showOpenDisconnectIcon) {
        this.showOpenDisconnectIcon = showOpenDisconnectIcon;
    }

    public boolean isShowOpenMacIcon() {
        return showOpenMacIcon;
    }

    public void setShowOpenMacIcon(final boolean showOpenMacIcon) {
        this.showOpenMacIcon = showOpenMacIcon;
    }
}
