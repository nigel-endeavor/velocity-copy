package com.vertek.corporate.qto.report;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vertek.corporate.qto.common.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;

/**
 * A view need for WIP dashboards.
 * @author llevit
 */
@Entity
@Table(name = "v_wip_service")
public class WipServiceView implements BaseEntity<Long>, WipService  {

    @Id
    @Column(name = "service_id")
    private Long serviceId;

    @Column(name = "location_id")
    private Long locationId;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "master_company_name")
    private String masterCompanyName;

    @Column(name = "provisioner")
    private Long provisionerId;

    @Column(name = "vertek_project_manager")
    private Long vertekProjectManagerId;

    @Column(name = "client_project_manager")
    private String clientProjectManager;

    @Transient
    private String provisioner;

    @Transient
    private String vertekProjectManager;

    @Column(name = "client_order_id")
    private String clientOrderId;

    @Column(name = "client_location_id")
    private String clientLocationId;

    @Column(name = "location_name")
    private String locationName;

    @Column(name = "address_1")
    private String address1;

    @Column(name = "address_2")
    private String address2;

    @Column(name = "city")
    private String city;

    @Column(name = "state_province")
    private String stateProvince;

    @Column(name = "client_service_id")
    private String clientServiceId;

    @Column(name = "provider")
    private String provider;

    @Column(name = "service_status")
    private String serviceStatus;

    @Column(name = "service_type")
    private String serviceType;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "data_provisioning_complete_date")
    private Date dataProvisioningCompleteDate;

    @Column(name = "service_mrc")
    private BigDecimal serviceMrc;

    @Column(name = "service_billed_to")
    private String serviceBilledTo;

    @Column(name = "service_complete_date")
    private Date completeDate;

    @JsonIgnore
    @Column(name = "tenant_id")
    private Long tenantId;

    @JsonIgnore
    @Column(name = "master_customer_id")
    private Long masterCustomerId;

    @Column(name = "current_inventory")
    private boolean currentInventory;

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(final Long serviceId) {
        this.serviceId = serviceId;
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

    public String getMasterCompanyName() {
        return masterCompanyName;
    }

    public void setMasterCompanyName(final String masterCompanyName) {
        this.masterCompanyName = masterCompanyName;
    }

    public Long getProvisionerId() {
        return provisionerId;
    }

    public void setProvisionerId(final Long provisionerId) {
        this.provisionerId = provisionerId;
    }

    public Long getVertekProjectManagerId() {
        return vertekProjectManagerId;
    }

    public void setVertekProjectManagerId(final Long vertekProjectManagerId) {
        this.vertekProjectManagerId = vertekProjectManagerId;
    }

    public String getProvisioner() {
        return provisioner;
    }

    public void setProvisioner(final String provisioner) {
        this.provisioner = provisioner;
    }

    public String getVertekProjectManager() {
        return vertekProjectManager;
    }

    public void setVertekProjectManager(final String vertekProjectManager) {
        this.vertekProjectManager = vertekProjectManager;
    }

    public String getClientProjectManager() {
        return clientProjectManager;
    }

    public void setClientProjectManager(final String clientProjectManager) {
        this.clientProjectManager = clientProjectManager;
    }

    public String getClientOrderId() {
        return clientOrderId;
    }

    public void setClientOrderId(final String clientOrderId) {
        this.clientOrderId = clientOrderId;
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

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(final String locationName) {
        this.locationName = locationName;
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

    public String getClientServiceId() {
        return clientServiceId;
    }

    public void setClientServiceId(final String clientServiceId) {
        this.clientServiceId = clientServiceId;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(final String provider) {
        this.provider = provider;
    }

    public String getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(final String serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(final String serviceType) {
        this.serviceType = serviceType;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(final Boolean active) {
        this.active = active;
    }

    public Date getDataProvisioningCompleteDate() {
        return dataProvisioningCompleteDate;
    }

    public void setDataProvisioningCompleteDate(final Date dataProvisioningCompleteDate) {
        this.dataProvisioningCompleteDate = dataProvisioningCompleteDate;
    }

    public BigDecimal getServiceMrc() {
        return serviceMrc;
    }

    public void setServiceMrc(final BigDecimal serviceMrc) {
        this.serviceMrc = serviceMrc;
    }

    public String getServiceBilledTo() {
        return serviceBilledTo;
    }

    public void setServiceBilledTo(final String serviceBilledTo) {
        this.serviceBilledTo = serviceBilledTo;
    }

    public Date getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(final Date completeDate) {
        this.completeDate = completeDate;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(final Long tenantId) {
        this.tenantId = tenantId;
    }

    public Long getMasterCustomerId() {
        return masterCustomerId;
    }

    public void setMasterCustomerId(final Long masterCustomerId) {
        this.masterCustomerId = masterCustomerId;
    }

    @Override
    public Long getId() {
        return serviceId;
    }

    @Override
    public boolean isNew() {
        return false;
    }

    public boolean isCurrentInventory() {
        return currentInventory;
    }

    public void setCurrentInventory(final boolean currentInventory) {
        this.currentInventory = currentInventory;
    }
}
