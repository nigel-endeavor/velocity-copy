package com.vertek.corporate.qto.location;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vertek.corporate.qto.brokerage.ServiceBrokerage;
import com.vertek.corporate.qto.common.AbstractMasterCustomerOwnedEntity;
import com.vertek.corporate.qto.common.SecurityUtils;
import com.vertek.corporate.qto.contact.location.LocationContact;
import com.vertek.corporate.qto.service.OrderType;
import com.vertek.corporate.qto.service.Service;
import com.vertek.corporate.qto.service.TerminalServiceStatuses;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author rcasey
 * @since 1/10/2023
 */
@Entity
@Table(name = "location")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Location extends AbstractMasterCustomerOwnedEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    private Long id;

    @Column(name="legacy_id")
    private String legacyId;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "client_location_id")
    private String clientLocationId;

    @Column(name = "client_location_type")
    private String clientLocationType;

    @Column(name = "client_location_info")
    private String clientLocationInfo;

    @Column(name = "quote_location_id")
    private String quoteLocationId;

    @Column(name = "location_name")
    private String name;

    @Column(name = "location_status")
    private String status;

    @Column(name = "building_type")
    private String buildingType;

    @Transient
    private BigDecimal mrc;

    @Transient
    private BigDecimal nrc;

    @Transient
    private BigDecimal mrr;

    @Transient
    private BigDecimal nrr;

    @Transient
    private BigDecimal icb;

    @Transient
    private BigDecimal osp;

    @Transient
    private BigDecimal annualRecurringCost;

    @Transient
    private BigDecimal inventoryMrc;

    @Transient
    private BigDecimal inventoryNrc;

    @Transient
    private BigDecimal inventoryMrr;

    @Transient
    private BigDecimal inventoryNrr;

    @Transient
    private BigDecimal inventoryIcb;

    @Transient
    private BigDecimal inventoryOsp;

    @Transient
    private BigDecimal inventoryAnnualRecurringCost;

    @Transient
    private BigDecimal commissionableMrc;

    @Transient
    private BigDecimal commissionableNrc;

    @Transient
    private BigDecimal commissionableArc;

    @Column(name = "timezone")
    private String timezone;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "record_source")
    private String recordSource;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "location_id", referencedColumnName = "location_id")
    @Where(clause = "marked_for_deletion = false")
    @OrderBy("linkedBundledParentId DESC, linkedBundledParent DESC, sortOrder ASC, id ASC")
    private List<Service> services = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinColumn(name = "location_id", referencedColumnName = "location_id")
    @Where(clause = "current_inventory = true and marked_for_deletion = false")
    @OrderBy("linkedBundledParentId DESC, linkedBundledParent DESC, inventorySortOrder ASC, id ASC")
    private List<Service> inventoryServices = new ArrayList<>();

    @Column(name = "last_update_by")
    private String lastUpdateBy;

    @Column(name = "last_update_date")
    private Date lastUpdateDate;

    @Column(name = "requirement_template_id")
    private Long requirementTemplateId;

    @Column(name = "progress_percentage")
    private Integer progressPercentage = 0;

    @Column(name = "level_of_effort")
    private String levelOfEffort;

    @JsonIgnore
    @Formula("(SELECT CASE " +
                "WHEN location_status = 'Location Complete' THEN 1 " +
                "WHEN location_status = 'Location Cancelled' THEN 2 " +
                "WHEN location_status = 'Change In Assignment' THEN 3 " +
                "ELSE 0 END)")
    private Long sortOrder;

    @Transient
    private LocationContact lcon;

    @Column(name="legacy_transaction_type")
    private String legacyTransactionType;

    @Column(name = "legacy_transaction_amount")
    private BigDecimal legacyTransactionAmount;

    @Column(name = "active")
    private boolean active;

    @Column(name = "parent_location_id")
    private Long parentLocationId;

    @Column(name = "location_description")
    private String description;

    @Column(name = "inventory_location_id")
    private Long inventoryLocationId;

    @Column(name = "provisioning_location_id")
    private Long provisioningLocationId;

    @Column(name = "final_update")
    private boolean finalUpdate;

    @Column(name = "current_inventory")
    private boolean isCurrentInventory;

    @Column(name = "eligible_for_inventory")
    private boolean eligibleForInventory;

    @Transient
    private Boolean isDisconnect;

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

    @Column(name = "marked_for_deletion")
    private boolean markedForDeletion;

    @Column(name = "deletion_date")
    private Date deletionDate;

    @Transient
    private BigDecimal grossProfitMrcOverride = BigDecimal.ZERO;

    @Transient
    private BigDecimal grossProfitOverride = BigDecimal.ZERO;

    @Transient
    private BigDecimal repGrossProfitProduction = BigDecimal.ZERO;

    @Transient
    private BigDecimal totalContractValue = BigDecimal.ZERO;

    @Transient
    private BigDecimal upliftMrc = BigDecimal.ZERO;

    @Transient
    private BigDecimal subaccountMrc = BigDecimal.ZERO;

    @PrePersist
    @PreUpdate
    void preUpdate() {
        setLastUpdateDate(new Date());
        setLastUpdateBy(SecurityUtils.getLoggedInUser());
    }

    @PostLoad
    void postLoad() {
        // Do not include services that have status of "Cancelled" or "Change In Assignment"
        // in the total MRC, NRC, ICB, OSP
        List<Service> filteredServices = services.stream()
                .filter(s -> !TerminalServiceStatuses.CANCELLED.getStatus().equals(s.getStatus())
                        && !TerminalServiceStatuses.CHANGE_IN_ASSIGNMENT.getStatus().equals(s.getStatus()))
                .collect(java.util.stream.Collectors.toList());
        List<Service> filteredInventoryServices = inventoryServices.stream()
                .filter(s -> !TerminalServiceStatuses.CANCELLED.getStatus().equals(s.getStatus())
                        && !TerminalServiceStatuses.CHANGE_IN_ASSIGNMENT.getStatus().equals(s.getStatus())
                        && s.isActive())
                .collect(java.util.stream.Collectors.toList());
        List<Service> filteredDisconnectServices = services.stream()
                .filter(s -> OrderType.DISCONNECT.getOrderType().equals(s.getOrderType()))
                .collect(java.util.stream.Collectors.toList());

        this.isDisconnect = !filteredDisconnectServices.isEmpty();

        // Calculate the total MRC, NRC, ICB, OSP, and Annual Recurring Cost
        this.mrc = filteredServices.stream().map(Service::getTotalMrc).reduce(BigDecimal.ZERO, BigDecimal::add);
        this.nrc = filteredServices.stream().map(Service::getTotalNrc).reduce(BigDecimal.ZERO, BigDecimal::add).add(
                filteredServices.stream().map(Service::getEarlyTerminationFee)
                        .reduce(BigDecimal.ZERO, BigDecimal::add));
        this.mrr = filteredServices.stream().map(Service::getTotalMrr).reduce(BigDecimal.ZERO, BigDecimal::add);
        this.nrr = filteredServices.stream().map(Service::getTotalNrr).reduce(BigDecimal.ZERO, BigDecimal::add).add(
                filteredServices.stream().map(Service::getEarlyTerminationFee)
                        .reduce(BigDecimal.ZERO, BigDecimal::add));
        this.icb = filteredServices.stream().map(Service::getIcb).reduce(BigDecimal.ZERO, BigDecimal::add);
        this.osp = filteredServices.stream().map(Service::getOsp).reduce(BigDecimal.ZERO, BigDecimal::add);
        this.annualRecurringCost = filteredServices.stream().map(Service::getAnnualRecurringCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        // Calculate the total MRC, NRC, ICB, OSP, and Annual Recurring Cost for inventory services
        this.inventoryMrc = filteredInventoryServices.stream().map(Service::getTotalMrc)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        this.inventoryNrc = filteredInventoryServices.stream().map(Service::getTotalNrc)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .add(filteredInventoryServices.stream().map(Service::getEarlyTerminationFee)
                        .reduce(BigDecimal.ZERO, BigDecimal::add));
        this.inventoryMrr = filteredInventoryServices.stream().map(Service::getTotalMrr)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        this.inventoryNrr = filteredInventoryServices.stream().map(Service::getTotalNrr)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .add(filteredInventoryServices.stream().map(Service::getEarlyTerminationFee)
                        .reduce(BigDecimal.ZERO, BigDecimal::add));
        this.inventoryIcb = filteredInventoryServices.stream().map(Service::getIcb)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        this.inventoryOsp = filteredInventoryServices.stream().map(Service::getOsp)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        this.inventoryAnnualRecurringCost = filteredInventoryServices.stream().map(Service::getAnnualRecurringCost)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        // Calculate commissionable MRC, NRC, and ARC, use a common collection to avoid duplicate code
        List<Service> services = null;
        if (this.isCurrentInventory) {
            services = filteredInventoryServices;
        } else {
            services = filteredServices;
        }
        this.commissionableMrc = services.stream().map(Service :: getBrokerageInfo)
                .filter(Objects::nonNull)
                .map(ServiceBrokerage::getCommissionableMrc)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        this.commissionableNrc = services.stream().map(Service :: getBrokerageInfo)
                .filter(Objects::nonNull)
                .map(ServiceBrokerage::getCommissionableNrc)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        this.commissionableArc = services.stream().map(Service :: getBrokerageInfo)
                .filter(Objects::nonNull)
                .map(ServiceBrokerage::getCommissionableArc)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        this.grossProfitMrcOverride = services.stream().map(Service :: getBrokerageInfo)
                .filter(Objects::nonNull)
                .map(ServiceBrokerage::getGrossProfitMrcOverride)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        this.grossProfitOverride = services.stream().map(Service :: getBrokerageInfo)
                .filter(Objects::nonNull)
                .map(ServiceBrokerage::getGrossProfitOverride)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        this.repGrossProfitProduction = services.stream().map(Service :: getBrokerageInfo)
                .filter(Objects::nonNull)
                .map(ServiceBrokerage::getRepGrossProfitProduction)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        this.totalContractValue = services.stream().map(Service :: getBrokerageInfo)
                .filter(Objects::nonNull)
                .map(ServiceBrokerage::getTotalContractValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        this.upliftMrc = services.stream().map(Service :: getBrokerageInfo)
                .filter(Objects::nonNull)
                .map(ServiceBrokerage::getUpliftMrc)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        this.subaccountMrc = services.stream().map(Service :: getBrokerageInfo)
                .filter(Objects::nonNull)
                .map(ServiceBrokerage::getSubaccountMrc)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

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

    public String getClientLocationId() {
        return clientLocationId;
    }

    public void setClientLocationId(final String clientLocationId) {
        this.clientLocationId = clientLocationId;
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

    public String getQuoteLocationId() {
        return quoteLocationId;
    }

    public void setQuoteLocationId(final String quoteLocationId) {
        this.quoteLocationId = quoteLocationId;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public String getBuildingType() {
        return buildingType;
    }

    public void setBuildingType(final String buildingType) {
        this.buildingType = buildingType;
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

    public BigDecimal getIcb() {
        return icb;
    }

    public void setIcb(final BigDecimal icb) {
        this.icb = icb;
    }

    public BigDecimal getOsp() {
        return osp;
    }

    public void setOsp(final BigDecimal osp) {
        this.osp = osp;
    }

    public BigDecimal getAnnualRecurringCost() {
        return annualRecurringCost;
    }

    public void setAnnualRecurringCost(final BigDecimal annualRecurringCost) {
        this.annualRecurringCost = annualRecurringCost;
    }

    public BigDecimal getInventoryMrc() {
        return inventoryMrc;
    }

    public void setInventoryMrc(final BigDecimal inventoryMrc) {
        this.inventoryMrc = inventoryMrc;
    }

    public BigDecimal getInventoryNrc() {
        return inventoryNrc;
    }

    public void setInventoryNrc(final BigDecimal inventoryNrc) {
        this.inventoryNrc = inventoryNrc;
    }

    public BigDecimal getInventoryMrr() {
        return inventoryMrr;
    }

    public void setInventoryMrr(final BigDecimal inventoryMrr) {
        this.inventoryMrr = inventoryMrr;
    }

    public BigDecimal getInventoryNrr() {
        return inventoryNrr;
    }

    public void setInventoryNrr(final BigDecimal inventoryNrr) {
        this.inventoryNrr = inventoryNrr;
    }

    public BigDecimal getInventoryIcb() {
        return inventoryIcb;
    }

    public void setInventoryIcb(final BigDecimal inventoryIcb) {
        this.inventoryIcb = inventoryIcb;
    }

    public BigDecimal getInventoryOsp() {
        return inventoryOsp;
    }

    public void setInventoryOsp(final BigDecimal inventoryOsp) {
        this.inventoryOsp = inventoryOsp;
    }

    public BigDecimal getInventoryAnnualRecurringCost() {
        return inventoryAnnualRecurringCost;
    }

    public void setInventoryAnnualRecurringCost(final BigDecimal inventoryAnnualRecurringCost) {
        this.inventoryAnnualRecurringCost = inventoryAnnualRecurringCost;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(final String timezone) {
        this.timezone = timezone;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(final String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(final List<Service> services) {
        this.services = services;
    }

    public List<Service> getInventoryServices() {
        return inventoryServices;
    }

    public void setInventoryServices(final List<Service> inventoryServices) {
        this.inventoryServices = inventoryServices;
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

    public Long getRequirementTemplateId() {
        return requirementTemplateId;
    }

    public void setRequirementTemplateId(final Long requirementTemplateId) {
        this.requirementTemplateId = requirementTemplateId;
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

    public String getLegacyId() {
        return legacyId;
    }

    public void setLegacyId(final String legacyId) {
        this.legacyId = legacyId;
    }

    public LocationContact getLcon() {
        return lcon;
    }

    public void setLcon(final LocationContact lcon) {
        this.lcon = lcon;
    }

    public String getLevelOfEffort() {
        return levelOfEffort;
    }

    public void setLevelOfEffort(final String levelOfEffort) {
        this.levelOfEffort = levelOfEffort;
    }

    public String getLegacyTransactionType() {
        return legacyTransactionType;
    }

    public void setLegacyTransactionType(final String legacyTransactionType) {
        this.legacyTransactionType = legacyTransactionType;
    }

    public BigDecimal getLegacyTransactionAmount() {
        return legacyTransactionAmount;
    }

    public void setLegacyTransactionAmount(final BigDecimal legacyTransactionAmount) {
        this.legacyTransactionAmount = legacyTransactionAmount;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(final boolean active) {
        this.active = active;
    }

    public Long getParentLocationId() {
        return parentLocationId;
    }

    public void setParentLocationId(final Long parentLocationId) {
        this.parentLocationId = parentLocationId;
    }

    public Boolean getIsDisconnect() {
        return isDisconnect;
    }

    public void setIsDisconnect(final Boolean isDisconnect) {
        this.isDisconnect = isDisconnect;
    }

    public String getRecordSource() {
        return recordSource;
    }

    public void setRecordSource(final String recordSource) {
        this.recordSource = recordSource;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public Long getInventoryLocationId() {
        return inventoryLocationId;
    }

    public void setInventoryLocationId(final Long inventoryLocationId) {
        this.inventoryLocationId = inventoryLocationId;
    }

    public Long getProvisioningLocationId() {
        return provisioningLocationId;
    }

    public void setProvisioningLocationId(final Long provisioningLocationId) {
        this.provisioningLocationId = provisioningLocationId;
    }

    public boolean getFinalUpdate() {
        return finalUpdate;
    }

    public void setFinalUpdate(final boolean finalUpdate) {
        this.finalUpdate = finalUpdate;
    }

    public boolean isCurrentInventory() {
        return isCurrentInventory;
    }

    public void setCurrentInventory(final boolean isCurrentInventory) {
        this.isCurrentInventory = isCurrentInventory;
    }

    public BigDecimal getCommissionableMrc() {
        return commissionableMrc;
    }

    public void setCommissionableMrc(final BigDecimal commissionableMrc) {
        this.commissionableMrc = commissionableMrc;
    }

    public BigDecimal getCommissionableNrc() {
        return commissionableNrc;
    }

    public void setCommissionableNrc(final BigDecimal commissionableNrc) {
        this.commissionableNrc = commissionableNrc;
    }

    public BigDecimal getCommissionableArc() {
        return commissionableArc;
    }

    public void setCommissionableArc(final BigDecimal commissionableArc) {
        this.commissionableArc = commissionableArc;
    }

    public boolean isEligibleForInventory() {
        return eligibleForInventory;
    }

    public void setEligibleForInventory(final boolean eligibleForInventory) {
        this.eligibleForInventory = eligibleForInventory;
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

    public BigDecimal getGrossProfitMrcOverride() {
        return grossProfitMrcOverride;
    }

    public void setGrossProfitMrcOverride(final BigDecimal grossProfitMrcOverride) {
        this.grossProfitMrcOverride = grossProfitMrcOverride;
    }

    public BigDecimal getGrossProfitOverride() {
        return grossProfitOverride;
    }

    public void setGrossProfitOverride(final BigDecimal grossProfitOverride) {
        this.grossProfitOverride = grossProfitOverride;
    }

    public BigDecimal getRepGrossProfitProduction() {
        return repGrossProfitProduction;
    }

    public void setRepGrossProfitProduction(final BigDecimal repGrossProfitProduction) {
        this.repGrossProfitProduction = repGrossProfitProduction;
    }

    public BigDecimal getTotalContractValue() {
        return totalContractValue;
    }

    public void setTotalContractValue(final BigDecimal totalContractValue) {
        this.totalContractValue = totalContractValue;
    }

    public BigDecimal getUpliftMrc() {
        return upliftMrc;
    }

    public void setUpliftMrc(final BigDecimal upliftMrc) {
        this.upliftMrc = upliftMrc;
    }

    public BigDecimal getSubaccountMrc() {
        return subaccountMrc;
    }

    public void setSubaccountMrc(final BigDecimal subaccountMrc) {
        this.subaccountMrc = subaccountMrc;
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
