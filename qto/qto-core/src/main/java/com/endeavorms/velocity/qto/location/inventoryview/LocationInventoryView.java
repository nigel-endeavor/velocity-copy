package com.endeavorms.velocity.qto.location.inventoryview;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.endeavorms.velocity.qto.common.AbstractMasterCustomerOwnedEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author rcasey 
 * @since 1/9/2023
 */
@Entity
@Table(name = "v_manage_location_inventory")
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationInventoryView extends AbstractMasterCustomerOwnedEntity {

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

    @Column(name = "count_active_services")
    private Long activeServiceCount;

    @Column(name = "count_inactive_services")
    private Long inactiveServiceCount;

    @Column(name = "count_services_complete")
    private Long countServicesComplete;

    @Column(name = "count_services_cancelled")
    private Long countServicesCancelled;

    @Column(name = "count_services_change_in_assignment")
    private Long countServicesChangeInAssignment;

    @Column(name = "active_complete_mrc")
    private BigDecimal activeCompleteMrc;

    @Column(name = "active_complete_nrc")
    private BigDecimal activeCompleteNrc;

    @Column(name = "active_complete_mrr")
    private BigDecimal activeCompleteMrr;

    @Column(name = "active_complete_nrr")
    private BigDecimal activeCompleteNrr;

    @Column(name = "annual_recurring_cost")
    private BigDecimal annualRecurringCost;

    @Column(name = "count_open_disputes")
    private Long countOpenDisputes;

    @Column(name = "open_dispute_mrc")
    private BigDecimal openDisputeMrc;

    @Column(name = "open_dispute_nrc")
    private BigDecimal openDisputeNrc;

    @Column(name = "macd_count")
    private Long macdCount;

    @Column(name = "active_inactive")
    private String activeInactive;

    @Column(name = "inventory_added_date")
    private Date inventoryAddedDate;

    @Column(name = "client_location_info")
    private String clientLocationInfo;

    @Column(name = "client_location_type")
    private String clientLocationType;

    @Column(name = "sub_order_types")
    private String subOrderTypes;

    @Column(name = "show_open_disconnect_icon")
    private boolean showOpenDisconnectIcon;

    @Column(name = "show_open_mac_icon")
    private boolean showOpenMacIcon;

    @Column(name = "show_open_dispute_icon")
    private boolean showOpenDisputeIcon;

    @Column(name = "show_linked_icon")
    private boolean showLinkedIcon;

    @Column(name = "show_bundled_icon")
    private boolean showBundledIcon;

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

    public Long getActiveServiceCount() {
        return activeServiceCount;
    }

    public void setActiveServiceCount(final Long activeServiceCount) {
        this.activeServiceCount = activeServiceCount;
    }

    public Long getInactiveServiceCount() {
        return inactiveServiceCount;
    }

    public void setInactiveServiceCount(final Long inactiveServiceCount) {
        this.inactiveServiceCount = inactiveServiceCount;
    }

    public Long getCountServicesComplete() {
        return countServicesComplete;
    }

    public void setCountServicesComplete(final Long countServicesComplete) {
        this.countServicesComplete = countServicesComplete;
    }

    public Long getCountServicesCancelled() {
        return countServicesCancelled;
    }

    public void setCountServicesCancelled(final Long countServicesCancelled) {
        this.countServicesCancelled = countServicesCancelled;
    }

    public Long getCountServicesChangeInAssignment() {
        return countServicesChangeInAssignment;
    }

    public void setCountServicesChangeInAssignment(final Long countServicesChangeInAssignment) {
        this.countServicesChangeInAssignment = countServicesChangeInAssignment;
    }

    public BigDecimal getActiveCompleteMrc() {
        return activeCompleteMrc;
    }

    public void setActiveCompleteMrc(final BigDecimal activeCompleteMrc) {
        this.activeCompleteMrc = activeCompleteMrc;
    }

    public BigDecimal getActiveCompleteNrc() {
        return activeCompleteNrc;
    }

    public void setActiveCompleteNrc(final BigDecimal activeCompleteNrc) {
        this.activeCompleteNrc = activeCompleteNrc;
    }

    public BigDecimal getActiveCompleteMrr() {
        return activeCompleteMrr;
    }

    public void setActiveCompleteMrr(final BigDecimal activeCompleteMrr) {
        this.activeCompleteMrr = activeCompleteMrr;
    }

    public BigDecimal getActiveCompleteNrr() {
        return activeCompleteNrr;
    }

    public void setActiveCompleteNrr(final BigDecimal activeCompleteNrr) {
        this.activeCompleteNrr = activeCompleteNrr;
    }

    public BigDecimal getAnnualRecurringCost() {
        return annualRecurringCost;
    }

    public void setAnnualRecurringCost(final BigDecimal annualRecurringCost) {
        this.annualRecurringCost = annualRecurringCost;
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

    public Long getMacdCount() {
        return macdCount;
    }

    public void setMacdCount(final Long macdCount) {
        this.macdCount = macdCount;
    }

    public String getActiveInactive() {
        return activeInactive;
    }

    public void setActiveInactive(final String activeInactive) {
        this.activeInactive = activeInactive;
    }

    public Date getInventoryAddedDate() {
        return inventoryAddedDate;
    }

    public void setInventoryAddedDate(final Date inventoryAddedDate) {
        this.inventoryAddedDate = inventoryAddedDate;
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

    public Long getCountOpenDisputes() {
        return countOpenDisputes;
    }

    public void setCountOpenDisputes(final Long countOpenDisputes) {
        this.countOpenDisputes = countOpenDisputes;
    }

    public String getSubOrderTypes() {
        return subOrderTypes;
    }

    public void setSubOrderTypes(final String subOrderTypes) {
        this.subOrderTypes = subOrderTypes;
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

    public boolean isShowOpenDisputeIcon() {
        return showOpenDisputeIcon;
    }

    public void setShowOpenDisputeIcon(final boolean showOpenDisputeIcon) {
        this.showOpenDisputeIcon = showOpenDisputeIcon;
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
