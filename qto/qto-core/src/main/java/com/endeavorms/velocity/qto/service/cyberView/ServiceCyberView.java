package com.endeavorms.velocity.qto.service.cyberView;

import com.endeavorms.velocity.qto.common.AbstractMasterCustomerOwnedEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "v_manage_cyber_services")
public class ServiceCyberView extends AbstractMasterCustomerOwnedEntity {

    @Id
    @Column(name = "service_id")
    private Long id;

    @Column(name = "location_id")
    private Long locationId;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "client_location_id")
    private String clientLocationId;

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

    @Column(name = "service_type")
    private String type;

    @Column(name = "address")
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

    @Column(name = "service_status")
    private String status;

    @Column(name = "follow_up_date")
    private Date followUpDate;

    @Column(name = "provisioner")
    private String provisioner;

    @Column(name = "i90_project_manager")
    private String i90ProjectManager;

    @Column(name = "progress_percentage")
    private Long progressPercentage;

    @Column(name = "provider")
    private String provider;

    @Column(name = "status_age")
    private Long statusAge;

    @Column(name = "service_mrc")
    private BigDecimal mrc;

    @Column(name = "service_mrr")
    private BigDecimal mrr;

    @Column(name = "service_nrr")
    private BigDecimal nrr;

    @Column(name = "service_nrc")
    private BigDecimal nrc;

    @Column(name = "greatest_milestone_name")
    private String greatestMilestoneName;

    @Column(name = "greatest_milestone_date")
    private Date greatestMilestoneDate;

    @Column(name = "active")
    private boolean active;

    @Column(name = "latest_note")
    private String latestNote;

    @Column(name = "linked")
    private Boolean linked;

    @Column(name = "bundled")
    private Boolean bundled;

    @Column(name = "linked_bundled_parent")
    private Boolean linkedBundledParent;

    @Column(name = "linked_bundled_parent_id")
    private Long linkedBundledParentId;

    @Column(name = "equipment_count")
    private Long equipmentCount;

    @Column(name = "equipment_types")
    private String equipmentTypes;

    @Column(name = "customer_requested_install")
    private Date customerRequestedInstall;

    @Column(name = "created")
    private Date created;

    @Column(name = "tech_data_gathering_form_sent")
    private Date techDataGatheringFormSent;

    @Column(name = "tech_data_gathering_meeting_scheduled")
    private Date techDataGatheringMeetingScheduled;

    @Column(name = "tech_data_gathering_meeting_completed")
    private Date techDataGatheringMeetingCompleted;

    @Column(name = "email_usm_anywhere_template_requirements")
    private Date emailUsmAnywhereTemplateRequirements;

    @Column(name = "inventory_assignment_verified")
    private Date inventoryAssignmentVerified;

    @Column(name = "new_usm_anywhere_server_build")
    private Date newUsmAnywhereServerBuild;

    @Column(name = "implementation_qa")
    private Date implementationQa;

    @Column(name = "verify_assets_in_siem_db")
    private Date verifyAssetsInSiemDb;

    @Column(name = "verify_logging_data_source")
    private Date verifyLoggingDataSource;

    @Column(name = "schedule_vulnerability_scans")
    private Date scheduleVulnerabilityScans;

    @Column(name = "bulk_alarm_tuning_phase")
    private Date bulkAlarmTuningPhase;

    @Column(name = "siem_event_filtering")
    private Date siemEventFiltering;

    @Column(name = "filters_built_for_reports")
    private Date filtersBuiltForReports;

    @Column(name = "default_alarm_rule_additions")
    private Date defaultAlarmRuleAdditions;

    @Column(name = "custom_alarm_rule_additions")
    private Date customAlarmRuleAdditions;

    @Column(name = "forward_alarms_to_usm_central")
    private Date forwardAlarmsToUsmCentral;

    @Column(name = "forward_alarms_to_d3Soc_live")
    private Date forwardAlarmsToD3SocLive;

    @Column(name = "order_type")
    private String orderType;

    @Column(name = "host_list_provided_by_client")
    private Date hostListProvidedByClient;

    @Column(name = "halcyon_package_given_to_client")
    private Date halcyonPackageGivenToClient;

    @Column(name = "halcyon_deployed_to_hosts")
    private Date halcyonDeployedToHosts;

    @Column(name = "devops_notified_of_halcyon_addition")
    private Date devopsNotifiedOfHalcyonAddition;

    @Column(name = "halcyon_api_token_added_to_d3")
    private Date halcyonApiTokenAddedToD3;

    @Column(name = "d3_connection_verified")
    private Date d3ConnectionVerified;

    @Column(name = "end_learning_mode")
    private Date endLearningMode;

    @Column(name = "test_email_sent_to_client")
    private Date testEmailSentToClient;

    @Column(name = "deploy_consulting_tenant")
    private Date deployConsultingTenant;

    @Column(name = "deploy_vulnerability_scans")
    private Date deployVulnerabilityScans;

    @Column(name = "provided_customer_with_report")
    private Date providedCustomerWithReport;

    @Column(name = "discuss_future_cyrisma_management")
    private Date discussFutureCyrismaManagement;

    @Column(name = "review_existing_ca_and_mfa_policies")
    private Date reviewExistingCaAndMfaPolicies;

    @Column(name = "sign_in_policies_enabled")
    private Date signInPoliciesEnabled;

    @Column(name = "conditional_access_policy_verification")
    private Date conditionalAccessPolicyVerification;

    @Column(name = "geographic_restrictions_enabled")
    private Date geographicRestrictionsEnabled;

    @Column(name = "device_compliance_enabled")
    private Date deviceComplianceEnabled;

    @Column(name = "password_reset_enabled_for_self_service")
    private Date passwordResetEnabledForSelfService;

    @Column(name = "break_glass_account_configured")
    private Date breakGlassAccountConfigured;

    @Column(name = "pim_enablement")
    private Date pimEnablement;

    @Column(name = "implementation_verified")
    private Date implementationVerified;

    @Column(name = "show_jeop_icon")
    private boolean showJeopIcon;

    @Column(name = "show_note_icon")
    private boolean showNoteIcon;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public Date getFollowUpDate() {
        return followUpDate;
    }

    public void setFollowUpDate(final Date followUpDate) {
        this.followUpDate = followUpDate;
    }

    public String getProvisioner() {
        return provisioner;
    }

    public void setProvisioner(final String provisioner) {
        this.provisioner = provisioner;
    }

    public String getI90ProjectManager() {
        return i90ProjectManager;
    }

    public void setI90ProjectManager(final String i90ProjectManager) {
        this.i90ProjectManager = i90ProjectManager;
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

    public Long getStatusAge() {
        return statusAge;
    }

    public void setStatusAge(final Long statusAge) {
        this.statusAge = statusAge;
    }

    public BigDecimal getMrc() {
        return mrc;
    }

    public void setMrc(final BigDecimal mrc) {
        this.mrc = mrc;
    }

    public BigDecimal getNrc() {
        return nrc;
    }

    public void setNrc(final BigDecimal nrc) {
        this.nrc = nrc;
    }

    public BigDecimal getMrr() {
        return mrr;
    }

    public void setMrr(final BigDecimal mrr) {
        this.mrr = mrr;
    }

    public BigDecimal getNrr() {
        return nrr;
    }

    public void setNrr(final BigDecimal nrr) {
        this.nrr = nrr;
    }

    public String getGreatestMilestoneName() {
        return greatestMilestoneName;
    }

    public void setGreatestMilestoneName(final String greatestMilestoneName) {
        this.greatestMilestoneName = greatestMilestoneName;
    }

    public Date getGreatestMilestoneDate() {
        return greatestMilestoneDate;
    }

    public void setGreatestMilestoneDate(final Date greatestMilestoneDate) {
        this.greatestMilestoneDate = greatestMilestoneDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(final boolean active) {
        this.active = active;
    }

    public String getLatestNote() {
        return latestNote;
    }

    public void setLatestNote(final String latestNote) {
        this.latestNote = latestNote;
    }

    public Boolean getLinked() {
        return linked;
    }

    public void setLinked(final Boolean linked) {
        this.linked = linked;
    }

    public Boolean getBundled() {
        return bundled;
    }

    public void setBundled(final Boolean bundled) {
        this.bundled = bundled;
    }

    public Boolean getLinkedBundledParent() {
        return linkedBundledParent;
    }

    public void setLinkedBundledParent(final Boolean linkedBundledParent) {
        this.linkedBundledParent = linkedBundledParent;
    }

    public Long getLinkedBundledParentId() {
        return linkedBundledParentId;
    }

    public void setLinkedBundledParentId(final Long linkedBundledParentId) {
        this.linkedBundledParentId = linkedBundledParentId;
    }

    public Long getEquipmentCount() {
        return equipmentCount;
    }

    public void setEquipmentCount(final Long equipmentCount) {
        this.equipmentCount = equipmentCount;
    }

    public String getEquipmentTypes() {
        return equipmentTypes;
    }

    public void setEquipmentTypes(final String equipmentTypes) {
        this.equipmentTypes = equipmentTypes;
    }

    public Date getCustomerRequestedInstall() {
        return customerRequestedInstall;
    }

    public void setCustomerRequestedInstall(final Date customerRequestedInstall) {
        this.customerRequestedInstall = customerRequestedInstall;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(final Date created) {
        this.created = created;
    }

    public Date getTechDataGatheringFormSent() {
        return techDataGatheringFormSent;
    }

    public void setTechDataGatheringFormSent(final Date techDataGatheringFormSent) {
        this.techDataGatheringFormSent = techDataGatheringFormSent;
    }

    public Date getTechDataGatheringMeetingScheduled() {
        return techDataGatheringMeetingScheduled;
    }

    public void setTechDataGatheringMeetingScheduled(final Date techDataGatheringMeetingScheduled) {
        this.techDataGatheringMeetingScheduled = techDataGatheringMeetingScheduled;
    }

    public Date getTechDataGatheringMeetingCompleted() {
        return techDataGatheringMeetingCompleted;
    }

    public void setTechDataGatheringMeetingCompleted(final Date techDataGatheringMeetingCompleted) {
        this.techDataGatheringMeetingCompleted = techDataGatheringMeetingCompleted;
    }

    public Date getEmailUsmAnywhereTemplateRequirements() {
        return emailUsmAnywhereTemplateRequirements;
    }

    public void setEmailUsmAnywhereTemplateRequirements(final Date emailUsmAnywhereTemplateRequirements) {
        this.emailUsmAnywhereTemplateRequirements = emailUsmAnywhereTemplateRequirements;
    }

    public Date getInventoryAssignmentVerified() {
        return inventoryAssignmentVerified;
    }

    public void setInventoryAssignmentVerified(final Date inventoryAssignmentVerified) {
        this.inventoryAssignmentVerified = inventoryAssignmentVerified;
    }

    public Date getNewUsmAnywhereServerBuild() {
        return newUsmAnywhereServerBuild;
    }

    public void setNewUsmAnywhereServerBuild(final Date newUsmAnywhereServerBuild) {
        this.newUsmAnywhereServerBuild = newUsmAnywhereServerBuild;
    }

    public Date getImplementationQa() {
        return implementationQa;
    }

    public void setImplementationQa(final Date implementationQa) {
        this.implementationQa = implementationQa;
    }

    public Date getVerifyAssetsInSiemDb() {
        return verifyAssetsInSiemDb;
    }

    public void setVerifyAssetsInSiemDb(final Date verifyAssetsInSiemDb) {
        this.verifyAssetsInSiemDb = verifyAssetsInSiemDb;
    }

    public Date getVerifyLoggingDataSource() {
        return verifyLoggingDataSource;
    }

    public void setVerifyLoggingDataSource(final Date verifyLoggingDataSource) {
        this.verifyLoggingDataSource = verifyLoggingDataSource;
    }

    public Date getScheduleVulnerabilityScans() {
        return scheduleVulnerabilityScans;
    }

    public void setScheduleVulnerabilityScans(final Date scheduleVulnerabilityScans) {
        this.scheduleVulnerabilityScans = scheduleVulnerabilityScans;
    }

    public Date getBulkAlarmTuningPhase() {
        return bulkAlarmTuningPhase;
    }

    public void setBulkAlarmTuningPhase(final Date bulkAlarmTuningPhase) {
        this.bulkAlarmTuningPhase = bulkAlarmTuningPhase;
    }

    public Date getSiemEventFiltering() {
        return siemEventFiltering;
    }

    public void setSiemEventFiltering(final Date siemEventFiltering) {
        this.siemEventFiltering = siemEventFiltering;
    }

    public Date getFiltersBuiltForReports() {
        return filtersBuiltForReports;
    }

    public void setFiltersBuiltForReports(final Date filtersBuiltForReports) {
        this.filtersBuiltForReports = filtersBuiltForReports;
    }

    public Date getDefaultAlarmRuleAdditions() {
        return defaultAlarmRuleAdditions;
    }

    public void setDefaultAlarmRuleAdditions(final Date defaultAlarmRuleAdditions) {
        this.defaultAlarmRuleAdditions = defaultAlarmRuleAdditions;
    }

    public Date getCustomAlarmRuleAdditions() {
        return customAlarmRuleAdditions;
    }

    public void setCustomAlarmRuleAdditions(final Date customAlarmRuleAdditions) {
        this.customAlarmRuleAdditions = customAlarmRuleAdditions;
    }

    public Date getForwardAlarmsToUsmCentral() {
        return forwardAlarmsToUsmCentral;
    }

    public void setForwardAlarmsToUsmCentral(final Date forwardAlarmsToUsmCentral) {
        this.forwardAlarmsToUsmCentral = forwardAlarmsToUsmCentral;
    }

    public Date getForwardAlarmsToD3SocLive() {
        return forwardAlarmsToD3SocLive;
    }

    public void setForwardAlarmsToD3SocLive(final Date forwardAlarmsToD3SocLive) {
        this.forwardAlarmsToD3SocLive = forwardAlarmsToD3SocLive;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(final String orderType) {
        this.orderType = orderType;
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

    public Date getHostListProvidedByClient() {
        return hostListProvidedByClient;
    }

    public void setHostListProvidedByClient(final Date hostListProvidedByClient) {
        this.hostListProvidedByClient = hostListProvidedByClient;
    }

    public Date getHalcyonPackageGivenToClient() {
        return halcyonPackageGivenToClient;
    }

    public void setHalcyonPackageGivenToClient(final Date halcyonPackageGivenToClient) {
        this.halcyonPackageGivenToClient = halcyonPackageGivenToClient;
    }

    public Date getHalcyonDeployedToHosts() {
        return halcyonDeployedToHosts;
    }

    public void setHalcyonDeployedToHosts(final Date halcyonDeployedToHosts) {
        this.halcyonDeployedToHosts = halcyonDeployedToHosts;
    }

    public Date getDevopsNotifiedOfHalcyonAddition() {
        return devopsNotifiedOfHalcyonAddition;
    }

    public void setDevopsNotifiedOfHalcyonAddition(final Date devopsNotifiedOfHalcyonAddition) {
        this.devopsNotifiedOfHalcyonAddition = devopsNotifiedOfHalcyonAddition;
    }

    public Date getHalcyonApiTokenAddedToD3() {
        return halcyonApiTokenAddedToD3;
    }

    public void setHalcyonApiTokenAddedToD3(final Date halcyonApiTokenAddedToD3) {
        this.halcyonApiTokenAddedToD3 = halcyonApiTokenAddedToD3;
    }

    public Date getD3ConnectionVerified() {
        return d3ConnectionVerified;
    }

    public void setD3ConnectionVerified(final Date d3ConnectionVerified) {
        this.d3ConnectionVerified = d3ConnectionVerified;
    }

    public Date getEndLearningMode() {
        return endLearningMode;
    }

    public void setEndLearningMode(final Date endLearningMode) {
        this.endLearningMode = endLearningMode;
    }

    public Date getTestEmailSentToClient() {
        return testEmailSentToClient;
    }

    public void setTestEmailSentToClient(final Date testEmailSentToClient) {
        this.testEmailSentToClient = testEmailSentToClient;
    }

    public Date getDeployConsultingTenant() {
        return deployConsultingTenant;
    }

    public void setDeployConsultingTenant(final Date deployConsultingTenant) {
        this.deployConsultingTenant = deployConsultingTenant;
    }

    public Date getDeployVulnerabilityScans() {
        return deployVulnerabilityScans;
    }

    public void setDeployVulnerabilityScans(final Date deployVulnerabilityScans) {
        this.deployVulnerabilityScans = deployVulnerabilityScans;
    }

    public Date getProvidedCustomerWithReport() {
        return providedCustomerWithReport;
    }

    public void setProvidedCustomerWithReport(final Date providedCustomerWithReport) {
        this.providedCustomerWithReport = providedCustomerWithReport;
    }

    public Date getDiscussFutureCyrismaManagement() {
        return discussFutureCyrismaManagement;
    }

    public void setDiscussFutureCyrismaManagement(final Date discussFutureCyrismaManagement) {
        this.discussFutureCyrismaManagement = discussFutureCyrismaManagement;
    }

    public Date getReviewExistingCaAndMfaPolicies() {
        return reviewExistingCaAndMfaPolicies;
    }

    public void setReviewExistingCaAndMfaPolicies(final Date reviewExistingCaAndMfaPolicies) {
        this.reviewExistingCaAndMfaPolicies = reviewExistingCaAndMfaPolicies;
    }

    public Date getSignInPoliciesEnabled() {
        return signInPoliciesEnabled;
    }

    public void setSignInPoliciesEnabled(final Date signInPoliciesEnabled) {
        this.signInPoliciesEnabled = signInPoliciesEnabled;
    }

    public Date getConditionalAccessPolicyVerification() {
        return conditionalAccessPolicyVerification;
    }

    public void setConditionalAccessPolicyVerification(final Date conditionalAccessPolicyVerification) {
        this.conditionalAccessPolicyVerification = conditionalAccessPolicyVerification;
    }

    public Date getGeographicRestrictionsEnabled() {
        return geographicRestrictionsEnabled;
    }

    public void setGeographicRestrictionsEnabled(final Date geographicRestrictionsEnabled) {
        this.geographicRestrictionsEnabled = geographicRestrictionsEnabled;
    }

    public Date getDeviceComplianceEnabled() {
        return deviceComplianceEnabled;
    }

    public void setDeviceComplianceEnabled(final Date deviceComplianceEnabled) {
        this.deviceComplianceEnabled = deviceComplianceEnabled;
    }

    public Date getPasswordResetEnabledForSelfService() {
        return passwordResetEnabledForSelfService;
    }

    public void setPasswordResetEnabledForSelfService(final Date passwordResetEnabledForSelfService) {
        this.passwordResetEnabledForSelfService = passwordResetEnabledForSelfService;
    }

    public Date getBreakGlassAccountConfigured() {
        return breakGlassAccountConfigured;
    }

    public void setBreakGlassAccountConfigured(final Date breakGlassAccountConfigured) {
        this.breakGlassAccountConfigured = breakGlassAccountConfigured;
    }

    public Date getPimEnablement() {
        return pimEnablement;
    }

    public void setPimEnablement(final Date pimEnablement) {
        this.pimEnablement = pimEnablement;
    }

    public Date getImplementationVerified() {
        return implementationVerified;
    }

    public void setImplementationVerified(final Date implementationVerified) {
        this.implementationVerified = implementationVerified;
    }

    public boolean isShowJeopIcon() {
        return showJeopIcon;
    }

    public void setShowJeopIcon(final boolean showJeopIcon) {
        this.showJeopIcon = showJeopIcon;
    }

    public boolean isShowNoteIcon() {
        return showNoteIcon;
    }

    public void setShowNoteIcon(final boolean showNoteIcon) {
        this.showNoteIcon = showNoteIcon;
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
