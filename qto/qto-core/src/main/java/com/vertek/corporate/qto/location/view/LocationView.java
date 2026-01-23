package com.vertek.corporate.qto.location.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vertek.corporate.qto.common.AbstractMasterCustomerOwnedEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author rcasey 
 * @since 1/9/2023
 */
@Entity
@Table(name = "v_manage_locations")
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationView extends AbstractMasterCustomerOwnedEntity {

    @Id
    @Column(name = "location_id")
    private Long id;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "parent_company_name")
    private String parentCompanyName;

    @Column(name = "parent_company_client_id")
    private String parentCompanyClientId;

    @Column(name = "end_customer_client_id")
    private String endCustomerClientId;

    @Column(name = "provisioner")
    private String provisioner;

    @Column(name = "client_project_manager")
    private String clientProjectManager;

    @Column(name = "vertek_project_manager")
    private String vertekProjectManager;

    @Column(name = "client_order_id")
    private String  clientOrderId;

    @Column(name = "client_location_id")
    private String clientLocationId;

    @Column(name = "location_name")
    private String locationName;

    @Column(name = "location_status")
    private String locationStatus;

    @Column(name = "count_services")
    private Long countServices;

    @Column(name = "services")
    private String services;

    @Column(name = "completion_date")
    private Date completionDate;

    @Column(name = "open_jeops")
    private String openJeops;

    @Column(name = "show_jeop_icon")
    private boolean showJeopIcon;

    @Column(name = "open_jeop_responsibilites")
    private String openJeopResponsibilities;

    @Column(name= "active")
    private boolean active;

    @Column(name = "progress_percentage")
    private Long progressPercentage;

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

    @Column(name = "mac_count")
    private Long macCount;

    @Column(name = "record_source")
    private String recordSource;

    @Column(name = "client_location_info")
    private String clientLocationInfo;

    @Column(name = "client_location_type")
    private String clientLocationType;

    @Column(name = "greatest_milestone_name")
    private String greatestMilestoneName;

    @Column(name = "greatest_milestone_date")
    private Date greatestMilestoneDate;

    @Column(name = "open_jeop")
    private String openJeop;

    @Column(name = "latest_note")
    private String latestNote;

    @Column(name = "mrc")
    private BigDecimal mrc;

    @Column(name = "annual_recurring_cost")
    private BigDecimal annualRecurringCost;

    @Column(name = "nrc")
    private BigDecimal nrc;

    @Column(name = "mrr")
    private BigDecimal mrr;

    @Column(name = "nrr")
    private BigDecimal nrr;

    @Column(name = "show_linked_icon")
    private boolean showLinkedIcon;

    @Column(name = "show_bundled_icon")
    private boolean showBundledIcon;

    @Column(name = "show_open_disconnect_icon")
    private boolean showOpenDisconnectIcon;

    @Column(name = "show_open_mac_icon")
    private boolean showOpenMacIcon;

    @Override
    public Long getId() {
        return id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(final Long orderId) {
        this.orderId = orderId;
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

    public String getProvisioner() {
        return provisioner;
    }

    public void setProvisioner(final String provisioner) {
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

    public String getLocationStatus() {
        return locationStatus;
    }

    public void setLocationStatus(final String locationStatus) {
        this.locationStatus = locationStatus;
    }

    public Long getCountServices() {
        return countServices;
    }

    public void setCountServices(final Long countServices) {
        this.countServices = countServices;
    }

    public String getServices() {
        return services;
    }

    public void setServices(final String services) {
        this.services = services;
    }

    public Date getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(final Date completionDate) {
        this.completionDate = completionDate;
    }

    public String getOpenJeops() {
        return openJeops;
    }

    public void setOpenJeops(final String openJeops) {
        this.openJeops = openJeops;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(final boolean active) {
        this.active = active;
    }

    public Long getProgressPercentage() {
        return progressPercentage;
    }

    public void setProgressPercentage(final Long progressPercentage) {
        this.progressPercentage = progressPercentage;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public Long getMacCount() {
        return macCount;
    }

    public void setMacCount(final Long macCount) {
        this.macCount = macCount;
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

    public BigDecimal getMrc() {
        return mrc;
    }

    public void setMrc(final BigDecimal mrc) {
        this.mrc = mrc;
    }

    public BigDecimal getAnnualRecurringCost() {
        return annualRecurringCost;
    }

    public void setAnnualRecurringCost(final BigDecimal annualRecurringCost) {
        this.annualRecurringCost = annualRecurringCost;
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

    public boolean isShowLinkedIcon() {
        return showLinkedIcon;
    }

    public void setShowLinkedIcon(final boolean showLinkedIcon) {
        this.showLinkedIcon = showLinkedIcon;
    }

    public boolean isShowBundledIcon() {
        return showBundledIcon;
    }

    public void setShowBundledIcon(final boolean showBundledIcon) {
        this.showBundledIcon = showBundledIcon;
    }

    public String getParentCompanyClientId() {
        return parentCompanyClientId;
    }

    public void set(final String parentCompanyClientId) {
        this.parentCompanyClientId = parentCompanyClientId;
    }

    public String getEndCustomerClientId() {
        return endCustomerClientId;
    }

    public void setEndCustomerClientId(final String endCustomerClientId) {
        this.endCustomerClientId = endCustomerClientId;
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
