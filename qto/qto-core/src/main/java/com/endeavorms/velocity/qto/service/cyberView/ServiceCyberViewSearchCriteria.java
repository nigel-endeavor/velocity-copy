package com.endeavorms.velocity.qto.service.cyberView;

import com.endeavorms.velocity.qto.common.BaseSearchCriteria;
import com.endeavorms.velocity.qto.common.DateRangeType;
import com.endeavorms.velocity.qto.common.RangeType;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class ServiceCyberViewSearchCriteria extends BaseSearchCriteria<ServiceCyberView> {
    private String search;

    private boolean hideTerminalStatuses;

    private boolean macOnly;

    private Long serviceId;

    private Long locationId;

    private List<RangeType> locationIdRange;

    private String clientLocationId;

    private String clientLocationInfo;

    private List<String> parentCompanyName;

    private String parentCompanyClientId;

    private Long companyId;

    private List<String> companyName;

    private String endCustomerClientId;

    private List<String> type;

    private String address;

    private String address1;

    private String address2;

    private String city;

    private String stateProvince;

    private String postalCode;

    private String status;

    private List<Date> followUpDate;

    private List<DateRangeType> followUpDateRange;

    private List<String> provisioner;

    private String i90ProjectManager;

    private List<String> provider;

    private Long statusAge;

    private List<RangeType> statusAgeRange;

    private List<BigDecimal> mrc;

    private List<RangeType> mrcRange;

    private List<BigDecimal> nrc;

    private List<RangeType> nrcRange;

    private List<BigDecimal> mrr;

    private List<RangeType> mrrRange;

    private List<BigDecimal> nrr;

    private List<RangeType> nrrRange;

    private boolean activeOnly;

    private String activeInactive;

    private String linkBundleType;

    private String linkBundleFrom;

    private List<String> equipmentTypes;

    private List<Long> equipmentCount;

    private List<RangeType> equipmentCountRange;

    private List<Date> customerRequestedInstall;

    private List<DateRangeType> customerRequestedInstallRange;

    private List<Date> created;

    private List<DateRangeType> createdRange;

    private List<Date> techDataGatheringFormSent;

    private List<DateRangeType> techDataGatheringFormSentRange;

    private List<Date> techDataGatheringMeetingScheduled;

    private List<DateRangeType> techDataGatheringMeetingScheduledRange;

    private List<Date> techDataGatheringMeetingCompleted;

    private List<DateRangeType> techDataGatheringMeetingCompletedRange;

    private List<Date> emailUsmAnywhereTemplateRequirements;

    private List<DateRangeType> emailUsmAnywhereTemplateRequirementsRange;

    private List<Date> inventoryAssignmentVerified;

    private List<DateRangeType> inventoryAssignmentVerifiedRange;

    private List<Date> newUsmAnywhereServerBuild;

    private List<DateRangeType> newUsmAnywhereServerBuildRange;

    private List<Date> implementationQa;

    private List<DateRangeType> implementationQaRange;

    private List<Date> verifyAssetsInSiemDb;

    private List<DateRangeType> verifyAssetsInSiemDbRange;

    private List<Date> verifyLoggingDataSource;

    private List<DateRangeType> verifyLoggingDataSourceRange;

    private List<Date> scheduleVulnerabilityScans;

    private List<DateRangeType> scheduleVulnerabilityScansRange;

    private List<Date> bulkAlarmTuningPhase;

    private List<DateRangeType> bulkAlarmTuningPhaseRange;

    private List<Date> siemEventFiltering;

    private List<DateRangeType> siemEventFilteringRange;

    private List<Date> filtersBuiltForReports;

    private List<DateRangeType> filtersBuiltForReportsRange;

    private List<Date> defaultAlarmRuleAdditions;

    private List<DateRangeType> defaultAlarmRuleAdditionsRange;

    private List<Date> customAlarmRuleAdditions;

    private List<DateRangeType> customAlarmRuleAdditionsRange;

    private List<Date> forwardAlarmsToUsmCentral;

    private List<DateRangeType> forwardAlarmsToUsmCentralRange;

    private List<Date> forwardAlarmsToD3SocLive;

    private List<DateRangeType> forwardAlarmsToD3SocLiveRange;

    private List<Date> hostListProvidedByClient;

    private List<DateRangeType> hostListProvidedByClientRange;

    private List<Date> halcyonPackageGivenToClient;

    private List<DateRangeType> halcyonPackageGivenToClientRange;

    private List<Date> halcyonDeployedToHosts;

    private List<DateRangeType> halcyonDeployedToHostsRange;

    private List<Date> devopsNotifiedOfHalcyonAddition;

    private List<DateRangeType> devopsNotifiedOfHalcyonAdditionRange;

    private List<Date> halcyonApiTokenAddedToD3;

    private List<DateRangeType> halcyonApiTokenAddedToD3Range;

    private List<Date> d3ConnectionVerified;

    private List<DateRangeType> d3ConnectionVerifiedRange;

    private List<Date> endLearningMode;

    private List<DateRangeType> endLearningModeRange;

    private List<Date> testEmailSentToClient;

    private List<DateRangeType> testEmailSentToClientRange;

    private List<Date> deployConsultingTenant;

    private List<DateRangeType> deployConsultingTenantRange;

    private List<Date> deployVulnerabilityScans;

    private List<DateRangeType> deployVulnerabilityScansRange;

    private List<Date> providedCustomerWithReport;

    private List<DateRangeType> providedCustomerWithReportRange;

    private List<Date> discussFutureCyrismaManagement;

    private List<DateRangeType> discussFutureCyrismaManagementRange;

    private List<Date> reviewExistingCaAndMfaPolicies;

    private List<DateRangeType> reviewExistingCaAndMfaPoliciesRange;

    private List<Date> signInPoliciesEnabled;

    private List<DateRangeType> signInPoliciesEnabledRange;

    private List<Date> conditionalAccessPolicyVerification;

    private List<DateRangeType> conditionalAccessPolicyVerificationRange;

    private List<Date> geographicRestrictionsEnabled;

    private List<DateRangeType> geographicRestrictionsEnabledRange;

    private List<Date> deviceComplianceEnabled;

    private List<DateRangeType> deviceComplianceEnabledRange;

    private List<Date> passwordResetEnabledForSelfService;

    private List<DateRangeType> passwordResetEnabledForSelfServiceRange;

    private List<Date> breakGlassAccountConfigured;

    private List<DateRangeType> breakGlassAccountConfiguredRange;

    private List<Date> pimEnablement;

    private List<DateRangeType> pimEnablementRange;

    private List<Date> implementationVerified;

    private List<DateRangeType> implementationVerifiedRange;

    public String getSearch() {
        return search;
    }

    public void setSearch(final String search) {
        this.search = search;
    }

    public boolean getHideTerminalStatuses() {
        return hideTerminalStatuses;
    }

    public void setHideTerminalStatuses(final boolean hideTerminalStatuses) {
        this.hideTerminalStatuses = hideTerminalStatuses;
    }

    public boolean isMacOnly() {
        return macOnly;
    }

    public void setMacOnly(final boolean macOnly) {
        this.macOnly = macOnly;
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

    public String getClientLocationInfo() {
        return clientLocationInfo;
    }

    public void setClientLocationInfo(final String clientLocationInfo) {
        this.clientLocationInfo = clientLocationInfo;
    }

    public List<String> getParentCompanyName() {
        return parentCompanyName;
    }

    public void setParentCompanyName(final List<String> parentCompanyName) {
        this.parentCompanyName = parentCompanyName;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(final Long companyId) {
        this.companyId = companyId;
    }

    public List<String> getCompanyName() {
        return companyName;
    }

    public void setCompanyName(final List<String> companyName) {
        this.companyName = companyName;
    }

    public List<String> getType() {
        return type;
    }

    public void setType(final List<String> type) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
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

    public List<String> getProvisioner() {
        return provisioner;
    }

    public void setProvisioner(final List<String> provisioner) {
        this.provisioner = provisioner;
    }

    public String getI90ProjectManager() {
        return i90ProjectManager;
    }

    public void setI90ProjectManager(final String i90ProjectManager) {
        this.i90ProjectManager = i90ProjectManager;
    }

    public List<String> getProvider() {
        return provider;
    }

    public void setProvider(final List<String> provider) {
        this.provider = provider;
    }

    public Long getStatusAge() {
        return statusAge;
    }

    public void setStatusAge(final Long statusAge) {
        this.statusAge = statusAge;
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

    public boolean isActiveOnly() {
        return activeOnly;
    }

    public void setActiveOnly(final boolean activeOnly) {
        this.activeOnly = activeOnly;
    }

    public String getActiveInactive() {
        return activeInactive;
    }

    public void setActiveInactive(final String activeInactive) {
        this.activeInactive = activeInactive;
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

    public List<Long> getEquipmentCount() {
        return equipmentCount;
    }

    public void setEquipmentCount(final List<Long> equipmentCount) {
        this.equipmentCount = equipmentCount;
    }

    public List<RangeType> getEquipmentCountRange() {
        return equipmentCountRange;
    }

    public void setEquipmentCountRange(final List<RangeType> equipmentCountRange) {
        this.equipmentCountRange = equipmentCountRange;
    }

    public List<String> getEquipmentTypes() {
        return equipmentTypes;
    }

    public void setEquipmentTypes(final List<String> equipmentTypes) {
        this.equipmentTypes = equipmentTypes;
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

    public List<Date> getTechDataGatheringFormSent() {
        return techDataGatheringFormSent;
    }

    public void setTechDataGatheringFormSent(final List<Date> techDataGatheringFormSent) {
        this.techDataGatheringFormSent = techDataGatheringFormSent;
    }

    public List<DateRangeType> getTechDataGatheringFormSentRange() {
        return techDataGatheringFormSentRange;
    }

    public void setTechDataGatheringFormSentRange(final List<DateRangeType> techDataGatheringFormSentRange) {
        this.techDataGatheringFormSentRange = techDataGatheringFormSentRange;
    }

    public List<Date> getTechDataGatheringMeetingScheduled() {
        return techDataGatheringMeetingScheduled;
    }

    public void setTechDataGatheringMeetingScheduled(final List<Date> techDataGatheringMeetingScheduled) {
        this.techDataGatheringMeetingScheduled = techDataGatheringMeetingScheduled;
    }

    public List<DateRangeType> getTechDataGatheringMeetingScheduledRange() {
        return techDataGatheringMeetingScheduledRange;
    }

    public void setTechDataGatheringMeetingScheduledRange(final List<DateRangeType> techDataGatheringMeetingScheduledRange) {
        this.techDataGatheringMeetingScheduledRange = techDataGatheringMeetingScheduledRange;
    }

    public List<Date> getTechDataGatheringMeetingCompleted() {
        return techDataGatheringMeetingCompleted;
    }

    public void setTechDataGatheringMeetingCompleted(final List<Date> techDataGatheringMeetingCompleted) {
        this.techDataGatheringMeetingCompleted = techDataGatheringMeetingCompleted;
    }

    public List<DateRangeType> getTechDataGatheringMeetingCompletedRange() {
        return techDataGatheringMeetingCompletedRange;
    }

    public void setTechDataGatheringMeetingCompletedRange(final List<DateRangeType> techDataGatheringMeetingCompletedRange) {
        this.techDataGatheringMeetingCompletedRange = techDataGatheringMeetingCompletedRange;
    }

    public List<Date> getEmailUsmAnywhereTemplateRequirements() {
        return emailUsmAnywhereTemplateRequirements;
    }

    public void setEmailUsmAnywhereTemplateRequirements(final List<Date> emailUsmAnywhereTemplateRequirements) {
        this.emailUsmAnywhereTemplateRequirements = emailUsmAnywhereTemplateRequirements;
    }

    public List<DateRangeType> getEmailUsmAnywhereTemplateRequirementsRange() {
        return emailUsmAnywhereTemplateRequirementsRange;
    }

    public void setEmailUsmAnywhereTemplateRequirementsRange(final List<DateRangeType> emailUsmAnywhereTemplateRequirementsRange) {
        this.emailUsmAnywhereTemplateRequirementsRange = emailUsmAnywhereTemplateRequirementsRange;
    }

    public List<Date> getInventoryAssignmentVerified() {
        return inventoryAssignmentVerified;
    }

    public void setInventoryAssignmentVerified(final List<Date> inventoryAssignmentVerified) {
        this.inventoryAssignmentVerified = inventoryAssignmentVerified;
    }

    public List<DateRangeType> getInventoryAssignmentVerifiedRange() {
        return inventoryAssignmentVerifiedRange;
    }

    public void setInventoryAssignmentVerifiedRange(final List<DateRangeType> inventoryAssignmentVerifiedRange) {
        this.inventoryAssignmentVerifiedRange = inventoryAssignmentVerifiedRange;
    }

    public List<Date> getNewUsmAnywhereServerBuild() {
        return newUsmAnywhereServerBuild;
    }

    public void setNewUsmAnywhereServerBuild(final List<Date> newUsmAnywhereServerBuild) {
        this.newUsmAnywhereServerBuild = newUsmAnywhereServerBuild;
    }

    public List<DateRangeType> getNewUsmAnywhereServerBuildRange() {
        return newUsmAnywhereServerBuildRange;
    }

    public void setNewUsmAnywhereServerBuildRange(final List<DateRangeType> newUsmAnywhereServerBuildRange) {
        this.newUsmAnywhereServerBuildRange = newUsmAnywhereServerBuildRange;
    }

    public List<Date> getImplementationQa() {
        return implementationQa;
    }

    public void setImplementationQa(final List<Date> implementationQa) {
        this.implementationQa = implementationQa;
    }

    public List<DateRangeType> getImplementationQaRange() {
        return implementationQaRange;
    }

    public void setImplementationQaRange(final List<DateRangeType> implementationQaRange) {
        this.implementationQaRange = implementationQaRange;
    }

    public List<Date> getVerifyAssetsInSiemDb() {
        return verifyAssetsInSiemDb;
    }

    public void setVerifyAssetsInSiemDb(final List<Date> verifyAssetsInSiemDb) {
        this.verifyAssetsInSiemDb = verifyAssetsInSiemDb;
    }

    public List<DateRangeType> getVerifyAssetsInSiemDbRange() {
        return verifyAssetsInSiemDbRange;
    }

    public void setVerifyAssetsInSiemDbRange(final List<DateRangeType> verifyAssetsInSiemDbRange) {
        this.verifyAssetsInSiemDbRange = verifyAssetsInSiemDbRange;
    }

    public List<Date> getVerifyLoggingDataSource() {
        return verifyLoggingDataSource;
    }

    public void setVerifyLoggingDataSource(final List<Date> verifyLoggingDataSource) {
        this.verifyLoggingDataSource = verifyLoggingDataSource;
    }

    public List<DateRangeType> getVerifyLoggingDataSourceRange() {
        return verifyLoggingDataSourceRange;
    }

    public void setVerifyLoggingDataSourceRange(final List<DateRangeType> verifyLoggingDataSourceRange) {
        this.verifyLoggingDataSourceRange = verifyLoggingDataSourceRange;
    }

    public List<Date> getScheduleVulnerabilityScans() {
        return scheduleVulnerabilityScans;
    }

    public void setScheduleVulnerabilityScans(final List<Date> scheduleVulnerabilityScans) {
        this.scheduleVulnerabilityScans = scheduleVulnerabilityScans;
    }

    public List<DateRangeType> getScheduleVulnerabilityScansRange() {
        return scheduleVulnerabilityScansRange;
    }

    public void setScheduleVulnerabilityScansRange(final List<DateRangeType> scheduleVulnerabilityScansRange) {
        this.scheduleVulnerabilityScansRange = scheduleVulnerabilityScansRange;
    }

    public List<Date> getBulkAlarmTuningPhase() {
        return bulkAlarmTuningPhase;
    }

    public void setBulkAlarmTuningPhase(final List<Date> bulkAlarmTuningPhase) {
        this.bulkAlarmTuningPhase = bulkAlarmTuningPhase;
    }

    public List<DateRangeType> getBulkAlarmTuningPhaseRange() {
        return bulkAlarmTuningPhaseRange;
    }

    public void setBulkAlarmTuningPhaseRange(final List<DateRangeType> bulkAlarmTuningPhaseRange) {
        this.bulkAlarmTuningPhaseRange = bulkAlarmTuningPhaseRange;
    }

    public List<Date> getSiemEventFiltering() {
        return siemEventFiltering;
    }

    public void setSiemEventFiltering(final List<Date> siemEventFiltering) {
        this.siemEventFiltering = siemEventFiltering;
    }

    public List<DateRangeType> getSiemEventFilteringRange() {
        return siemEventFilteringRange;
    }

    public void setSiemEventFilteringRange(final List<DateRangeType> siemEventFilteringRange) {
        this.siemEventFilteringRange = siemEventFilteringRange;
    }

    public List<Date> getFiltersBuiltForReports() {
        return filtersBuiltForReports;
    }

    public void setFiltersBuiltForReports(final List<Date> filtersBuiltForReports) {
        this.filtersBuiltForReports = filtersBuiltForReports;
    }

    public List<DateRangeType> getFiltersBuiltForReportsRange() {
        return filtersBuiltForReportsRange;
    }

    public void setFiltersBuiltForReportsRange(final List<DateRangeType> filtersBuiltForReportsRange) {
        this.filtersBuiltForReportsRange = filtersBuiltForReportsRange;
    }

    public List<Date> getDefaultAlarmRuleAdditions() {
        return defaultAlarmRuleAdditions;
    }

    public void setDefaultAlarmRuleAdditions(final List<Date> defaultAlarmRuleAdditions) {
        this.defaultAlarmRuleAdditions = defaultAlarmRuleAdditions;
    }

    public List<DateRangeType> getDefaultAlarmRuleAdditionsRange() {
        return defaultAlarmRuleAdditionsRange;
    }

    public void setDefaultAlarmRuleAdditionsRange(final List<DateRangeType> defaultAlarmRuleAdditionsRange) {
        this.defaultAlarmRuleAdditionsRange = defaultAlarmRuleAdditionsRange;
    }

    public List<Date> getCustomAlarmRuleAdditions() {
        return customAlarmRuleAdditions;
    }

    public void setCustomAlarmRuleAdditions(final List<Date> customAlarmRuleAdditions) {
        this.customAlarmRuleAdditions = customAlarmRuleAdditions;
    }

    public List<DateRangeType> getCustomAlarmRuleAdditionsRange() {
        return customAlarmRuleAdditionsRange;
    }

    public void setCustomAlarmRuleAdditionsRange(final List<DateRangeType> customAlarmRuleAdditionsRange) {
        this.customAlarmRuleAdditionsRange = customAlarmRuleAdditionsRange;
    }

    public List<Date> getForwardAlarmsToUsmCentral() {
        return forwardAlarmsToUsmCentral;
    }

    public void setForwardAlarmsToUsmCentral(final List<Date> forwardAlarmsToUsmCentral) {
        this.forwardAlarmsToUsmCentral = forwardAlarmsToUsmCentral;
    }

    public List<DateRangeType> getForwardAlarmsToUsmCentralRange() {
        return forwardAlarmsToUsmCentralRange;
    }

    public void setForwardAlarmsToUsmCentralRange(final List<DateRangeType> forwardAlarmsToUsmCentralRange) {
        this.forwardAlarmsToUsmCentralRange = forwardAlarmsToUsmCentralRange;
    }

    public List<Date> getForwardAlarmsToD3SocLive() {
        return forwardAlarmsToD3SocLive;
    }

    public void setForwardAlarmsToD3SocLive(final List<Date> forwardAlarmsToD3SocLive) {
        this.forwardAlarmsToD3SocLive = forwardAlarmsToD3SocLive;
    }

    public List<DateRangeType> getForwardAlarmsToD3SocLiveRange() {
        return forwardAlarmsToD3SocLiveRange;
    }

    public void setForwardAlarmsToD3SocLiveRange(final List<DateRangeType> forwardAlarmsToD3SocLiveRange) {
        this.forwardAlarmsToD3SocLiveRange = forwardAlarmsToD3SocLiveRange;
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

    public List<Date> getHostListProvidedByClient() {
        return hostListProvidedByClient;
    }

    public void setHostListProvidedByClient(final List<Date> hostListProvidedByClient) {
        this.hostListProvidedByClient = hostListProvidedByClient;
    }

    public List<DateRangeType> getHostListProvidedByClientRange() {
        return hostListProvidedByClientRange;
    }

    public void setHostListProvidedByClientRange(final List<DateRangeType> hostListProvidedByClientRange) {
        this.hostListProvidedByClientRange = hostListProvidedByClientRange;
    }

    public List<Date> getHalcyonPackageGivenToClient() {
        return halcyonPackageGivenToClient;
    }

    public void setHalcyonPackageGivenToClient(final List<Date> halcyonPackageGivenToClient) {
        this.halcyonPackageGivenToClient = halcyonPackageGivenToClient;
    }

    public List<DateRangeType> getHalcyonPackageGivenToClientRange() {
        return halcyonPackageGivenToClientRange;
    }

    public void setHalcyonPackageGivenToClientRange(final List<DateRangeType> halcyonPackageGivenToClientRange) {
        this.halcyonPackageGivenToClientRange = halcyonPackageGivenToClientRange;
    }

    public List<Date> getHalcyonDeployedToHosts() {
        return halcyonDeployedToHosts;
    }

    public void setHalcyonDeployedToHosts(final List<Date> halcyonDeployedToHosts) {
        this.halcyonDeployedToHosts = halcyonDeployedToHosts;
    }

    public List<DateRangeType> getHalcyonDeployedToHostsRange() {
        return halcyonDeployedToHostsRange;
    }

    public void setHalcyonDeployedToHostsRange(final List<DateRangeType> halcyonDeployedToHostsRange) {
        this.halcyonDeployedToHostsRange = halcyonDeployedToHostsRange;
    }

    public List<Date> getDevopsNotifiedOfHalcyonAddition() {
        return devopsNotifiedOfHalcyonAddition;
    }

    public void setDevopsNotifiedOfHalcyonAddition(final List<Date> devopsNotifiedOfHalcyonAddition) {
        this.devopsNotifiedOfHalcyonAddition = devopsNotifiedOfHalcyonAddition;
    }

    public List<DateRangeType> getDevopsNotifiedOfHalcyonAdditionRange() {
        return devopsNotifiedOfHalcyonAdditionRange;
    }

    public void setDevopsNotifiedOfHalcyonAdditionRange(final List<DateRangeType> devopsNotifiedOfHalcyonAdditionRange) {
        this.devopsNotifiedOfHalcyonAdditionRange = devopsNotifiedOfHalcyonAdditionRange;
    }

    public List<Date> getHalcyonApiTokenAddedToD3() {
        return halcyonApiTokenAddedToD3;
    }

    public void setHalcyonApiTokenAddedToD3(final List<Date> halcyonApiTokenAddedToD3) {
        this.halcyonApiTokenAddedToD3 = halcyonApiTokenAddedToD3;
    }

    public List<DateRangeType> getHalcyonApiTokenAddedToD3Range() {
        return halcyonApiTokenAddedToD3Range;
    }

    public void setHalcyonApiTokenAddedToD3Range(final List<DateRangeType> halcyonApiTokenAddedToD3Range) {
        this.halcyonApiTokenAddedToD3Range = halcyonApiTokenAddedToD3Range;
    }

    public List<Date> getD3ConnectionVerified() {
        return d3ConnectionVerified;
    }

    public void setD3ConnectionVerified(final List<Date> d3ConnectionVerified) {
        this.d3ConnectionVerified = d3ConnectionVerified;
    }

    public List<DateRangeType> getD3ConnectionVerifiedRange() {
        return d3ConnectionVerifiedRange;
    }

    public void setD3ConnectionVerifiedRange(final List<DateRangeType> d3ConnectionVerifiedRange) {
        this.d3ConnectionVerifiedRange = d3ConnectionVerifiedRange;
    }

    public List<Date> getEndLearningMode() {
        return endLearningMode;
    }

    public void setEndLearningMode(final List<Date> endLearningMode) {
        this.endLearningMode = endLearningMode;
    }

    public List<DateRangeType> getEndLearningModeRange() {
        return endLearningModeRange;
    }

    public void setEndLearningModeRange(final List<DateRangeType> endLearningModeRange) {
        this.endLearningModeRange = endLearningModeRange;
    }

    public List<Date> getTestEmailSentToClient() {
        return testEmailSentToClient;
    }

    public void setTestEmailSentToClient(final List<Date> testEmailSentToClient) {
        this.testEmailSentToClient = testEmailSentToClient;
    }

    public List<DateRangeType> getTestEmailSentToClientRange() {
        return testEmailSentToClientRange;
    }

    public void setTestEmailSentToClientRange(final List<DateRangeType> testEmailSentToClientRange) {
        this.testEmailSentToClientRange = testEmailSentToClientRange;
    }

    public List<Date> getDeployConsultingTenant() {
        return deployConsultingTenant;
    }

    public void setDeployConsultingTenant(final List<Date> deployConsultingTenant) {
        this.deployConsultingTenant = deployConsultingTenant;
    }

    public List<DateRangeType> getDeployConsultingTenantRange() {
        return deployConsultingTenantRange;
    }

    public void setDeployConsultingTenantRange(final List<DateRangeType> deployConsultingTenantRange) {
        this.deployConsultingTenantRange = deployConsultingTenantRange;
    }

    public List<Date> getDeployVulnerabilityScans() {
        return deployVulnerabilityScans;
    }

    public void setDeployVulnerabilityScans(final List<Date> deployVulnerabilityScans) {
        this.deployVulnerabilityScans = deployVulnerabilityScans;
    }

    public List<DateRangeType> getDeployVulnerabilityScansRange() {
        return deployVulnerabilityScansRange;
    }

    public void setDeployVulnerabilityScansRange(final List<DateRangeType> deployVulnerabilityScansRange) {
        this.deployVulnerabilityScansRange = deployVulnerabilityScansRange;
    }

    public List<Date> getProvidedCustomerWithReport() {
        return providedCustomerWithReport;
    }

    public void setProvidedCustomerWithReport(final List<Date> providedCustomerWithReport) {
        this.providedCustomerWithReport = providedCustomerWithReport;
    }

    public List<DateRangeType> getProvidedCustomerWithReportRange() {
        return providedCustomerWithReportRange;
    }

    public void setProvidedCustomerWithReportRange(final List<DateRangeType> providedCustomerWithReportRange) {
        this.providedCustomerWithReportRange = providedCustomerWithReportRange;
    }

    public List<Date> getDiscussFutureCyrismaManagement() {
        return discussFutureCyrismaManagement;
    }

    public void setDiscussFutureCyrismaManagement(final List<Date> discussFutureCyrismaManagement) {
        this.discussFutureCyrismaManagement = discussFutureCyrismaManagement;
    }

    public List<DateRangeType> getDiscussFutureCyrismaManagementRange() {
        return discussFutureCyrismaManagementRange;
    }

    public void setDiscussFutureCyrismaManagementRange(final List<DateRangeType> discussFutureCyrismaManagementRange) {
        this.discussFutureCyrismaManagementRange = discussFutureCyrismaManagementRange;
    }

    public List<Date> getReviewExistingCaAndMfaPolicies() {
        return reviewExistingCaAndMfaPolicies;
    }

    public void setReviewExistingCaAndMfaPolicies(final List<Date> reviewExistingCaAndMfaPolicies) {
        this.reviewExistingCaAndMfaPolicies = reviewExistingCaAndMfaPolicies;
    }

    public List<DateRangeType> getReviewExistingCaAndMfaPoliciesRange() {
        return reviewExistingCaAndMfaPoliciesRange;
    }

    public void setReviewExistingCaAndMfaPoliciesRange(final List<DateRangeType> reviewExistingCaAndMfaPoliciesRange) {
        this.reviewExistingCaAndMfaPoliciesRange = reviewExistingCaAndMfaPoliciesRange;
    }

    public List<Date> getSignInPoliciesEnabled() {
        return signInPoliciesEnabled;
    }

    public void setSignInPoliciesEnabled(final List<Date> signInPoliciesEnabled) {
        this.signInPoliciesEnabled = signInPoliciesEnabled;
    }

    public List<DateRangeType> getSignInPoliciesEnabledRange() {
        return signInPoliciesEnabledRange;
    }

    public void setSignInPoliciesEnabledRange(final List<DateRangeType> signInPoliciesEnabledRange) {
        this.signInPoliciesEnabledRange = signInPoliciesEnabledRange;
    }

    public List<Date> getConditionalAccessPolicyVerification() {
        return conditionalAccessPolicyVerification;
    }

    public void setConditionalAccessPolicyVerification(final List<Date> conditionalAccessPolicyVerification) {
        this.conditionalAccessPolicyVerification = conditionalAccessPolicyVerification;
    }

    public List<DateRangeType> getConditionalAccessPolicyVerificationRange() {
        return conditionalAccessPolicyVerificationRange;
    }

    public void setConditionalAccessPolicyVerificationRange(final List<DateRangeType> conditionalAccessPolicyVerificationRange) {
        this.conditionalAccessPolicyVerificationRange = conditionalAccessPolicyVerificationRange;
    }

    public List<Date> getGeographicRestrictionsEnabled() {
        return geographicRestrictionsEnabled;
    }

    public void setGeographicRestrictionsEnabled(final List<Date> geographicRestrictionsEnabled) {
        this.geographicRestrictionsEnabled = geographicRestrictionsEnabled;
    }

    public List<DateRangeType> getGeographicRestrictionsEnabledRange() {
        return geographicRestrictionsEnabledRange;
    }

    public void setGeographicRestrictionsEnabledRange(final List<DateRangeType> geographicRestrictionsEnabledRange) {
        this.geographicRestrictionsEnabledRange = geographicRestrictionsEnabledRange;
    }

    public List<Date> getDeviceComplianceEnabled() {
        return deviceComplianceEnabled;
    }

    public void setDeviceComplianceEnabled(final List<Date> deviceComplianceEnabled) {
        this.deviceComplianceEnabled = deviceComplianceEnabled;
    }

    public List<DateRangeType> getDeviceComplianceEnabledRange() {
        return deviceComplianceEnabledRange;
    }

    public void setDeviceComplianceEnabledRange(final List<DateRangeType> deviceComplianceEnabledRange) {
        this.deviceComplianceEnabledRange = deviceComplianceEnabledRange;
    }

    public List<Date> getPasswordResetEnabledForSelfService() {
        return passwordResetEnabledForSelfService;
    }

    public void setPasswordResetEnabledForSelfService(final List<Date> passwordResetEnabledForSelfService) {
        this.passwordResetEnabledForSelfService = passwordResetEnabledForSelfService;
    }

    public List<DateRangeType> getPasswordResetEnabledForSelfServiceRange() {
        return passwordResetEnabledForSelfServiceRange;
    }

    public void setPasswordResetEnabledForSelfServiceRange(final List<DateRangeType> passwordResetEnabledForSelfServiceRange) {
        this.passwordResetEnabledForSelfServiceRange = passwordResetEnabledForSelfServiceRange;
    }

    public List<Date> getBreakGlassAccountConfigured() {
        return breakGlassAccountConfigured;
    }

    public void setBreakGlassAccountConfigured(final List<Date> breakGlassAccountConfigured) {
        this.breakGlassAccountConfigured = breakGlassAccountConfigured;
    }

    public List<DateRangeType> getBreakGlassAccountConfiguredRange() {
        return breakGlassAccountConfiguredRange;
    }

    public void setBreakGlassAccountConfiguredRange(final List<DateRangeType> breakGlassAccountConfiguredRange) {
        this.breakGlassAccountConfiguredRange = breakGlassAccountConfiguredRange;
    }

    public List<Date> getPimEnablement() {
        return pimEnablement;
    }

    public void setPimEnablement(final List<Date> pimEnablement) {
        this.pimEnablement = pimEnablement;
    }

    public List<DateRangeType> getPimEnablementRange() {
        return pimEnablementRange;
    }

    public void setPimEnablementRange(final List<DateRangeType> pimEnablementRange) {
        this.pimEnablementRange = pimEnablementRange;
    }

    public List<Date> getImplementationVerified() {
        return implementationVerified;
    }

    public void setImplementationVerified(final List<Date> implementationVerified) {
        this.implementationVerified = implementationVerified;
    }

    public List<DateRangeType> getImplementationVerifiedRange() {
        return implementationVerifiedRange;
    }

    public void setImplementationVerifiedRange(final List<DateRangeType> implementationVerifiedRange) {
        this.implementationVerifiedRange = implementationVerifiedRange;
    }
}
