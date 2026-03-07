package com.vertek.corporate.qto.service.snapshot;

import com.vertek.corporate.qto.common.AbstractMasterCustomerOwnedEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author rcasey
 * @since 3/22/2024
 */
@Entity
@Table(name = "service_snapshot")
public class ServiceSnapshot extends AbstractMasterCustomerOwnedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_snapshot_id")
    private Long id;

    @Column(name = "snapshot_date")
    private Date snapshotDate;

    @Column(name = "service_id")
    private Long serviceId;

    @Column(name = "location_id")
    private Long locationId;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "end_customer_id")
    private Long endCustomerId;

    @Column(name = "client_service_id")
    private String clientServiceId;

    @Column(name = "client_location_id")
    private String clientLocationId;

    @Column(name = "service_type")
    private String serviceType;

    @Column(name = "service_billed_to")
    private String serviceBilledTo;

    @Column(name = "provider")
    private String provider;

    @Column(name = "sub_product_type")
    private String subProductType;

    @Column(name = "status")
    private String status;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "current_inventory")
    private Boolean currentInventory;

    @Column(name = "data_provisioning_complete_date")
    private Date dataProvisioningCompleteDate;

    @Column(name = "complete_date")
    private Date completeDate;

    @Column(name = "mrc")
    private BigDecimal mrc;

    @Column(name = "nrc")
    private BigDecimal nrc;

    @Column(name = "annual_recurring_cost")
    private BigDecimal annualRecurringCost;

    @Column(name = "osp")
    private BigDecimal osp;

    @Column(name = "icb")
    private BigDecimal icb;

    @Override
    public Long getId() {
        return id;
    }

    public Date getSnapshotDate() {
        return snapshotDate;
    }

    public void setSnapshotDate(final Date snapshotDate) {
        this.snapshotDate = snapshotDate;
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

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(final Long orderId) {
        this.orderId = orderId;
    }

    public Long getEndCustomerId() {
        return endCustomerId;
    }

    public void setEndCustomerId(final Long endCustomerId) {
        this.endCustomerId = endCustomerId;
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

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(final String serviceType) {
        this.serviceType = serviceType;
    }

    public String getServiceBilledTo() {
        return serviceBilledTo;
    }

    public void setServiceBilledTo(final String serviceBilledTo) {
        this.serviceBilledTo = serviceBilledTo;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(final String provider) {
        this.provider = provider;
    }

    public String getSubProductType() {
        return subProductType;
    }

    public void setSubProductType(final String subProductType) {
        this.subProductType = subProductType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(final Boolean active) {
        this.active = active;
    }

    public Boolean getCurrentInventory() {
        return currentInventory;
    }

    public void setCurrentInventory(final Boolean currentInventory) {
        this.currentInventory = currentInventory;
    }

    public Date getDataProvisioningCompleteDate() {
        return dataProvisioningCompleteDate;
    }

    public void setDataProvisioningCompleteDate(final Date dataProvisioningCompleteDate) {
        this.dataProvisioningCompleteDate = dataProvisioningCompleteDate;
    }

    public Date getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(final Date completeDate) {
        this.completeDate = completeDate;
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

    public BigDecimal getAnnualRecurringCost() {
        return annualRecurringCost;
    }

    public void setAnnualRecurringCost(final BigDecimal annualRecurringCost) {
        this.annualRecurringCost = annualRecurringCost;
    }

    public BigDecimal getOsp() {
        return osp;
    }

    public void setOsp(final BigDecimal osp) {
        this.osp = osp;
    }

    public BigDecimal getIcb() {
        return icb;
    }

    public void setIcb(final BigDecimal icb) {
        this.icb = icb;
    }
}
