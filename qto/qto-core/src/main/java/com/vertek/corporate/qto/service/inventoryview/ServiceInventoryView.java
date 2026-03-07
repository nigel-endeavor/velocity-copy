package com.vertek.corporate.qto.service.inventoryview;

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
@Table(name = "v_manage_service_inventory")
public class ServiceInventoryView extends AbstractMasterCustomerOwnedEntity {
    
    @Id
    @Column(name = "service_id")
    private Long id;

    @Column(name = "location_id")
    private Long locationId;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "client_location_id")
    private String clientLocationId;

    @Column(name = "client_location_info")
    private String clientLocationInfo;

    @Column(name = "client_location_type")
    private String clientLocationType;

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

    @Column(name = "provisioner")
    private String provisioner;

    @Column(name = "project_manager")
    private String projectManager;

    @Column(name = "provider")
    private String provider;

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

    @Column(name = "speed")
    private String speed;

    @Column(name = "service_type")
    private String type;

    @Column(name = "service_billed_to")
    private String serviceBilledTo;

    @Column(name= "order_type")
    private String orderType;

    @Column(name= "active")
    private boolean active;

    @Column(name = "client_service_id")
    private String clientServiceId;

    @Column(name = "provider_circuit_id")
    private String providerCircuitId;

    @Column(name = "service_mrc")
    private BigDecimal mrc;

    @Column(name = "service_nrc")
    private BigDecimal nrc;

    @Column(name = "service_mrr")
    private BigDecimal mrr;

    @Column(name = "service_nrr")
    private BigDecimal nrr;

    @Column(name = "annual_recurring_cost")
    private BigDecimal annualRecurringCost;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "summary_bill")
    private String summaryBill;

    @Column(name = "active_inactive")
    private String activeInactive;

    @Column(name = "contract_signed_date")
    private Date contractSignedDate;

    @Column(name = "contract_term")
    private String contractTerm;

    @Column(name = "circuit_term_end_date")
    private Date circuitTermEndDate;

    @Column(name = "count_open_disputes")
    private Long countOpenDisputes;

    @Column(name = "open_dispute_mrc")
    private BigDecimal openDisputeMrc;

    @Column(name = "open_dispute_nrc")
    private BigDecimal openDisputeNrc;

    @Column(name = "dispute_types")
    private String disputeTypes;

    @Column(name = "has_icb")
    private boolean hasIcb;

    @Column(name = "inventory_added_date")
    private Date inventoryAddedDate;

    @Column(name = "is_macd")
    private Long isMacd; //supports macD meta count for worklist, 1 if true, 0 if false

    @Column(name = "sub_order_type")
    private String subOrderType;

    @Column(name = "billable")
    private boolean billable;

    @Column(name = "child_ids")
    private String childIds;

    @Column(name = "child_order_types")
    private String childOrderTypes;

    @Column(name = "child_sub_order_types")
    private String childSubOrderTypes;

    @Column(name = "show_open_disconnect_icon")
    private boolean showOpenDisconnectIcon;

    @Column(name = "show_open_mac_icon")
    private boolean showOpenMacIcon;

    @Column(name = "show_open_dispute_icon")
    private boolean showOpenDisputeIcon;

    @Column(name = "linked")
    private boolean linked;

    @Column(name = "bundled")
    private boolean bundled;

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

    public String getProviderCircuitId() {
        return providerCircuitId;
    }

    public void setProviderCircuitId(final String providerCircuitId) {
        this.providerCircuitId = providerCircuitId;
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

    public BigDecimal getAnnualRecurringCost() {
        return annualRecurringCost;
    }

    public void setAnnualRecurringCost(final BigDecimal annualRecurringCost) {
        this.annualRecurringCost = annualRecurringCost;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public String getSummaryBill() {
        return summaryBill;
    }

    public void setSummaryBill(final String summaryBill) {
        this.summaryBill = summaryBill;
    }

    public String getActiveInactive() {
        return activeInactive;
    }

    public void setActiveInactive(final String activeInactive) {
        this.activeInactive = activeInactive;
    }

    public Date getContractSignedDate() {
        return contractSignedDate;
    }

    public void setContractSignedDate(final Date contractSignedDate) {
        this.contractSignedDate = contractSignedDate;
    }

    public String getContractTerm() {
        return contractTerm;
    }

    public void setContractTerm(final String contractTerm) {
        this.contractTerm = contractTerm;
    }

    public Date getCircuitTermEndDate() {
        return circuitTermEndDate;
    }

    public void setCircuitTermEndDate(final Date circuitTermEndDate) {
        this.circuitTermEndDate = circuitTermEndDate;
    }

    public Long getCountOpenDisputes() {
        return countOpenDisputes;
    }

    public void setCountOpenDisputes(final Long countOpenDisputes) {
        this.countOpenDisputes = countOpenDisputes;
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

    public String getDisputeTypes() {
        return disputeTypes;
    }

    public void setDisputeTypes(final String disputeTypes) {
        this.disputeTypes = disputeTypes;
    }

    public boolean isHasIcb() {
        return hasIcb;
    }

    public void setHasIcb(final boolean hasIcb) {
        this.hasIcb = hasIcb;
    }

    public Date getInventoryAddedDate() {
        return inventoryAddedDate;
    }

    public void setInventoryAddedDate(final Date inventoryAddedDate) {
        this.inventoryAddedDate = inventoryAddedDate;
    }

    public Long isMacd() {
        return isMacd;
    }

    public void setMacd(final Long macd) {
        isMacd = macd;
    }

    public String getSubOrderType() {
        return subOrderType;
    }

    public void setSubOrderType(final String subOrderType) {
        this.subOrderType = subOrderType;
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

    public boolean isBillable() {
        return billable;
    }

    public void setBillable(final boolean billable) {
        this.billable = billable;
    }

    public String getChildIds() {
        return childIds;
    }

    public void setChildIds(final String childIds) {
        this.childIds = childIds;
    }

    public String getChildOrderTypes() {
        return childOrderTypes;
    }

    public void setChildOrderTypes(final String childOrderTypes) {
        this.childOrderTypes = childOrderTypes;
    }

    public String getChildSubOrderTypes() {
        return childSubOrderTypes;
    }

    public void setChildSubOrderTypes(final String childSubOrderTypes) {
        this.childSubOrderTypes = childSubOrderTypes;
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

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(final String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public boolean isLinked() {
        return linked;
    }

    public void setLinked(final boolean linked) {
        this.linked = linked;
    }

    public boolean isBundled() {
        return bundled;
    }

    public void setBundled(final boolean bundled) {
        this.bundled = bundled;
    }

    public String getServiceBilledTo() {
        return serviceBilledTo;
    }

    public void setServiceBilledTo(final String serviceBilledTo) {
        this.serviceBilledTo = serviceBilledTo;
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
