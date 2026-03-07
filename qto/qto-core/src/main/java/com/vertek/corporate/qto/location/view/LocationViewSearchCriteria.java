package com.vertek.corporate.qto.location.view;

import com.vertek.corporate.qto.common.BaseSearchCriteria;
import com.vertek.corporate.qto.common.DateRangeType;
import com.vertek.corporate.qto.common.RangeType;

import jakarta.ws.rs.QueryParam;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author rcasey
 * @since 1/9/2023
 */
public class LocationViewSearchCriteria extends BaseSearchCriteria<LocationView> {
    @QueryParam("search")
    private String search;

    /** Allows filtering by a location ID. */
    @QueryParam("id")
    private Long id;

    @QueryParam("companyName")
    private List<String> companyName;

    @QueryParam("endCustomerClientId")
    private String endCustomerClientId;

    @QueryParam("address")
    private String address;

    @QueryParam("address1")
    private String address1;

    @QueryParam("address2")
    private String address2;

    @QueryParam("city")
    private String city;

    @QueryParam("stateProvince")
    private String stateProvince;

    @QueryParam("postalCode")
    private String postalCode;

    @QueryParam("parentCompanyName")
    private List<String> parentCompanyName;

    @QueryParam("parentCompanyClientId")
    private String parentCompanyClientId;

    @QueryParam("relocateLocationId")
    private Long relocateLocationId;

    @QueryParam("parentCompanyId")
    private Long parentCompanyId;

    @QueryParam("provisioner")
    private List<String> provisioner;

    @QueryParam("clientOrderId")
    private String clientOrderId;

    @QueryParam("clientLocationId")
    private String clientLocationId;

    @QueryParam("locationName")
    private String locationName;

    @QueryParam("locationStatus")
    private List<String> locationStatus;

    @QueryParam("countServices")
    private List<Long> countServices;

    @QueryParam("services")
    private List<String> services;

    @QueryParam("completionDate")
    private List<Date> completionDate;

    @QueryParam("completionDate-comparison")
    private List<DateRangeType> completionDateComparison;

    @QueryParam("openJeops")
    private String openJeops;

    @QueryParam("openJeopResponsibilities")
    private List<String> openJeopResponsibilities;

    @QueryParam("hideTerminalStatuses")
    private boolean hideTerminalStatuses;

    @QueryParam("activeOnly")
    private boolean activeOnly;

    @QueryParam("macOnly")
    private boolean macOnly;

    @QueryParam("orderId")
    private Long orderId;

    @QueryParam("recordSource")
    private String recordSource;

    @QueryParam("clientProjectManager")
    private String clientProjectManager;

    @QueryParam("vertekProjectManager")
    private String vertekProjectManager;

    @QueryParam("clientLocationInfo")
    private String clientLocationInfo;

    @QueryParam("clientLocationType")
    private String clientLocationType;

    @QueryParam("mrc")
    private List<BigDecimal> mrc;

    @QueryParam("mrc-comparison")
    private List<RangeType> mrcRange;

    @QueryParam("nrc")
    private List<BigDecimal> nrc;

    @QueryParam("nrc-comparison")
    private List<RangeType> nrcRange;

    @QueryParam("mrr")
    private List<BigDecimal> mrr;

    @QueryParam("mrr-comparison")
    private List<RangeType> mrrRange;

    @QueryParam("nrr")
    private List<BigDecimal> nrr;

    @QueryParam("nrr-comparison")
    private List<RangeType> nrrRange;

    @QueryParam("annualRecurringCost")
    private List<BigDecimal> annualRecurringCost;

    @QueryParam("annualRecurringCost-comparison")
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
