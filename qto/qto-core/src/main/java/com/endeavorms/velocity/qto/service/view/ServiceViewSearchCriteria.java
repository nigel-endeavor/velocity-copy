package com.endeavorms.velocity.qto.service.view;

import com.endeavorms.velocity.qto.common.BaseSearchCriteria;
import com.endeavorms.velocity.qto.common.DateRangeType;
import com.endeavorms.velocity.qto.common.RangeType;

import jakarta.ws.rs.QueryParam;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author rcasey
 * @since 2/23/2023
 */
public class ServiceViewSearchCriteria extends BaseSearchCriteria<ServiceView> {
    @QueryParam("search")
    private String search;

    @QueryParam("locationId")
    private Long locationId;

    @QueryParam("locationId-comparison")
    private List<RangeType> locationIdRange;

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

    @QueryParam("status")
    private List<String> status;

    @QueryParam("provisioner")
    private List<String> provisioner;

    @QueryParam("projectManager")
    private List<String> projectManager;

    @QueryParam("vertekProjectManager")
    private List<String> vertekProjectManager;

    @QueryParam("provider")
    private List<String> provider;

    @QueryParam("customerRequestedInstall")
    private List<Date> customerRequestedInstall;

    @QueryParam("customerRequestedInstall-comparison")
    private List<DateRangeType> customerRequestedInstallRange;

    @QueryParam("siteSurveyDue")
    private List<Date> siteSurveyDue;

    @QueryParam("siteSurveyDue-comparison")
    private List<DateRangeType> siteSurveyDueRange;

    @QueryParam("siteSurveySubmit")
    private List<Date> siteSurveySubmit;

    @QueryParam("siteSurveySubmit-comparison")
    private List<DateRangeType> siteSurveySubmitRange;

    @QueryParam("providerOrderSubmitted")
    private List<Date> providerOrderSubmitted;

    @QueryParam("providerOrderSubmitted-comparison")
    private List<DateRangeType> providerOrderSubmittedRange;

    @QueryParam("networkProviderFoc")
    private List<Date> networkProviderFoc;

    @QueryParam("networkProviderFoc-comparison")
    private List<DateRangeType> networkProviderFocRange;

    @QueryParam("dataProvisioningComplete")
    private List<Date> dataProvisioningComplete;

    @QueryParam("dataProvisioningComplete-comparison")
    private List<DateRangeType> dataProvisioningCompleteRange;

    @QueryParam("followUpDate")
    private List<Date> followUpDate;

    @QueryParam("followUpDate-comparison")
    private List<DateRangeType> followUpDateRange;

    @QueryParam("clientLocationType")
    private String clientLocationType;

    @QueryParam("clientLocationInfo")
    private String clientLocationInfo;

    @QueryParam("hideTerminalStatuses")
    private boolean hideTerminalStatuses;

    @QueryParam("activeOnly")
    private boolean activeOnly;

    @QueryParam("created")
    private List<Date> created;

    @QueryParam("created-comparison")
    private List<DateRangeType> createdRange;

    @QueryParam("clientServiceId")
    private String clientServiceId;

    @QueryParam("clientLocationId")
    private String clientLocationId;

    @QueryParam("companyName")
    private List<String> companyName;

    @QueryParam("parentCompanyName")
    private List<String> parentCompanyName;

    @QueryParam("statusAge")
    private List<Long> statusAge;

    @QueryParam("statusAge-comparison")
    private List<RangeType> statusAgeRange;

    @QueryParam("serviceType")
    private List<String> serviceType;

    @QueryParam("serviceBilledTo")
    private List<String> serviceBilledTo;

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

    @QueryParam("lconPhone")
    private String lconPhone;

    @QueryParam("speed")
    private String speed;

    @QueryParam("levelOfEffort")
    private List<String> levelOfEffort;

    @QueryParam("latestNote")
    private String latestNote;

    @QueryParam("qaCheckOpen")
    private List<Date> qaCheckOpen;

    @QueryParam("qaCheckOpen-comparison")
    private List<DateRangeType> qaCheckOpenRange;

    @QueryParam("firstVendorInvoice")
    private List<Date> firstVendorInvoice;

    @QueryParam("firstVendorInvoice-comparison")
    private List<DateRangeType> firstVendorInvoiceRange;

    @QueryParam("returnedToOrderGroup")
    private List<Date> returnedToOrderGroup;

    @QueryParam("returnedToOrderGroup-comparison")
    private List<DateRangeType> returnedToOrderGroupRange;

    @QueryParam("returnedToSales")
    private List<Date> returnedToSales;

    @QueryParam("returnedToSales-comparison")
    private List<DateRangeType> returnedToSalesRange;

    @QueryParam("billingReviewComplete")
    private List<Date> billingReviewComplete;

    @QueryParam("billingReviewComplete-comparison")
    private List<DateRangeType> billingReviewCompleteRange;

    @QueryParam("qaManager")
    private List<String> qaManager;

    @QueryParam("macOnly")
    private boolean macOnly;

    @QueryParam("workflowView")
    private String workflowView;

    @QueryParam("projectName")
    private String projectName;

    @QueryParam("recordSource")
    private String recordSource;

    @QueryParam("openJeopResponsibilities")
    private List<String> openJeopResponsibilities;

    @QueryParam("serviceId")
    private Long serviceId;

    @QueryParam("linkType")
    private String linkBundleType;

    @QueryParam("from")
    private String linkBundleFrom;

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

    public List<String> getStatus() {
        return status;
    }

    public void setStatus(final List<String> status) {
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

    public List<String> getVertekProjectManager() {
        return vertekProjectManager;
    }

    public void setVertekProjectManager(final List<String> vertekProjectManager) {
        this.vertekProjectManager = vertekProjectManager;
    }

    public List<String> getProvider() {
        return provider;
    }

    public void setProvider(final List<String> provider) {
        this.provider = provider;
    }

    public List<Date> getCustomerRequestedInstall() {
        return customerRequestedInstall;
    }

    public void setCustomerRequestedInstall(final List<Date> customerRequestedInstall) {
        this.customerRequestedInstall = customerRequestedInstall;
    }

    public List<DateRangeType> getCustomerRequestedInstallRange() {
        return customerRequestedInstallRange;
    }

    public void setCustomerRequestedInstallRange(final List<DateRangeType> customerRequestedInstallRange) {
        this.customerRequestedInstallRange = customerRequestedInstallRange;
    }

    public List<Date> getSiteSurveyDue() {
        return siteSurveyDue;
    }

    public void setSiteSurveyDue(final List<Date> siteSurveyDue) {
        this.siteSurveyDue = siteSurveyDue;
    }

    public List<DateRangeType> getSiteSurveyDueRange() {
        return siteSurveyDueRange;
    }

    public void setSiteSurveyDueRange(final List<DateRangeType> siteSurveyDueRange) {
        this.siteSurveyDueRange = siteSurveyDueRange;
    }

    public List<Date> getSiteSurveySubmit() {
        return siteSurveySubmit;
    }

    public void setSiteSurveySubmit(final List<Date> siteSurveySubmit) {
        this.siteSurveySubmit = siteSurveySubmit;
    }

    public List<DateRangeType> getSiteSurveySubmitRange() {
        return siteSurveySubmitRange;
    }

    public void setSiteSurveySubmitRange(final List<DateRangeType> siteSurveySubmitRange) {
        this.siteSurveySubmitRange = siteSurveySubmitRange;
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

    public List<Date> getDataProvisioningComplete() {
        return dataProvisioningComplete;
    }

    public void setDataProvisioningComplete(final List<Date> dataProvisioningComplete) {
        this.dataProvisioningComplete = dataProvisioningComplete;
    }

    public List<DateRangeType> getDataProvisioningCompleteRange() {
        return dataProvisioningCompleteRange;
    }

    public void setDataProvisioningCompleteRange(final List<DateRangeType> dataProvisioningCompleteRange) {
        this.dataProvisioningCompleteRange = dataProvisioningCompleteRange;
    }

    public List<Date> getFollowUpDate() {
        return followUpDate;
    }

    public void setFollowUpDate(final List<Date> followUpDate) {
        this.followUpDate = followUpDate;
    }

    public List<DateRangeType> getFollowUpDateRange() {
        return followUpDateRange;
    }

    public void setFollowUpDateRange(final List<DateRangeType> followUpDateRange) {
        this.followUpDateRange = followUpDateRange;
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

    public String getLconPhone() {
        return lconPhone;
    }

    public void setLconPhone(final String lconPhone) {
        this.lconPhone = lconPhone;
    }

    public List<String> getLevelOfEffort() {
        return levelOfEffort;
    }

    public void setLevelOfEffort(final List<String> levelOfEffort) {
        this.levelOfEffort = levelOfEffort;
    }

    public String getLatestNote() {
        return latestNote;
    }

    public void setLatestNote(final String latestNote) {
        this.latestNote = latestNote;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(final String speed) {
        this.speed = speed;
    }

    public List<Date> getQaCheckOpen() {
        return qaCheckOpen;
    }

    public void setQaCheckOpen(final List<Date> qaCheckOpen) {
        this.qaCheckOpen = qaCheckOpen;
    }

    public List<DateRangeType> getQaCheckOpenRange() {
        return qaCheckOpenRange;
    }

    public void setQaCheckOpenRange(final List<DateRangeType> qaCheckOpenRange) {
        this.qaCheckOpenRange = qaCheckOpenRange;
    }

    public List<Date> getFirstVendorInvoice() {
        return firstVendorInvoice;
    }

    public void setFirstVendorInvoice(final List<Date> firstVendorInvoice) {
        this.firstVendorInvoice = firstVendorInvoice;
    }

    public List<DateRangeType> getFirstVendorInvoiceRange() {
        return firstVendorInvoiceRange;
    }

    public void setFirstVendorInvoiceRange(final List<DateRangeType> firstVendorInvoiceRange) {
        this.firstVendorInvoiceRange = firstVendorInvoiceRange;
    }

    public List<Date> getReturnedToOrderGroup() {
        return returnedToOrderGroup;
    }

    public void setReturnedToOrderGroup(final List<Date> returnedToOrderGroup) {
        this.returnedToOrderGroup = returnedToOrderGroup;
    }

    public List<DateRangeType> getReturnedToOrderGroupRange() {
        return returnedToOrderGroupRange;
    }

    public void setReturnedToOrderGroupRange(final List<DateRangeType> returnedToOrderGroupRange) {
        this.returnedToOrderGroupRange = returnedToOrderGroupRange;
    }

    public List<Date> getReturnedToSales() {
        return returnedToSales;
    }

    public void setReturnedToSales(final List<Date> returnedToSales) {
        this.returnedToSales = returnedToSales;
    }

    public List<DateRangeType> getReturnedToSalesRange() {
        return returnedToSalesRange;
    }

    public void setReturnedToSalesRange(final List<DateRangeType> returnedToSalesRange) {
        this.returnedToSalesRange = returnedToSalesRange;
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

    public List<String> getQaManager() {
        return qaManager;
    }

    public void setQaManager(final List<String> qaManager) {
        this.qaManager = qaManager;
    }

    public boolean isMacOnly() {
        return macOnly;
    }

    public void setMacOnly(final boolean macOnly) {
        this.macOnly = macOnly;
    }

    public String getWorkflowView() {
        return workflowView;
    }

    public void setWorkflowView(final String workflowView) {
        this.workflowView = workflowView;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(final String projectName) {
        this.projectName = projectName;
    }

    public String getRecordSource() {
        return recordSource;
    }

    public void setRecordSource(final String recordSource) {
        this.recordSource = recordSource;
    }

    public List<String> getOpenJeopResponsibilities() {
        return openJeopResponsibilities;
    }

    public void setOpenJeopResponsibilities(final List<String> openJeopResponsibilities) {
        this.openJeopResponsibilities = openJeopResponsibilities;
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

    public List<String> getServiceBilledTo() {
        return serviceBilledTo;
    }

    public void setServiceBilledTo(final List<String> serviceBilledTo) {
        this.serviceBilledTo = serviceBilledTo;
    }
}
