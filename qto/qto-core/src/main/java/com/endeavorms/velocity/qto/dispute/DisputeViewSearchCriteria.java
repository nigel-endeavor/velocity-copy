package com.endeavorms.velocity.qto.dispute;

import com.endeavorms.velocity.qto.common.BaseSearchCriteria;
import com.endeavorms.velocity.qto.common.DateRangeType;
import com.endeavorms.velocity.qto.common.RangeType;

import jakarta.ws.rs.QueryParam;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author fcurran
 * @since 9/18/2023
 */
public class DisputeViewSearchCriteria extends BaseSearchCriteria<DisputeView> {
    @QueryParam("search")
    private String search;

    @QueryParam("locationId")
    private Long locationId;

    @QueryParam("locationId-comparison")
    private List<RangeType> locationIdRange;

    @QueryParam("parentCompanyName")
    private List<String> parentCompanyName;

    @QueryParam("parentCompanyClientId")
    private String parentCompanyClientId;

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

    @QueryParam("serviceType")
    private List<String> serviceType;

    @QueryParam("disputeType")
    private String disputeType;

    @QueryParam("disputeStatus")
    private String disputeStatus;

    @QueryParam("provider")
    private List<String> provider;

    @QueryParam("amountDisputedMrc")
    private List<BigDecimal> amountDisputedMrc;

    @QueryParam("amountDisputedMrc-comparison")
    private List<RangeType> amountDisputedMrcRange;

    @QueryParam("amountDisputedNrc")
    private List<BigDecimal> amountDisputedNrc;

    @QueryParam("amountDisputedNrc-comparison")
    private List<RangeType> amountDisputedNrcRange;

    @QueryParam("openDate")
    private List<Date> openDate;

    @QueryParam("openDate-comparison")
    private List<DateRangeType> openDateRange;

    @QueryParam("billingReviewCompleteDate")
    private List<Date> billingReviewCompleteDate;

    @QueryParam("billingReviewCompleteDate-comparison")
    private List<DateRangeType> billingReviewCompleteDateRange;

    @QueryParam("disputeClosedDate")
    private List<Date> disputeClosedDate;

    @QueryParam("disputeClosedDate-comparison")
    private List<DateRangeType> disputeClosedDateRange;

    @QueryParam("hasIcb")
    private boolean hasIcb;

    @QueryParam("serviceMrc")
    private List<BigDecimal> serviceMrc;

    @QueryParam("serviceMrc-comparison")
    private List<RangeType> serviceMrcRange;

    @QueryParam("serviceNrc")
    private List<BigDecimal> serviceNrc;

    @QueryParam("serviceNrc-comparison")
    private List<RangeType> serviceNrcRange;

    @QueryParam("disputeOpen")
    private boolean disputeOpen;

    @QueryParam("disputeAssignment")
    private List<String> disputeAssignment;

    @QueryParam("serviceBilledTo")
    private List<String> serviceBilledTo;

    @QueryParam("clientServiceId")
    private List<String> clientServiceId;

    @QueryParam("clientLocationId")
    private List<String> clientLocationId;

    @QueryParam("speed")
    private String speed;

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

    public List<String> getParentCompanyName() {
        return parentCompanyName;
    }

    public void setParentCompanyName(final List<String> parentCompanyName) {
        this.parentCompanyName = parentCompanyName;
    }

    public List<String> getCompanyName() {
        return companyName;
    }

    public void setCompanyName(final List<String> companyName) {
        this.companyName = companyName;
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

    public List<String> getServiceType() {
        return serviceType;
    }

    public void setServiceType(final List<String> serviceType) {
        this.serviceType = serviceType;
    }

    public String getDisputeType() {
        return disputeType;
    }

    public void setDisputeType(final String disputeType) {
        this.disputeType = disputeType;
    }

    public String getDisputeStatus() {
        return disputeStatus;
    }

    public void setDisputeStatus(final String disputeStatus) {
        this.disputeStatus = disputeStatus;
    }

    public List<String> getProvider() {
        return provider;
    }

    public void setProvider(final List<String> provider) {
        this.provider = provider;
    }

    public List<BigDecimal> getAmountDisputedMrc() {
        return amountDisputedMrc;
    }

    public void setAmountDisputedMrc(final List<BigDecimal> amountDisputedMrc) {
        this.amountDisputedMrc = amountDisputedMrc;
    }

    public List<RangeType> getAmountDisputedMrcRange() {
        return amountDisputedMrcRange;
    }

    public void setAmountDisputedMrcRange(final List<RangeType> amountDisputedMrcRange) {
        this.amountDisputedMrcRange = amountDisputedMrcRange;
    }

    public List<BigDecimal> getAmountDisputedNrc() {
        return amountDisputedNrc;
    }

    public void setAmountDisputedNrc(final List<BigDecimal> amountDisputedNrc) {
        this.amountDisputedNrc = amountDisputedNrc;
    }

    public List<RangeType> getAmountDisputedNrcRange() {
        return amountDisputedNrcRange;
    }

    public void setAmountDisputedNrcRange(final List<RangeType> amountDisputedNrcRange) {
        this.amountDisputedNrcRange = amountDisputedNrcRange;
    }

    public List<Date> getOpenDate() {
        return openDate;
    }

    public void setOpenDate(final List<Date> openDate) {
        this.openDate = openDate;
    }

    public List<DateRangeType> getOpenDateRange() {
        return openDateRange;
    }

    public void setOpenDateRange(final List<DateRangeType> openDateRange) {
        this.openDateRange = openDateRange;
    }

    public List<Date> getBillingReviewCompleteDate() {
        return billingReviewCompleteDate;
    }

    public void setBillingReviewCompleteDate(final List<Date> billingReviewCompleteDate) {
        this.billingReviewCompleteDate = billingReviewCompleteDate;
    }

    public List<DateRangeType> getBillingReviewCompleteDateRange() {
        return billingReviewCompleteDateRange;
    }

    public void setBillingReviewCompleteDateRange(final List<DateRangeType> billingReviewCompleteDateRange) {
        this.billingReviewCompleteDateRange = billingReviewCompleteDateRange;
    }

    public List<Date> getDisputeClosedDate() {
        return disputeClosedDate;
    }

    public void setDisputeClosedDate(final List<Date> disputeClosedDate) {
        this.disputeClosedDate = disputeClosedDate;
    }

    public List<DateRangeType> getDisputeClosedDateRange() {
        return disputeClosedDateRange;
    }

    public void setDisputeClosedDateRange(final List<DateRangeType> disputeClosedDateRange) {
        this.disputeClosedDateRange = disputeClosedDateRange;
    }

    public boolean isHasIcb() {
        return hasIcb;
    }

    public void setHasIcb(final boolean hasIcb) {
        this.hasIcb = hasIcb;
    }

    public List<BigDecimal> getServiceMrc() {
        return serviceMrc;
    }

    public void setServiceMrc(final List<BigDecimal> serviceMrc) {
        this.serviceMrc = serviceMrc;
    }

    public List<RangeType> getServiceMrcRange() {
        return serviceMrcRange;
    }

    public void setServiceMrcRange(final List<RangeType> serviceMrcRange) {
        this.serviceMrcRange = serviceMrcRange;
    }

    public List<BigDecimal> getServiceNrc() {
        return serviceNrc;
    }

    public void setServiceNrc(final List<BigDecimal> serviceNrc) {
        this.serviceNrc = serviceNrc;
    }

    public List<RangeType> getServiceNrcRange() {
        return serviceNrcRange;
    }

    public void setServiceNrcRange(final List<RangeType> serviceNrcRange) {
        this.serviceNrcRange = serviceNrcRange;
    }

    public boolean isDisputeOpen() {
        return disputeOpen;
    }

    public void setDisputeOpen(final boolean disputeOpen) {
        this.disputeOpen = disputeOpen;
    }

    public List<String> getDisputeAssignment() {
        return disputeAssignment;
    }

    public void setDisputeAssignment(final List<String> disputeAssignment) {
        this.disputeAssignment = disputeAssignment;
    }

    public List<String> getServiceBilledTo() {
        return serviceBilledTo;
    }

    public void setServiceBilledTo(final List<String> serviceBilledTo) {
        this.serviceBilledTo = serviceBilledTo;
    }

    public List<String> getClientServiceId() {
        return clientServiceId;
    }

    public void setClientServiceId(final List<String> clientServiceId) {
        this.clientServiceId = clientServiceId;
    }

    public List<String> getClientLocationId() {
        return clientLocationId;
    }

    public void setClientLocationId(final List<String> clientLocationId) {
        this.clientLocationId = clientLocationId;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(final String speed) {
        this.speed = speed;
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
