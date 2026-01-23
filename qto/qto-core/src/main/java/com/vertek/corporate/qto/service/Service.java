package com.vertek.corporate.qto.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vertek.corporate.qto.brokerage.ServiceBrokerage;
import com.vertek.corporate.qto.common.AbstractMasterCustomerOwnedEntity;
import com.vertek.corporate.qto.common.SecurityUtils;
import org.hibernate.annotations.Formula;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author rcasey
 * @since 1/6/2023
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "service")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Service extends AbstractMasterCustomerOwnedEntity {

    /**
     * Unique Identifier.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_id")
    private Long id;

    @Column(name = "location_id")
    private Long locationId;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "client_service_id")
    private String clientServiceId;

    @Column(name = "alternate_id")
    private String alternateId;

    @Column(name = "quote_solution_id")
    private String quoteSolutionId;

    @Column(name = "service_status")
    private String status;

    @Column(name = "service_sub_status")
    private String subStatus;

    @Column(name = "sub_product_type")
    private String subProductType;

    @Column(name = "order_type")
    private String orderType;

    @Column(name = "activation_link")
    private String activationLink;

    @Column(name = "activation_phone")
    private String activationPhone;

    @Column(name = "follow_up_date")
    private Date followUpDate;

    @Column(name = "contract_term")
    private String contractTerm;

    @Column(name = "contract_signed_date")
    private Date contractSignedDate;

    @Column(name = "po_number")
    private String poNumber;

    @Column(name = "service_mrc")
    private BigDecimal mrc = new BigDecimal(0);

    @Column(name = "service_nrc")
    private BigDecimal nrc = new BigDecimal(0);

    @Column(name = "has_icb")
    private boolean hasIcb;

    @Column(name = "service_icb")
    private BigDecimal icb = new BigDecimal(0);

    @Column(name = "has_osp")
    private boolean hasOsp;

    @Column(name = "service_osp")
    private BigDecimal osp = new BigDecimal(0);

    @Column(name = "provider")
    private String provider;

    @Column(name = "underlying_provider")
    private String underlyingProvider;

    @Column(name = "summary_bill")
    private String summaryBill;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "provider_order_num")
    private String providerOrderNum;

    @Column(name = "provider_circuit_id")
    private String providerCircuitId;

    @Column(name = "cross_connect_id")
    private String crossConnectId;

    @Column(name = "inside_wiring_required", length = 3)
    private String insideWiringRequired;

    @Column(name = "dmarc")
    private String dmarc;

    @Column(name = "additional_ip_block_required", length = 3)
    private String additionalIpBlockRequired;

    @Column(name = "additional_ip_block")
    private String additionalIpBlock;

    @Column(name = "wan_ips")
    private String wanIps;

    @Column(name = "wan_gateway")
    private String wanGateway;

    @Column(name = "wan_subnet")
    private String wanSubnet;

    @Column(name = "lan_block")
    private String lanBlock;

    @Column(name = "lan_ips")
    private String lanIps;

    @Column(name = "lan_gateway")
    private String lanGateway;

    @Column(name = "lan_subnet")
    private String lanSubnet;

    @Column(name = "dns1")
    private String dns1;

    @Column(name = "dns2")
    private String dns2;

    @Column(name = "osp_const_interval_est")
    private String ospConstIntervalEst;

    @Column(name = "speed")
    private String speed;

    @Column(name = "download_speed")
    private String downloadSpeed;

    @Column(name = "upload_speed")
    private String uploadSpeed;

    @Column(name = "media_type")
    private String mediaType;

    @Column(name = "net_status")
    private String netStatus;

    @Column(name = "location_hours")
    private String locationHours;

    @Column(name = "product_install_interval")
    private String productInstallInterval;

    @Column(name = "expedite_order")
    private boolean expediteOrder;

    @Column(name = "trunk_group")
    private String trunkGroup;

    @Column(name = "connection_handoff_type")
    private String connectionHandoffType;

    @Column(name = "tie_down_info")
    private String tieDownInfo;

    @Column(name = "service_type")
    private String type;

    @Formula("(select lv.lookup_value_id from lookup_value lv join lookup_type lt on lv.lookup_type_id = lt.lookup_type_id "
            + "where lt.lookup_type_code = 'TENANT_SERVICE_TYPES' and lv.lookup_display = service_type and lv.tenant_id = tenant_id)")
    private Long typeId;

    @Column(name = "currency")
    private String currency;

    /** The status of the building. */
    @Column(name = "building_status")
    private String buildingStatus;

    @Column(name = "ip_format")
    private String ipFormat;

    @Column(name = "service_description")
    private String description;

    @Column(name = "last_update_by")
    private String lastUpdateBy;

    @Column(name = "last_update_date")
    private Date lastUpdateDate;

    @Column(name = "last_status_change")
    private Date lastStatusChange;

    @Column(name = "circuit_term_end_date")
    private Date circuitTermEndDate;

    @Column(name = "active")
    private boolean active = true;

    @Column(name = "progress_percentage")
    private Integer progressPercentage = 0;

    @Column(name = "ticket_number")
    private String ticketNumber;

    @Column(name = "service_billed_to")
    private String serviceBilledTo;

    @Column(name = "job_number")
    private String jobNumber;

    @Column(name="legacy_id")
    private String legacyId;

    @Column(name="legacy_dia_id")
    private String legacyDiaId;

    @Column(name="legacy_equipment")
    private String legacyEquipment;

    @Column(name = "update_client")
    private Boolean updateClient;

    @Column(name = "eligible_for_update")
    private Boolean eligibleForUpdate;

    @Column(name = "current_inventory")
    private boolean isCurrentInventory;

    @Column(name = "billable")
    private boolean billable;

    @Column(name="disconnect_reason")
    private String disconnectReason;

    @Column(name="sub_order_type")
    private String subOrderType;

    @Column(name="client_service_info")
    private String clientServiceInfo;

    @Column(name="client_service_type")
    private String clientServiceType;

    @Column(name="account_passcode")
    private String accountPasscode;

    @Column(name="project_name")
    private String projectName;

    @Column(name="early_termination_fee")
    private BigDecimal earlyTerminationFee = new BigDecimal(0);

    @Column(name="parent_service_id")
    private Long parentServiceId;

    @Column(name="macd_cost_change")
    private BigDecimal macdCostChange = new BigDecimal(0);

    @Column(name="macd_revenue_change")
    private BigDecimal macdRevenueChange = new BigDecimal(0);

    @Column(name="annual_recurring_cost")
    private BigDecimal annualRecurringCost = new BigDecimal(0);

    @Column(name="record_source")
    private String recordSource;

    @Column(name="ignore_for_renewals")
    private Boolean ignoreForRenewals;

    @Column(name = "linked")
    private boolean linked;

    @Column(name = "bundled")
    private boolean bundled;

    @Column(name = "linked_bundled_parent")
    private boolean linkedBundledParent;

    @Column(name = "linked_bundled_parent_id")
    private Long linkedBundledParentId;

    @Column(name = "bill_cycle")
    private Long billCycle;

    @Column(name = "tsp_code")
    private String tspCode;

    @Column(name = "tsp_code_expiration_date")
    private Date tspCodeExpirationDate;

    @Column(name = "inventory_service_id")
    private Long inventoryServiceId;

    @Column(name = "provisioning_service_id")
    private Long provisioningServiceId;

    @Column(name = "final_update")
    private boolean finalUpdate;

    @Column(name = "eligible_for_inventory")
    private boolean eligibleForInventory;

    @Column(name = "bill_to_location")
    private boolean billToLocation;

    @Column(name = "managed_service")
    private boolean managedService;

    @Column(name = "production_impacting")
    private boolean productionImpacting;

    @Column(name = "auto_renewal")
    private boolean autoRenewal;

    @Column(name = "co_terminus")
    private boolean coTerminus;

    @Column(name = "renewal_cancel_notice_period")
    private String renewalCancelNoticePeriod;

    @Column(name = "contract_info")
    private String contractInfo;

    @Column(name = "address_1")
    private String address1;

    @Column(name = "address_2")
    private String address2;

    @Column(name = "city")
    private String city;

    @Column(name = "state_province")
    private String state;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "country")
    private String country;

    @Column(name = "billing_email")
    private String billingEmail;

    @OneToOne(mappedBy = "service")
    @JsonIgnore
    private ServiceBrokerage brokerageInfo;

    @Column(name = "external_order_reference")
    private String externalOrderReference;

    @Column(name = "customer_billing_instructions")
    private String customerBillingInstructions;

    @Column (name = "field_services_provider")
    private String fieldServicesProvider;

    @Column(name = "shipping_address_1")
    private String shippingAddress1;

    @Column(name = "shipping_address_2")
    private String shippingAddress2;

    @Column(name = "shipping_city")
    private String shippingCity;

    @Column(name = "shipping_state_province")
    private String shippingState;

    @Column(name = "shipping_postal_code")
    private String shippingPostalCode;

    @Column(name = "shipping_country")
    private String shippingCountry;

    // used for Cyber technical pages
    @Column(name = "technical_notes")
    private String technicalNotes;

    @Column(name = "number_of_endpoints")
    private Integer numberOfEndpoints;

    @Column(name = "microsoft_licensing")
    private String microsoftLicensing;

    @Column(name = "number_of_users")
    private Integer numberOfUsers;

    @Column(name = "opportunity_num")
    private String opportunityNum;

    /** Could be the shipping address above, or the service's billing address, or the master customer's address. */
    @Column(name = "ship_to")
    private String shipTo;

    @Column(name = "service_mrr")
    private BigDecimal mrr = new BigDecimal(0);

    @Column(name = "service_nrr")
    private BigDecimal nrr = new BigDecimal(0);

    @Column(name = "marked_for_deletion")
    private boolean markedForDeletion;

    @Column(name = "deletion_date")
    private Date deletionDate;

    @JsonIgnore
    @Formula("(SELECT CASE " +
            "WHEN service_status = 'Service Complete' THEN 1 " +
            "WHEN service_status = 'Disconnect Complete' THEN 1 " +
            "WHEN service_status = 'Service Cancelled' THEN 2 " +
            "WHEN service_status = 'Disconnect Cancelled' THEN 2 " +
            "WHEN service_status = 'Change In Assignment' THEN 3 " +
            "ELSE 0 END)")
    private Long sortOrder;

    @Formula("(select s.service_mrc from service s where s.service_id = parent_service_id)")
    private BigDecimal parentMrc;

     @Formula("(select s.service_mrr from service s where s.service_id = parent_service_id)")
    private BigDecimal parentMrr;

    @JsonIgnore
    @Formula("(SELECT CASE " +
            "WHEN order_type = 'Disconnect' AND active = false THEN 1 " +
            "WHEN active = false THEN 2 " +
            "ELSE 0 END)")
    private Long inventorySortOrder;


    @JsonIgnore
    @Transient
    private String persistedStatus;

    @Transient
    private String costChangeReason;

    @JsonIgnore
    @Transient
    private BigDecimal originalMrc;

    @JsonIgnore
    @Transient
    private BigDecimal originalMrr;

    @JsonIgnore
    @Transient
    private BigDecimal originalNrr;

    @Transient
    private Boolean isTerminal;

    @PostLoad
    void postLoad() {
        this.persistedStatus = status;
        this.isTerminal = TerminalServiceStatuses.getStatuses().contains(status);
    }


    @PrePersist
    @PreUpdate
    void preUpdate() {
        setLastUpdateDate(new Date());
        setLastUpdateBy(SecurityUtils.getLoggedInUser());
        if (getStatus() != null && !getStatus().equals(getPersistedStatus())) {
            setLastStatusChange(new Date());
        }
    }

    @Override
    public Long getId() {
        return id;
    }

    /**
     * Used by the Location postLoad method to calculate the total MRC for the service.
     * Services with A and Z mrc override this.
     * @return
     */
    public BigDecimal getTotalMrc() {
        return this.mrc;
    }

    /**
     * Used by the Location postLoad method to calculate the total NRC for the service.
     * Services with A and Z mrc override this.
     * @return
     */
    public BigDecimal getTotalNrc() {
        return this.nrc;
    }

    public BigDecimal getTotalMrr() {
        return mrr;
    }

    public BigDecimal getTotalNrr() {
        return nrr;
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

    public String getClientServiceId() {
        return clientServiceId;
    }

    public void setClientServiceId(final String clientServiceId) {
        this.clientServiceId = clientServiceId;
    }

    public String getAlternateId() {
        return alternateId;
    }

    public void setAlternateId(final String alternateId) {
        this.alternateId = alternateId;
    }

    public String getQuoteSolutionId() {
        return quoteSolutionId;
    }

    public void setQuoteSolutionId(final String quoteSolutionId) {
        this.quoteSolutionId = quoteSolutionId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public String getSubStatus() {
        return subStatus;
    }

    public void setSubStatus(final String subStatus) {
        this.subStatus = subStatus;
    }

    public String getSubProductType() {
        return subProductType;
    }

    public void setSubProductType(final String subProductType) {
        this.subProductType = subProductType;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(final String orderType) {
        this.orderType = orderType;
    }

    public String getActivationLink() {
        return activationLink;
    }

    public void setActivationLink(final String activationLink) {
        this.activationLink = activationLink;
    }

    public String getActivationPhone() {
        return activationPhone;
    }

    public void setActivationPhone(final String activationPhone) {
        this.activationPhone = activationPhone;
    }

    public Date getFollowUpDate() {
        return followUpDate;
    }

    public void setFollowUpDate(final Date followUpDate) {
        this.followUpDate = followUpDate;
    }

    public String getContractTerm() {
        return contractTerm;
    }

    public void setContractTerm(final String contractTerm) {
        this.contractTerm = contractTerm;
    }

    public Date getContractSignedDate() {
        return contractSignedDate;
    }

    public void setContractSignedDate(final Date contractSignedDate) {
        this.contractSignedDate = contractSignedDate;
    }

    public String getPoNumber() {
        return poNumber;
    }

    public void setPoNumber(final String poNumber) {
        this.poNumber = poNumber;
    }

    public BigDecimal getMrc() {
        return mrc;
    }

    public void setMrc(final BigDecimal mrc) {
        this.mrc = mrc;
    }

    public BigDecimal getParentMrc() {
        return parentMrc;
    }

    public void setParentMrc(final BigDecimal parentMrc) {
        this.parentMrc = parentMrc;
    }

    public BigDecimal getParentMrr() {
        return parentMrr;
    }

    public void setParentMrr(final BigDecimal parentMrr) {
        this.parentMrr = parentMrr;
    }

    public BigDecimal getNrc() {
        return nrc;
    }

    public void setNrc(final BigDecimal nrc) {
        this.nrc = nrc;
    }

    public boolean isHasIcb() {
        return hasIcb;
    }

    public void setHasIcb(final boolean hasIcb) {
        this.hasIcb = hasIcb;
    }

    public BigDecimal getIcb() {
        return icb;
    }

    public void setIcb(final BigDecimal icb) {
        this.icb = icb;
    }

    public boolean isHasOsp() {
        return hasOsp;
    }

    public void setHasOsp(final boolean hasOsp) {
        this.hasOsp = hasOsp;
    }

    public BigDecimal getOsp() {
        return osp;
    }

    public void setOsp(final BigDecimal osp) {
        this.osp = osp;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(final String provider) {
        this.provider = provider;
    }

    public String getUnderlyingProvider() {
        return underlyingProvider;
    }

    public void setUnderlyingProvider(final String underlyingProvider) {
        this.underlyingProvider = underlyingProvider;
    }

    public String getSummaryBill() {
        return summaryBill;
    }

    public void setSummaryBill(final String summaryBill) {
        this.summaryBill = summaryBill;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(final String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getProviderOrderNum() {
        return providerOrderNum;
    }

    public void setProviderOrderNum(final String providerOrderNum) {
        this.providerOrderNum = providerOrderNum;
    }

    public String getProviderCircuitId() {
        return providerCircuitId;
    }

    public void setProviderCircuitId(final String providerCircuitId) {
        this.providerCircuitId = providerCircuitId;
    }

    public String getInsideWiringRequired() {
        return insideWiringRequired;
    }

    public void setInsideWiringRequired(final String insideWiringRequired) {
        this.insideWiringRequired = insideWiringRequired;
    }

    public String getDmarc() {
        return dmarc;
    }

    public void setDmarc(final String dmarc) {
        this.dmarc = dmarc;
    }

    public String getAdditionalIpBlockRequired() {
        return additionalIpBlockRequired;
    }

    public void setAdditionalIpBlockRequired(final String additionalIpBlockRequired) {
        this.additionalIpBlockRequired = additionalIpBlockRequired;
    }

    public String getAdditionalIpBlock() {
        return additionalIpBlock;
    }

    public void setAdditionalIpBlock(final String additionalIpBlock) {
        this.additionalIpBlock = additionalIpBlock;
    }

    public String getWanIps() {
        return wanIps;
    }

    public void setWanIps(final String wanIps) {
        this.wanIps = wanIps;
    }

    public String getWanGateway() {
        return wanGateway;
    }

    public void setWanGateway(final String wanGateway) {
        this.wanGateway = wanGateway;
    }

    public String getWanSubnet() {
        return wanSubnet;
    }

    public void setWanSubnet(final String wanSubnet) {
        this.wanSubnet = wanSubnet;
    }

    public String getLanBlock() {
        return lanBlock;
    }

    public void setLanBlock(final String lanBlock) {
        this.lanBlock = lanBlock;
    }

    public String getLanIps() {
        return lanIps;
    }

    public void setLanIps(final String lanIps) {
        this.lanIps = lanIps;
    }

    public String getLanGateway() {
        return lanGateway;
    }

    public void setLanGateway(final String lanGateway) {
        this.lanGateway = lanGateway;
    }

    public String getLanSubnet() {
        return lanSubnet;
    }

    public void setLanSubnet(final String lanSubnet) {
        this.lanSubnet = lanSubnet;
    }

    public String getDns1() {
        return dns1;
    }

    public void setDns1(final String dns1) {
        this.dns1 = dns1;
    }

    public String getDns2() {
        return dns2;
    }

    public void setDns2(final String dns2) {
        this.dns2 = dns2;
    }

    public String getOspConstIntervalEst() {
        return ospConstIntervalEst;
    }

    public void setOspConstIntervalEst(final String ospConstIntervalEst) {
        this.ospConstIntervalEst = ospConstIntervalEst;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(final String speed) {
        this.speed = speed;
    }

    public String getDownloadSpeed() {
        return downloadSpeed;
    }

    public void setDownloadSpeed(final String downloadSpeed) {
        this.downloadSpeed = downloadSpeed;
    }

    public String getUploadSpeed() {
        return uploadSpeed;
    }

    public void setUploadSpeed(final String uploadSpeed) {
        this.uploadSpeed = uploadSpeed;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(final String mediaType) {
        this.mediaType = mediaType;
    }

    public String getNetStatus() {
        return netStatus;
    }

    public void setNetStatus(final String netStatus) {
        this.netStatus = netStatus;
    }

    public String getLocationHours() {
        return locationHours;
    }

    public void setLocationHours(final String locationHours) {
        this.locationHours = locationHours;
    }

    public String getProductInstallInterval() {
        return productInstallInterval;
    }

    public void setProductInstallInterval(final String productInstallInterval) {
        this.productInstallInterval = productInstallInterval;
    }

    public boolean isExpediteOrder() {
        return expediteOrder;
    }

    public void setExpediteOrder(final boolean expediteOrder) {
        this.expediteOrder = expediteOrder;
    }

    public String getTrunkGroup() {
        return trunkGroup;
    }

    public void setTrunkGroup(final String trunkGroup) {
        this.trunkGroup = trunkGroup;
    }

    public String getConnectionHandoffType() {
        return connectionHandoffType;
    }

    public void setConnectionHandoffType(final String connectionHandoffType) {
        this.connectionHandoffType = connectionHandoffType;
    }

    public String getTieDownInfo() {
        return tieDownInfo;
    }

    public void setTieDownInfo(final String tieDownInfo) {
        this.tieDownInfo = tieDownInfo;
    }

    public String getType() {
        return type;
    }

    protected void setType(final String type) {
        this.type = type;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(final Long typeId) {
        this.typeId = typeId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(final String currency) {
        this.currency = currency;
    }

    public String getBuildingStatus() {
        return buildingStatus;
    }

    public void setBuildingStatus(final String buildingStatus) {
        this.buildingStatus = buildingStatus;
    }

    public String getIpFormat() {
        return ipFormat;
    }

    public void setIpFormat(final String ipFormat) {
        this.ipFormat = ipFormat;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(final String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(final Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Date getLastStatusChange() {
        return lastStatusChange;
    }

    public void setLastStatusChange(final Date lastStatusChange) {
        this.lastStatusChange = lastStatusChange;
    }

    public Date getCircuitTermEndDate() {
        return circuitTermEndDate;
    }

    public void setCircuitTermEndDate(final Date circuitTermEndDate) {
        this.circuitTermEndDate = circuitTermEndDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(final boolean active) {
        this.active = active;
    }

    public String getPersistedStatus() {
        return persistedStatus;
    }

    public Long getSortOrder() {
        return sortOrder;
    }

    public Integer getProgressPercentage() {
        return progressPercentage;
    }

    public void setProgressPercentage(final Integer progressPercentage) {
        this.progressPercentage = progressPercentage;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(final String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public Boolean getUpdateClient() {
        return updateClient;
    }

    public void setUpdateClient(final Boolean updateClient) {
        this.updateClient = updateClient;
    }

    public Boolean getEligibleForUpdate() {
        return eligibleForUpdate;
    }

    public void setEligibleForUpdate(final Boolean eligibleForUpdate) {
        this.eligibleForUpdate = eligibleForUpdate;
    }

    public String getServiceBilledTo() {
        return serviceBilledTo;
    }

    public void setServiceBilledTo(final String serviceBilledTo) {
        this.serviceBilledTo = serviceBilledTo;
    }

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(final String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public String getLegacyId() {
        return legacyId;
    }

    public void setLegacyId(final String legacyId) {
        this.legacyId = legacyId;
    }

    public String getLegacyDiaId() {
        return legacyDiaId;
    }

    public void setLegacyDiaId(final String legacyDiaId) {
        this.legacyDiaId = legacyDiaId;
    }

    public String getLegacyEquipment() {
        return legacyEquipment;
    }

    public void setLegacyEquipment(final String legacyEquipment) {
        this.legacyEquipment = legacyEquipment;
    }

    public boolean isCurrentInventory() {
        return isCurrentInventory;
    }

    public void setCurrentInventory(final boolean currentInventory) {
        isCurrentInventory = currentInventory;
    }

    public boolean getBillable() {
        return billable;
    }

    public void setBillable(final boolean billable) {
        this.billable = billable;
    }

    public String getDisconnectReason() {
        return disconnectReason;
    }

    public void setDisconnectReason(final String disconnectReason) {
        this.disconnectReason = disconnectReason;
    }

    public String getSubOrderType() {
        return subOrderType;
    }

    public void setSubOrderType(final String subOrderType) {
        this.subOrderType = subOrderType;
    }

    public BigDecimal getEarlyTerminationFee() {
        return earlyTerminationFee;
    }

    public void setEarlyTerminationFee(final BigDecimal earlyTerminationFee) {
        this.earlyTerminationFee = earlyTerminationFee;
    }

    public String getClientServiceInfo() {
        return clientServiceInfo;
    }

    public void setClientServiceInfo(final String clientServiceInfo) {
        this.clientServiceInfo = clientServiceInfo;
    }

    public String getClientServiceType() {
        return clientServiceType;
    }

    public void setClientServiceType(final String clientServiceType) {
        this.clientServiceType = clientServiceType;
    }

    public String getAccountPasscode() {
        return accountPasscode;
    }

    public void setAccountPasscode(final String accountPasscode) {
        this.accountPasscode = accountPasscode;
    }

    public Long getParentServiceId() {
        return parentServiceId;
    }

    public void setParentServiceId(final Long parentServiceId) {
        this.parentServiceId = parentServiceId;
    }

    public String getCostChangeReason() {
        return costChangeReason;
    }

    public void setCostChangeReason(final String costChangeReason) {
        this.costChangeReason = costChangeReason;
    }
    
    public BigDecimal getMacdCostChange() {
        return macdCostChange;
    }

    public void setMacdCostChange(final BigDecimal macdCostChange) {
        this.macdCostChange = macdCostChange;
    }

    public BigDecimal getMacdRevenueChange() {
        return macdRevenueChange;
    }

    public void setMacdRevenueChange(final BigDecimal macdRevenueChange) {
        this.macdRevenueChange = macdRevenueChange;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(final String projectName) {
        this.projectName = projectName;
    }

    public BigDecimal getAnnualRecurringCost() {
        return annualRecurringCost;
    }

    public void setAnnualRecurringCost(final BigDecimal annualRecurringCost) {
        this.annualRecurringCost = annualRecurringCost;
    }

    public String getRecordSource() {
        return recordSource;
    }

    public void setRecordSource(final String recordSource) {
        this.recordSource = recordSource;
    }

    public Boolean getIgnoreForRenewals() {
        return ignoreForRenewals;
    }

    public void setIgnoreForRenewals(final Boolean ignoreForRenewals) {
        this.ignoreForRenewals = ignoreForRenewals;
    }

    public boolean getLinked() {
        return linked;
    }

    public void setLinked(final boolean linked) {
        this.linked = linked;
    }

    public boolean getBundled() {
        return bundled;
    }

    public void setBundled(final boolean bundled) {
        this.bundled = bundled;
    }

    public boolean getLinkedBundledParent() {
        return linkedBundledParent;
    }

    public void setLinkedBundledParent(final boolean linkedBundledParent) {
        this.linkedBundledParent = linkedBundledParent;
    }

    public Long getLinkedBundledParentId() {
        return linkedBundledParentId;
    }

    public void setLinkedBundledParentId(final Long linkedBundledParentId) {
        this.linkedBundledParentId = linkedBundledParentId;
    }

    public Long getBillCycle() {
        return billCycle;
    }

    public void setBillCycle(final Long billCycle) {
        this.billCycle = billCycle;
    }

    public String getTspCode() {
        return tspCode;
    }

    public void setTspCode(final String tspCode) {
        this.tspCode = tspCode;
    }

    public Date getTspCodeExpirationDate() {
        return tspCodeExpirationDate;
    }

    public void setTspCodeExpirationDate(final Date tspCodeExpirationDate) {
        this.tspCodeExpirationDate = tspCodeExpirationDate;
    }

    public Long getInventoryServiceId() {
        return inventoryServiceId;
    }

    public void setInventoryServiceId(final Long inventoryServiceId) {
        this.inventoryServiceId = inventoryServiceId;
    }

    public Long getProvisioningServiceId() {
        return provisioningServiceId;
    }

    public void setProvisioningServiceId(final Long provisioningServiceId) {
        this.provisioningServiceId = provisioningServiceId;
    }

    public boolean getFinalUpdate() {
        return finalUpdate;
    }

    public void setFinalUpdate(final boolean finalUpdate) {
        this.finalUpdate = finalUpdate;
    }

    public boolean isBillToLocation() {
        return billToLocation;
    }

    public void setBillToLocation(final boolean billToLocation) {
        this.billToLocation = billToLocation;
    }

    public BigDecimal getOriginalMrc() {
        return originalMrc;
    }

    public void setOriginalMrc(final BigDecimal originalMrc) {
        this.originalMrc = originalMrc;
    }

    public BigDecimal getOriginalMrr() {
        return originalMrr;
    }

    public void setOriginalMrr(final BigDecimal originalMrr) {
        this.originalMrr = originalMrr;
    }

    public BigDecimal getOriginalNrr() {
        return originalNrr;
    }

    public void setOriginalNrr(final BigDecimal originalNrr) {
        this.originalNrr = originalNrr;
    }

    public boolean isManagedService() {
        return managedService;
    }

    public void setManagedService(final boolean managedService) {
        this.managedService = managedService;
    }

    public boolean isProductionImpacting() {
        return productionImpacting;
    }

    public void setProductionImpacting(final boolean productionImpacting) {
        this.productionImpacting = productionImpacting;
    }

    public boolean isAutoRenewal() {
        return autoRenewal;
    }

    public void setAutoRenewal(final boolean autoRenewal) {
        this.autoRenewal = autoRenewal;
    }

    public boolean isCoTerminus() {
        return coTerminus;
    }

    public void setCoTerminus(final boolean coTerminus) {
        this.coTerminus = coTerminus;
    }

    public String getRenewalCancelNoticePeriod() {
        return renewalCancelNoticePeriod;
    }

    public void setRenewalCancelNoticePeriod(final String renewalCancelNoticePeriod) {
        this.renewalCancelNoticePeriod = renewalCancelNoticePeriod;
    }

    public String getContractInfo() {
        return contractInfo;
    }

    public void setContractInfo(final String contractInfo) {
        this.contractInfo = contractInfo;
    }

    public boolean isEligibleForInventory() {
        return eligibleForInventory;
    }

    public void setEligibleForInventory(final boolean eligibleForInventory) {
        this.eligibleForInventory = eligibleForInventory;
    }

    public String getCrossConnectId() {
        return crossConnectId;
    }

    public void setCrossConnectId(final String crossConnectId) {
        this.crossConnectId = crossConnectId;
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

    public String getState() {
        return state;
    }

    public void setState(final String state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(final String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(final String country) {
        this.country = country;
    }

    public String getBillingEmail() {
        return billingEmail;
    }

    public void setBillingEmail(final String billingEmail) {
        this.billingEmail = billingEmail;
    }

    public String getExternalOrderReference() {
        return externalOrderReference;
    }

    public void setExternalOrderReference(final String externalOrderReference) {
        this.externalOrderReference = externalOrderReference;
    }

    public String getCustomerBillingInstructions() {
        return customerBillingInstructions;
    }

    public void setCustomerBillingInstructions(final String customerBillingInstructions) {
        this.customerBillingInstructions = customerBillingInstructions;
    }

    public String getFieldServicesProvider() {
        return fieldServicesProvider;
    }

    public void setFieldServicesProvider(final String fieldServicesProvider) {
        this.fieldServicesProvider = fieldServicesProvider;
    }

    public ServiceBrokerage getBrokerageInfo() {
        return brokerageInfo;
    }

    public void setBrokerageInfo(final ServiceBrokerage brokerageInfo) {
        this.brokerageInfo = brokerageInfo;
    }

    public String getShippingAddress1() {
        return shippingAddress1;
    }

    public void setShippingAddress1(final String shippingAddress1) {
        this.shippingAddress1 = shippingAddress1;
    }

    public String getShippingAddress2() {
        return shippingAddress2;
    }

    public void setShippingAddress2(final String shippingAddress2) {
        this.shippingAddress2 = shippingAddress2;
    }

    public String getShippingCity() {
        return shippingCity;
    }

    public void setShippingCity(final String shippingCity) {
        this.shippingCity = shippingCity;
    }

    public String getShippingState() {
        return shippingState;
    }

    public void setShippingState(final String shippingState) {
        this.shippingState = shippingState;
    }

    public String getShippingPostalCode() {
        return shippingPostalCode;
    }

    public void setShippingPostalCode(final String shippingPostalCode) {
        this.shippingPostalCode = shippingPostalCode;
    }

    public String getShippingCountry() {
        return shippingCountry;
    }

    public void setShippingCountry(final String shippingCountry) {
        this.shippingCountry = shippingCountry;
    }

    public String getShipTo() {
        return shipTo;
    }

    public void setShipTo(final String shipTo) {
        this.shipTo = shipTo;
    }

    public String getTechnicalNotes() {
        return technicalNotes;
    }

    public void setTechnicalNotes(final String technicalNotes) {
        this.technicalNotes = technicalNotes;
    }

    public Integer getNumberOfEndpoints() {
        return numberOfEndpoints;
    }

    public void setNumberOfEndpoints(final Integer numberOfEndpoints) {
        this.numberOfEndpoints = numberOfEndpoints;
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

    public String getMicrosoftLicensing() {
        return microsoftLicensing;
    }

    public void setMicrosoftLicensing(final String microsoftLicensing) {
        this.microsoftLicensing = microsoftLicensing;
    }

    public Integer getNumberOfUsers() {
        return numberOfUsers;
    }

    public void setNumberOfUsers(final Integer numberOfUsers) {
        this.numberOfUsers = numberOfUsers;
    }

    public String getOpportunityNum() {
        return opportunityNum;
    }

    public void setOpportunityNum(final String opportunityNum) {
        this.opportunityNum = opportunityNum;
    }

    public Boolean getIsTerminal() {
        return isTerminal;
    }

    public void setIsTerminal(final Boolean isTerminal) {
        this.isTerminal = isTerminal;
    }

    public boolean isMarkedForDeletion() {
        return markedForDeletion;
    }

    public void setMarkedForDeletion(final boolean markedForDeletion) {
        this.markedForDeletion = markedForDeletion;
    }

    public Date getDeletionDate() {
        return deletionDate;
    }

    public void setDeletionDate(final Date deletionDate) {
        this.deletionDate = deletionDate;
    }
}
