package com.endeavorms.velocity.qto.service.inventoryview;

import com.endeavorms.velocity.qto.common.BaseSearchCriteria;
import com.endeavorms.velocity.qto.common.DateRangeType;
import com.endeavorms.velocity.qto.common.RangeType;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author rcasey
 * @since 2/23/2023
 */
public class ServiceInventoryViewSearchCriteria extends BaseSearchCriteria<ServiceInventoryView> {
    private String search;

    private Long locationId;

    private List<RangeType> locationIdRange;

    private String address;

    private String address1;

    private String address2;

    private String city;

    private String stateProvince;

    private String postalCode;

    private String status;

    private List<String> provisioner;

    private List<String> projectManager;

    private List<String> provider;

    private boolean activeOnly;

    private List<Date> created;

    private List<DateRangeType> createdRange;

    private String clientServiceId;

    private String clientLocationId;

    private String clientLocationInfo;

    private List<String> companyName;

    private String endCustomerClientId;

    private List<String> parentCompanyName;

    private String parentCompanyClientId;

    private String providerCircuitId;

    private List<String> serviceType;

    private List<BigDecimal> mrc;

    private List<RangeType> mrcRange;

    private List<BigDecimal> nrc;

    private List<RangeType> nrcRange;

    private List<BigDecimal> mrr;

    private List<RangeType> mrrRange;

    private List<BigDecimal> nrr;

    private List<RangeType> nrrRange;

    private List<BigDecimal> annualRecurringCost;

    private List<RangeType> annualRecurringCostRange;

    private String speed;

    private boolean disputeOpen;

    private List<String> orderType;

    private List<String> subOrderType;

    private Long companyId;

    private boolean macdOpen;

    private String activeInactive;

    private String contractTerm;

    private String accountNumber;

    private String summaryBill;

    private String disputeTypes;

    private String clientLocationType;

    private BigDecimal openDisputeMrc;

    private BigDecimal openDisputeNrc;

    private Long serviceId;

    private String linkBundleType;

    private String linkBundleFrom;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public List<String> getProvisioner() {
        return provisioner;
    }

    public void setProvisioner(final List<String> provisioner) {
        this.provisioner = provisioner;
    }

    public List<String> getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(final List<String> projectManager) {
        this.projectManager = projectManager;
    }

    public List<String> getProvider() {
        return provider;
    }

    public void setProvider(final List<String> provider) {
        this.provider = provider;
    }

    public boolean getActiveOnly() {
        return activeOnly;
    }

    public void setActiveOnly(final boolean activeOnly) {
        this.activeOnly = activeOnly;
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

    public String getClientServiceId() {
        return clientServiceId;
    }

    public void setClientServiceId(final String clientServiceId) {
        this.clientServiceId = clientServiceId;
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

    public String getProviderCircuitId() {
        return providerCircuitId;
    }

    public void setProviderCircuitId(final String providerCircuitId) {
        this.providerCircuitId = providerCircuitId;
    }

    public List<String> getServiceType() {
        return serviceType;
    }

    public void setServiceType(final List<String> serviceType) {
        this.serviceType = serviceType;
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

    public List<BigDecimal> getNrc() {
        return nrc;
    }

    public void setNrc(final List<BigDecimal> nrc) {
        this.nrc = nrc;
    }

    public List<RangeType> getNrcRange() {
        return nrcRange;
    }

    public void setNrcRange(final List<RangeType> nrcRange) {
        this.nrcRange = nrcRange;
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

    public List<BigDecimal> getNrr() {
        return nrr;
    }

    public void setNrr(final List<BigDecimal> nrr) {
        this.nrr = nrr;
    }

    public List<RangeType> getNrrRange() {
        return nrrRange;
    }

    public void setNrrRange(final List<RangeType> nrrRange) {
        this.nrrRange = nrrRange;
    }

    public List<BigDecimal> getAnnualRecurringCost() {
        return annualRecurringCost;
    }

    public void setAnnualRecurringCost(final List<BigDecimal> annualRecurringCost) {
        this.annualRecurringCost = annualRecurringCost;
    }

    public List<RangeType> getAnnualRecurringCostRange() {
        return annualRecurringCostRange;
    }

    public void setAnnualRecurringCostRange(final List<RangeType> annualRecurringCostRange) {
        this.annualRecurringCostRange = annualRecurringCostRange;
    }

    public boolean isDisputeOpen() {
        return disputeOpen;
    }

    public void setDisputeOpen(final boolean disputeOpen) {
        this.disputeOpen = disputeOpen;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(final String speed) {
        this.speed = speed;
    }

    public List<String> getOrderType() {
        return orderType;
    }

    public void setOrderType(final List<String> orderType) {
        this.orderType = orderType;
    }

    public List<String> getSubOrderType() {
        return subOrderType;
    }

    public void setSubOrderType(final List<String> subOrderType) {
        this.subOrderType = subOrderType;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(final Long companyId) {
        this.companyId = companyId;
    }

    public boolean isMacdOpen() {
        return macdOpen;
    }

    public void setMacdOpen(final boolean macdOpen) {
        this.macdOpen = macdOpen;
    }

    public String getActiveInactive() {
        return activeInactive;
    }

    public boolean isActiveOnly() {
        return activeOnly;
    }

    public String getContractTerm() {
        return contractTerm;
    }

    public void setContractTerm(final String contractTerm) {
        this.contractTerm = contractTerm;
    }

    public String getSummaryBill() {
        return summaryBill;
    }

    public void setSummaryBill(final String summaryBill) {
        this.summaryBill = summaryBill;
    }

    public void setActiveInactive(final String activeInactive) {
        this.activeInactive = activeInactive;
    }

    public String getDisputeTypes() {
        return disputeTypes;
    }

    public void setDisputeTypes(final String disputeTypes) {
        this.disputeTypes = disputeTypes;
    }

    public String getClientLocationInfo() {
        return clientLocationInfo;
    }

    public void setClientLocationInfo(final String clientLocationInfo) {
        this.clientLocationInfo = clientLocationInfo;
    }

    public String getClientLocationType() {
        return clientLocationType;
    }

    public void setClientLocationType(final String clientLocationType) {
        this.clientLocationType = clientLocationType;
    }

    public BigDecimal getOpenDisputeMrc() {
        return openDisputeMrc;
    }

    public void setOpenDisputeMrc(final BigDecimal openDisputeMrc) {
        this.openDisputeMrc = openDisputeMrc;
    }

    public BigDecimal getOpenDisputeNrc() {
        return openDisputeNrc;
    }

    public void setOpenDisputeNrc(final BigDecimal openDisputeNrc) {
        this.openDisputeNrc = openDisputeNrc;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(final Long serviceId) {
        this.serviceId = serviceId;
    }

    public String getLinkBundleType() {
        return linkBundleType;
    }

    public void setLinkBundleType(final String linkBundleType) {
        this.linkBundleType = linkBundleType;
    }

    public String getLinkBundleFrom() {
        return linkBundleFrom;
    }

    public void setLinkBundleFrom(final String linkBundleFrom) {
        this.linkBundleFrom = linkBundleFrom;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(final String accountNumber) {
        this.accountNumber = accountNumber;
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
