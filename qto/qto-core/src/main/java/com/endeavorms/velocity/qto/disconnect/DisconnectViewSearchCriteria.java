package com.endeavorms.velocity.qto.disconnect;

import com.endeavorms.velocity.qto.common.BaseSearchCriteria;
import com.endeavorms.velocity.qto.common.DateRangeType;
import com.endeavorms.velocity.qto.common.RangeType;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author fcurran
 * @since 1.3.0
 */
public class DisconnectViewSearchCriteria extends BaseSearchCriteria<DisconnectView> {
    private String search;

    private Long locationId;

    private List<RangeType> locationIdRange;

    private String clientLocationId;

    private List<String> companyName;

    private String endCustomerClientId;

    private List<String> parentCompanyName;

    private String parentCompanyClientId;

    private List<String> serviceType;

    private String address;

    private String address1;

    private String address2;

    private String city;

    private String stateProvince;

    private String postalCode;

    private List<String> provisioner;

    private List<String> disconnectReason;

    private String status;

    private List<String> provider;

    private List<Date> providerOrderSubmitted;

    private List<DateRangeType> providerOrderSubmittedRange;

    private String providerOrderNumber;

    private List<Date> customerRequestedDisconnect;

    private List<DateRangeType> customerRequestedDisconnectRange;

    private List<Date> networkProviderFoc;

    private List<DateRangeType> networkProviderFocRange;

    private List<Date> complete;

    private List<DateRangeType> completeRange;

    private List<Date> created;

    private List<DateRangeType> createdRange;

    private List<Long> statusAge;

    private List<RangeType> statusAgeRange;

    private List<BigDecimal> mrr;

    private List<RangeType> mrrRange;

    private List<BigDecimal> mrc;

    private List<RangeType> mrcRange;

    private List<BigDecimal> earlyTerminationFee;

    private List<RangeType> earlyTerminationFeeRange;

    private String latestNote;

    private List<Date> billingReviewComplete;

    private List<DateRangeType> billingReviewCompleteRange;

    private boolean pendingDisconnect;

    private String projectName;

    private List<String> serviceBilledTo;

    public String getSearch() {
        return search;
    }

    public void setSearch(final String search) {
        this.search = search;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(final Long locationId) {
        this.locationId = locationId;
    }

    public List<RangeType> getLocationIdRange() {
        return locationIdRange;
    }

    public void setLocationIdRange(final List<RangeType> locationIdRange) {
        this.locationIdRange = locationIdRange;
    }

    public String getClientLocationId() {
        return clientLocationId;
    }

    public void setClientLocationId(final String clientLocationId) {
        this.clientLocationId = clientLocationId;
    }

    public List<String> getCompanyName() {
        return companyName;
    }

    public void setCompanyName(final List<String> companyName) {
        this.companyName = companyName;
    }

    public List<String> getParentCompanyName() {
        return parentCompanyName;
    }

    public void setParentCompanyName(final List<String> parentCompanyName) {
        this.parentCompanyName = parentCompanyName;
    }

    public List<String> getServiceType() {
        return serviceType;
    }

    public void setServiceType(final List<String> serviceType) {
        this.serviceType = serviceType;
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

    public List<String> getProvisioner() {
        return provisioner;
    }

    public void setProvisioner(final List<String> provisioner) {
        this.provisioner = provisioner;
    }

    public List<String> getDisconnectReason() {
        return disconnectReason;
    }

    public void setDisconnectReason(final List<String> disconnectReason) {
        this.disconnectReason = disconnectReason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public List<String> getProvider() {
        return provider;
    }

    public void setProvider(final List<String> provider) {
        this.provider = provider;
    }

    public List<Date> getProviderOrderSubmitted() {
        return providerOrderSubmitted;
    }

    public void setProviderOrderSubmitted(final List<Date> providerOrderSubmitted) {
        this.providerOrderSubmitted = providerOrderSubmitted;
    }

    public List<DateRangeType> getProviderOrderSubmittedRange() {
        return providerOrderSubmittedRange;
    }

    public void setProviderOrderSubmittedRange(final List<DateRangeType> providerOrderSubmittedRange) {
        this.providerOrderSubmittedRange = providerOrderSubmittedRange;
    }

    public String getProviderOrderNumber() {
        return providerOrderNumber;
    }

    public void setProviderOrderNumber(final String providerOrderNumber) {
        this.providerOrderNumber = providerOrderNumber;
    }

    public List<Date> getCustomerRequestedDisconnect() {
        return customerRequestedDisconnect;
    }

    public void setCustomerRequestedDisconnect(final List<Date> customerRequestedDisconnect) {
        this.customerRequestedDisconnect = customerRequestedDisconnect;
    }

    public List<DateRangeType> getCustomerRequestedDisconnectRange() {
        return customerRequestedDisconnectRange;
    }

    public void setCustomerRequestedDisconnectRange(final List<DateRangeType> customerRequestedDisconnectRange) {
        this.customerRequestedDisconnectRange = customerRequestedDisconnectRange;
    }

    public List<Date> getNetworkProviderFoc() {
        return networkProviderFoc;
    }

    public void setNetworkProviderFoc(final List<Date> networkProviderFoc) {
        this.networkProviderFoc = networkProviderFoc;
    }

    public List<DateRangeType> getNetworkProviderFocRange() {
        return networkProviderFocRange;
    }

    public void setNetworkProviderFocRange(final List<DateRangeType> networkProviderFocRange) {
        this.networkProviderFocRange = networkProviderFocRange;
    }

    public List<Date> getComplete() {
        return complete;
    }

    public void setComplete(final List<Date> complete) {
        this.complete = complete;
    }

    public List<DateRangeType> getCompleteRange() {
        return completeRange;
    }

    public void setCompleteRange(final List<DateRangeType> completeRange) {
        this.completeRange = completeRange;
    }

    public List<Date> getCreated() {
        return created;
    }

    public void setCreated(final List<Date> created) {
        this.created = created;
    }

    public List<DateRangeType> getCreatedRange() {
        return createdRange;
    }

    public void setCreatedRange(final List<DateRangeType> createdRange) {
        this.createdRange = createdRange;
    }

    public List<Long> getStatusAge() {
        return statusAge;
    }

    public void setStatusAge(final List<Long> statusAge) {
        this.statusAge = statusAge;
    }

    public List<RangeType> getStatusAgeRange() {
        return statusAgeRange;
    }

    public void setStatusAgeRange(final List<RangeType> statusAgeRange) {
        this.statusAgeRange = statusAgeRange;
    }

    public List<BigDecimal> getMrc() {
        return mrc;
    }

    public void setMrc(final List<BigDecimal> mrc) {
        this.mrc = mrc;
    }

    public List<RangeType> getMrcRange() {
        return mrcRange;
    }

    public void setMrcRange(final List<RangeType> mrcRange) {
        this.mrcRange = mrcRange;
    }

    public List<BigDecimal> getMrr() {
        return mrr;
    }

    public void setMrr(final List<BigDecimal> mrr) {
        this.mrr = mrr;
    }

    public List<RangeType> getMrrRange() {
        return mrrRange;
    }

    public void setMrrRange(final List<RangeType> mrrRange) {
        this.mrrRange = mrrRange;
    }

    public List<BigDecimal> getEarlyTerminationFee() {
        return earlyTerminationFee;
    }

    public void setEarlyTerminationFee(final List<BigDecimal> earlyTerminationFee) {
        this.earlyTerminationFee = earlyTerminationFee;
    }

    public List<RangeType> getEarlyTerminationFeeRange() {
        return earlyTerminationFeeRange;
    }

    public void setEarlyTerminationFeeRange(final List<RangeType> earlyTerminationFeeRange) {
        this.earlyTerminationFeeRange = earlyTerminationFeeRange;
    }

    public String getLatestNote() {
        return latestNote;
    }

    public void setLatestNote(final String latestNote) {
        this.latestNote = latestNote;
    }

    public List<Date> getBillingReviewComplete() {
        return billingReviewComplete;
    }

    public void setBillingReviewComplete(final List<Date> billingReviewComplete) {
        this.billingReviewComplete = billingReviewComplete;
    }

    public List<DateRangeType> getBillingReviewCompleteRange() {
        return billingReviewCompleteRange;
    }

    public void setBillingReviewCompleteRange(final List<DateRangeType> billingReviewCompleteRange) {
        this.billingReviewCompleteRange = billingReviewCompleteRange;
    }

    public boolean isPendingDisconnect() {
        return pendingDisconnect;
    }

    public void setPendingDisconnect(final boolean pendingDisconnect) {
        this.pendingDisconnect = pendingDisconnect;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(final String projectName) {
        this.projectName = projectName;
    }

    public List<String> getServiceBilledTo() {
        return serviceBilledTo;
    }

    public void setServiceBilledTo(final List<String> serviceBilledTo) {
        this.serviceBilledTo = serviceBilledTo;
    }

    public String getEndCustomerClientId() {
        return endCustomerClientId;
    }

    public void setEndCustomerClientId(final String endCustomerClientId) {
        this.endCustomerClientId = endCustomerClientId;
    }

    public String getParentCompanyClientId() {
        return parentCompanyClientId;
    }

    public void setParentCompanyClientId(final String parentCompanyClientId) {
        this.parentCompanyClientId = parentCompanyClientId;
    }
}
