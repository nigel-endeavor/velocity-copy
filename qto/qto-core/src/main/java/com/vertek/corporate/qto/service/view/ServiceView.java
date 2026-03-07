package com.vertek.corporate.qto.service.view;

import com.vertek.corporate.qto.common.AbstractMasterCustomerOwnedEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author rcasey
 * @since 2/23/2023
 */
@Entity
@Table(name = "v_manage_services")
public class ServiceView extends AbstractMasterCustomerOwnedEntity {
    
    @Id
    @Column(name = "service_id")
    private Long id;

    @Column(name = "location_id")
    private Long locationId;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "client_location_id")
    private String clientLocationId;

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

    @Column(name = "service_status")
    private String status;

    @Column(name = "service_sub_status")
    private String subStatus;

    @Column(name = "provisioner")
    private String provisioner;

    @Column(name = "project_manager")
    private String projectManager;

    @Column(name = "qa_manager")
    private String qaManager;

    @Column(name = "provider")
    private String provider;

    @Column(name = "customer_requested_install")
    private Date customerRequestedInstall;

    @Column(name = "site_survey_due")
    private Date siteSurveyDue;

    @Column(name = "site_survey_submit")
    private Date siteSurveySubmit;

    @Column(name = "service_billed_to")
    private String serviceBilledTo;

    @Column(name = "provider_order_submitted")
    private Date providerOrderSubmitted;

    @Column(name = "network_provider_foc")
    private Date networkProviderFoc;

    @Column(name = "data_provisioning_complete")
    private Date dataProvisioningComplete;

    @Column(name = "created")
    private Date created;

    @Column(name = "qa_check_open")
    private Date qaCheckOpen;

    @Column(name = "first_vendor_invoice")
    private Date firstVendorInvoice;

    @Column(name = "returned_to_order_group")
    private Date returnedToOrderGroup;

    @Column(name = "returned_to_sales")
    private Date returnedToSales;

    @Column(name = "billing_review_complete")
    private Date billingReviewComplete;

    @Column(name = "access_circuit_foc")
    private Date accessCircuitFoc;

    @Column(name = "on_hold")
    private Date onHold;

    @Column(name = "follow_up_date")
    private Date followUpDate;

    @Column(name = "greatest_milestone_name")
    private String greatestMilestoneName;

    @Column(name = "greatest_milestone_date")
    private Date greatestMilestoneDate;

    @Column(name = "client_location_type")
    private String clientLocationType;

    @Column(name = "client_location_info")
    private String clientLocationInfo;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "parent_company_name")
    private String parentCompanyName;

    @Column(name = "speed")
    private String speed;

    @Column(name = "service_type")
    private String type;

    @Column(name = "open_jeop")
    private String openJeop;

    @Column(name = "latest_note")
    private String latestNote;

    @Column(name = "show_jeop_icon")
    private boolean showJeopIcon;

    @Column(name = "show_note_icon")
    private boolean showNoteIcon;

    @Column(name = "open_jeop_responsibilites")
    private String openJeopResponsibilities;

    @Column(name = "vertek_project_manager")
    private String vertekProjectManager;

    @Column(name= "order_type")
    private String orderType;

    @Column(name= "active")
    private boolean active;

    @Column(name = "client_service_id")
    private String clientServiceId;

    @Column(name = "status_age")
    private Long statusAge;

    @Column(name = "service_mrc")
    private BigDecimal mrc;

    @Column(name = "service_nrc")
    private BigDecimal nrc;

    @Column(name = "service_mrr")
    private BigDecimal mrr;

    @Column(name = "service_nrr")
    private BigDecimal nrr;

    @Column(name = "lcon_phone")
    private String lconPhone;

    @Column(name = "level_of_effort")
    private String levelOfEffort;

    @Column (name = "progress_percentage")
    private Long progressPercentage;

    @Column(name = "project_name")
    private String projectName;

    @Column(name = "record_source")
    private String recordSource;

    @Column(name = "linked")
    private Boolean linked;

    @Column(name = "bundled")
    private Boolean bundled;

    @Column(name = "linked_bundled_parent")
    private Boolean linkedBundledParent;

    @Column(name = "linked_bundled_parent_id")
    private Long linkedBundledParentId;

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

    public String getClientLocationId() {
        return clientLocationId;
    }

    public void setClientLocationId(final String clientLocationId) {
        this.clientLocationId = clientLocationId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(final Long orderId) {
        this.orderId = orderId;
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

    public String getProvisioner() {
        return provisioner;
    }

    public void setProvisioner(final String provisioner) {
        this.provisioner = provisioner;
    }

    public String getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(final String projectManager) {
        this.projectManager = projectManager;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(final String provider) {
        this.provider = provider;
    }

    public Date getCustomerRequestedInstall() {
        return customerRequestedInstall;
    }

    public void setCustomerRequestedInstall(final Date customerRequestedInstall) {
        this.customerRequestedInstall = customerRequestedInstall;
    }

    public Date getSiteSurveyDue() {
        return siteSurveyDue;
    }

    public void setSiteSurveyDue(final Date siteSurveyDue) {
        this.siteSurveyDue = siteSurveyDue;
    }

    public Date getSiteSurveySubmit() {
        return siteSurveySubmit;
    }

    public void setSiteSurveySubmit(final Date siteSurveySubmit) {
        this.siteSurveySubmit = siteSurveySubmit;
    }

    public Date getProviderOrderSubmitted() {
        return providerOrderSubmitted;
    }

    public void setProviderOrderSubmitted(final Date providerOrderSubmitted) {
        this.providerOrderSubmitted = providerOrderSubmitted;
    }

    public Date getNetworkProviderFoc() {
        return networkProviderFoc;
    }

    public void setNetworkProviderFoc(final Date networkProviderFoc) {
        this.networkProviderFoc = networkProviderFoc;
    }

    public Date getDataProvisioningComplete() {
        return dataProvisioningComplete;
    }

    public void setDataProvisioningComplete(final Date dataProvisioningComplete) {
        this.dataProvisioningComplete = dataProvisioningComplete;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(final Date created) {
        this.created = created;
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

    public String getClientLocationType() {
        return clientLocationType;
    }

    public void setClientLocationType(final String clientLocationType) {
        this.clientLocationType = clientLocationType;
    }

    public String getClientLocationInfo() {
        return clientLocationInfo;
    }

    public void setClientLocationInfo(final String clientLocationInfo) {
        this.clientLocationInfo = clientLocationInfo;
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

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(final String speed) {
        this.speed = speed;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getOpenJeop() {
        return openJeop;
    }

    public void setOpenJeop(final String openJeop) {
        this.openJeop = openJeop;
    }

    public String getLatestNote() {
        return latestNote;
    }

    public void setLatestNote(final String latestNote) {
        this.latestNote = latestNote;
    }

    public boolean isShowJeopIcon() {
        return showJeopIcon;
    }

    public void setShowJeopIcon(final boolean showJeopIcon) {
        this.showJeopIcon = showJeopIcon;
    }

    public String getOpenJeopResponsibilities() {
        return openJeopResponsibilities;
    }

    public void setOpenJeopResponsibilities(final String openJeopResponsibilities) {
        this.openJeopResponsibilities = openJeopResponsibilities;
    }

    public boolean isShowNoteIcon() {
        return showNoteIcon;
    }

    public void setShowNoteIcon(final boolean showNoteIcon) {
        this.showNoteIcon = showNoteIcon;
    }

    public String getVertekProjectManager() {
        return vertekProjectManager;
    }

    public void setVertekProjectManager(final String vertekProjectManager) {
        this.vertekProjectManager = vertekProjectManager;
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

    public String getClientServiceId() {
        return clientServiceId;
    }

    public void setClientServiceId(final String clientServiceId) {
        this.clientServiceId = clientServiceId;
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

    public String getLconPhone() {
        return lconPhone;
    }

    public void setLconPhone(final String lconPhone) {
        this.lconPhone = lconPhone;
    }

    public Long getProgressPercentage() {
        return progressPercentage;
    }

    public void setProgressPercentage(final Long progressPercentage) {
        this.progressPercentage = progressPercentage;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public String getLevelOfEffort() {
        return levelOfEffort;
    }

    public void setLevelOfEffort(final String levelOfEffort) {
        this.levelOfEffort = levelOfEffort;
    }

    public String getSubStatus() {
        return subStatus;
    }

    public void setSubStatus(final String subStatus) {
        this.subStatus = subStatus;
    }

    public String getQaManager() {
        return qaManager;
    }

    public void setQaManager(final String qaManager) {
        this.qaManager = qaManager;
    }

    public Date getQaCheckOpen() {
        return qaCheckOpen;
    }

    public void setQaCheckOpen(final Date qaCheckOpen) {
        this.qaCheckOpen = qaCheckOpen;
    }

    public Date getFirstVendorInvoice() {
        return firstVendorInvoice;
    }

    public void setFirstVendorInvoice(final Date firstVendorInvoice) {
        this.firstVendorInvoice = firstVendorInvoice;
    }

    public Date getReturnedToOrderGroup() {
        return returnedToOrderGroup;
    }

    public void setReturnedToOrderGroup(final Date returnedToOrderGroup) {
        this.returnedToOrderGroup = returnedToOrderGroup;
    }

    public Date getReturnedToSales() {
        return returnedToSales;
    }

    public void setReturnedToSales(final Date returnedToSales) {
        this.returnedToSales = returnedToSales;
    }

    public Date getBillingReviewComplete() {
        return billingReviewComplete;
    }

    public void setBillingReviewComplete(final Date billingReviewComplete) {
        this.billingReviewComplete = billingReviewComplete;
    }

    public Date getAccessCircuitFoc() {
        return accessCircuitFoc;
    }

    public void setAccessCircuitFoc(final Date accessCircuitFoc) {
        this.accessCircuitFoc = accessCircuitFoc;
    }

    public Date getOnHold() {
        return onHold;
    }

    public void setOnHold(final Date onHold) {
        this.onHold = onHold;
    }

    public Date getFollowUpDate() {
        return followUpDate;
    }

    public void setFollowUpDate(final Date followUpDate) {
        this.followUpDate = followUpDate;
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

    public String getServiceBilledTo() {
        return serviceBilledTo;
    }

    public void setServiceBilledTo(final String serviceBilledTo) {
        this.serviceBilledTo = serviceBilledTo;
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
