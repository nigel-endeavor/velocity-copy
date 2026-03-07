package com.vertek.corporate.qto.location.inventoryview;

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
public class LocationInventoryViewSearchCriteria extends BaseSearchCriteria<LocationInventoryView> {
    @QueryParam("search")
    private String search;

    /** Allows filtering by a location ID. */
    @QueryParam("id")
    private Long id;

    @QueryParam("orderId")
    private Long orderId;

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

    @QueryParam("activeServiceCount")
    private List<Long> activeServiceCount;

    @QueryParam("services")
    private List<String> services;

    @QueryParam("inventoryAddedDate")
    private List<Date> inventoryAddedDate;

    @QueryParam("inventoryAddedDate-comparison")
    private List<DateRangeType> inventoryAddedDateComparison;

    @QueryParam("activeOnly")
    private boolean activeOnly;

    @QueryParam("macdOpen")
    private boolean macdOpen;

    @QueryParam("disputeOpen")
    private boolean disputeOpen;

    @QueryParam("macdCount")
    private List<Long> macdCount;

    @QueryParam("subOrderTypes")
    private String subOrderTypes;

    @QueryParam("activeInactive")
    private String activeInactive;

    @QueryParam("parentCompanyId")
    private List<Long> parentCompanyId;

    @QueryParam("openDisputeMrc")
    private List<BigDecimal> openDisputeMrc;

    @QueryParam("openDisputeMrc-comparison")
    private List<RangeType> openDisputeMrcRange;

    @QueryParam("openDisputeNrc")
    private List<BigDecimal> openDisputeNrc;

    @QueryParam("openDisputeNrc-comparison")
    private List<RangeType> openDisputeNrcRange;

    @QueryParam("activeCompleteMrc")
    private List<BigDecimal> activeCompleteMrc;

    @QueryParam("activeCompleteMrc-comparison")
    private List<RangeType> activeCompleteMrcRange;

    @QueryParam("activeCompleteNrc")
    private List<BigDecimal> activeCompleteNrc;

    @QueryParam("activeCompleteNrc-comparison")
    private List<RangeType> activeCompleteNrcRange;

    @QueryParam("activeCompleteMrr")
    private List<BigDecimal> activeCompleteMrr;

    @QueryParam("activeCompleteMrr-comparison")
    private List<RangeType> activeCompleteMrrRange;

    @QueryParam("activeCompleteNrr")
    private List<BigDecimal> activeCompleteNrr;

    @QueryParam("activeCompleteNrr-comparison")
    private List<RangeType> activeCompleteNrrRange;

    @QueryParam("annualRecurringCost")
    private List<BigDecimal> annualRecurringCost;

    @QueryParam("annualRecurringCost-comparison")
    private List<RangeType> annualRecurringCostRange;

    @QueryParam("relocateLocationId")
    private Long relocateLocationId;

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

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(final Long orderId) {
        this.orderId = orderId;
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

    public List<Date> getInventoryAddedDate() {
        return inventoryAddedDate;
    }

    public void setInventoryAddedDate(final List<Date> inventoryAddedDate) {
        this.inventoryAddedDate = inventoryAddedDate;
    }

    public List<DateRangeType> getInventoryAddedDateComparison() {
        return inventoryAddedDateComparison;
    }

    public void setInventoryAddedDateComparison(final List<DateRangeType> inventoryAddedDateComparison) {
        this.inventoryAddedDateComparison = inventoryAddedDateComparison;
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

    public boolean isMacdOpen() {
        return macdOpen;
    }

    public void setMacdOpen(final boolean macdOpen) {
        this.macdOpen = macdOpen;
    }

    public boolean isDisputeOpen() {
        return disputeOpen;
    }

    public void setDisputeOpen(final boolean disputeOpen) {
        this.disputeOpen = disputeOpen;
    }

    public List<Long> getActiveServiceCount() {
        return activeServiceCount;
    }

    public void setActiveServiceCount(final List<Long> activeServiceCount) {
        this.activeServiceCount = activeServiceCount;
    }

    public List<Long> getMacdCount() {
        return macdCount;
    }

    public void setMacdCount(final List<Long> macdCount) {
        this.macdCount = macdCount;
    }

    public String getSubOrderTypes() {
        return subOrderTypes;
    }

    public void setSubOrderTypes(final String subOrderTypes) {
        this.subOrderTypes = subOrderTypes;
    }

    public String getActiveInactive() {
        return activeInactive;
    }

    public void setActiveInactive(final String activeInactive) {
        this.activeInactive = activeInactive;
    }

    public List<Long> getParentCompanyId() {
        return parentCompanyId;
    }

    public void setParentCompanyId(final List<Long> parentCompanyId) {
        this.parentCompanyId = parentCompanyId;
    }

    public List<BigDecimal> getActiveCompleteMrc() {
        return activeCompleteMrc;
    }

    public void setActiveCompleteMrc(final List<BigDecimal> activeCompleteMrc) {
        this.activeCompleteMrc = activeCompleteMrc;
    }

    public List<RangeType> getActiveCompleteMrcRange() {
        return activeCompleteMrcRange;
    }

    public void setActiveCompleteMrcRange(final List<RangeType> activeCompleteMrcRange) {
        this.activeCompleteMrcRange = activeCompleteMrcRange;
    }

    public List<BigDecimal> getActiveCompleteNrc() {
        return activeCompleteNrc;
    }

    public void setActiveCompleteNrc(final List<BigDecimal> activeCompleteNrc) {
        this.activeCompleteNrc = activeCompleteNrc;
    }

    public List<RangeType> getActiveCompleteNrcRange() {
        return activeCompleteNrcRange;
    }

    public void setActiveCompleteNrcRange(final List<RangeType> activeCompleteNrcRange) {
        this.activeCompleteNrcRange = activeCompleteNrcRange;
    }

    public List<BigDecimal> getActiveCompleteMrr() {
        return activeCompleteMrr;
    }

    public void setActiveCompleteMrr(final List<BigDecimal> activeCompleteMrr) {
        this.activeCompleteMrr = activeCompleteMrr;
    }

    public List<RangeType> getActiveCompleteMrrRange() {
        return activeCompleteMrrRange;
    }

    public void setActiveCompleteMrrRange(final List<RangeType> activeCompleteMrrRange) {
        this.activeCompleteMrrRange = activeCompleteMrrRange;
    }

    public List<BigDecimal> getActiveCompleteNrr() {
        return activeCompleteNrr;
    }

    public void setActiveCompleteNrr(final List<BigDecimal> activeCompleteNrr) {
        this.activeCompleteNrr = activeCompleteNrr;
    }

    public List<RangeType> getActiveCompleteNrrRange() {
        return activeCompleteNrrRange;
    }

    public void setActiveCompleteNrrRange(final List<RangeType> activeCompleteNrrRange) {
        this.activeCompleteNrrRange = activeCompleteNrrRange;
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

    public List<BigDecimal> getOpenDisputeMrc() {
        return openDisputeMrc;
    }

    public void setOpenDisputeMrc(final List<BigDecimal> openDisputeMrc) {
        this.openDisputeMrc = openDisputeMrc;
    }

    public List<RangeType> getOpenDisputeMrcRange() {
        return openDisputeMrcRange;
    }

    public void setOpenDisputeMrcRange(final List<RangeType> openDisputeMrcRange) {
        this.openDisputeMrcRange = openDisputeMrcRange;
    }

    public List<BigDecimal> getOpenDisputeNrc() {
        return openDisputeNrc;
    }

    public void setOpenDisputeNrc(final List<BigDecimal> openDisputeNrc) {
        this.openDisputeNrc = openDisputeNrc;
    }

    public List<RangeType> getOpenDisputeNrcRange() {
        return openDisputeNrcRange;
    }

    public void setOpenDisputeNrcRange(final List<RangeType> openDisputeNrcRange) {
        this.openDisputeNrcRange = openDisputeNrcRange;
    }

    public Long getRelocateLocationId() {
        return relocateLocationId;
    }

    public void setRelocateLocationId(final Long relocateLocationId) {
        this.relocateLocationId = relocateLocationId;
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
