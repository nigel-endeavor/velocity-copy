package com.endeavorms.velocity.qto.location.view;

import com.endeavorms.velocity.qto.common.BaseSearchCriteria;
import com.endeavorms.velocity.qto.common.DateRangeType;
import com.endeavorms.velocity.qto.common.RangeType;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author rcasey
 * @since 1/9/2023
 */
public class LocationViewSearchCriteria extends BaseSearchCriteria<LocationView> {
    private String search;

    /** Allows filtering by a location ID. */
    private Long id;

    private List<String> companyName;

    private String endCustomerClientId;

    private String address;

    private String address1;

    private String address2;

    private String city;

    private String stateProvince;

    private String postalCode;

    private List<String> parentCompanyName;

    private String parentCompanyClientId;

    private Long relocateLocationId;

    private Long parentCompanyId;

    private List<String> provisioner;

    private String clientOrderId;

    private String clientLocationId;

    private String locationName;

    private List<String> locationStatus;

    private List<Long> countServices;

    private List<String> services;

    private List<Date> completionDate;

    private List<DateRangeType> completionDateComparison;

    private String openJeops;

    private List<String> openJeopResponsibilities;

    private boolean hideTerminalStatuses;

    private boolean activeOnly;

    private boolean macOnly;

    private Long orderId;

    private String recordSource;

    private String clientProjectManager;

    private String vertekProjectManager;

    private String clientLocationInfo;

    private String clientLocationType;

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

    public String getSearch() {
        return search;
    }

    public void setSearch(final String search) {
        this.search = search;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
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

    public Long getParentCompanyId() {
        return parentCompanyId;
    }

    public void setParentCompanyId(final Long parentCompanyId) {
        this.parentCompanyId = parentCompanyId;
    }

    public Long getRelocateLocationId() {
        return relocateLocationId;
    }

    public void setRelocateLocationId(final Long relocateLocationId) {
        this.relocateLocationId = relocateLocationId;
    }

    public List<String> getProvisioner() {
        return provisioner;
    }

    public void setProvisioner(final List<String> provisioner) {
        this.provisioner = provisioner;
    }

    public String getClientOrderId() {
        return clientOrderId;
    }

    public void setClientOrderId(final String clientOrderId) {
        this.clientOrderId = clientOrderId;
    }

    public String getClientLocationId() {
        return clientLocationId;
    }

    public void setClientLocationId(final String clientLocationId) {
        this.clientLocationId = clientLocationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(final String locationName) {
        this.locationName = locationName;
    }

    public List<String> getLocationStatus() {
        return locationStatus;
    }

    public void setLocationStatus(final List<String> locationStatus) {
        this.locationStatus = locationStatus;
    }

    public List<Long> getCountServices() {
        return countServices;
    }

    public void setCountServices(final List<Long> countServices) {
        this.countServices = countServices;
    }

    public List<String> getServices() {
        return services;
    }

    public void setServices(final List<String> services) {
        this.services = services;
    }

    public List<Date> getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(final List<Date> completionDate) {
        this.completionDate = completionDate;
    }

    public List<DateRangeType> getCompletionDateComparison() {
        return completionDateComparison;
    }

    public void setCompletionDateComparison(final List<DateRangeType> completionDateComparison) {
        this.completionDateComparison = completionDateComparison;
    }

    public String getOpenJeops() {
        return openJeops;
    }

    public void setOpenJeops(final String openJeops) {
        this.openJeops = openJeops;
    }

    public List<String> getOpenJeopResponsibilities() {
        return openJeopResponsibilities;
    }

    public void setOpenJeopResponsibilities(final List<String> openJeopResponsibilities) {
        this.openJeopResponsibilities = openJeopResponsibilities;
    }

    public boolean getHideTerminalStatuses() {
        return hideTerminalStatuses;
    }

    public void setHideTerminalStatuses(final boolean hideTerminalStatuses) {
        this.hideTerminalStatuses = hideTerminalStatuses;
    }

    public boolean getActiveOnly() {
        return activeOnly;
    }

    public void setActiveOnly(final boolean activeOnly) {
        this.activeOnly = activeOnly;
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

    public boolean isMacOnly() {
        return macOnly;
    }

    public void setMacOnly(final boolean macOnly) {
        this.macOnly = macOnly;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(final Long orderId) {
        this.orderId = orderId;
    }

    public String getRecordSource() {
        return recordSource;
    }

    public void setRecordSource(final String recordSource) {
        this.recordSource = recordSource;
    }

    public String getClientProjectManager() {
        return clientProjectManager;
    }

    public void setClientProjectManager(final String clientProjectManager) {
        this.clientProjectManager = clientProjectManager;
    }

    public String getVertekProjectManager() {
        return vertekProjectManager;
    }

    public void setVertekProjectManager(final String vertekProjectManager) {
        this.vertekProjectManager = vertekProjectManager;
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
