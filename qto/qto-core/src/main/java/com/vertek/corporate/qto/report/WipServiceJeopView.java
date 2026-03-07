package com.vertek.corporate.qto.report;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vertek.corporate.qto.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.util.Date;

/**
 * A view need for WIP dashboards.
 * @author llevit
 */
@Entity
@Table(name = "v_wip_service_jeop")
public class WipServiceJeopView implements BaseEntity<Long>, WipService  {

    @Id
    @Column(name = "jeop_instance_id")
    Long jeopInstanceId;

    @Column(name = "service_id")
    Long serviceId;

    @Column(name = "location_id")
    Long locationId;

    @Column(name = "order_id")
    Long orderId;

    @Column(name = "company_name")
    String companyName;

    @Column(name = "master_company_name")
    private String masterCompanyName;

    @Column(name = "provisioner")
    Long provisionerId;

    @Column(name = "vertek_project_manager")
    Long vertekProjectManagerId;

    @Column(name = "client_project_manager")
    String clientProjectManager;

    @Transient
    String provisioner;

    @Transient
    String vertekProjectManager;

    @Column(name = "client_order_id")
    String clientOrderId ;

    @Column(name = "client_location_id")
    String clientLocationId;

    @Column(name = "location_name")
    String locationName;

    @Column(name = "address_1")
    String address1;

    @Column(name = "address_2")
    String address2;

    @Column(name = "city")
    String city;

    @Column(name = "state_province")
    String stateProvince;

    @Column(name = "client_service_id")
    String clientServiceId;

    @Column(name = "provider")
    String provider;

    @Column(name = "service_status")
    String serviceStatus;

    @Column(name = "service_type")
    String serviceType;

    @Column(name = "jeop_description")
    String jeopDescription;

    @Column(name = "start_date")
    Date startDate;

    @Column(name = "end_date")
    Date endDate;

    @Column(name = "responsibility")
    String responsibility;

    @Column(name = "assigned_to")
    String assignedTo;

    @JsonIgnore
    @Column(name = "tenant_id")
    private Long tenantId;

    @JsonIgnore
    @Column(name = "master_customer_id")
    private Long masterCustomerId;

    @Column(name = "service_billed_to")
    private String serviceBilledTo;

    public Long getJeopInstanceId() {
        return jeopInstanceId;
    }

    public void setJeopInstanceId(final Long jeopInstanceId) {
        this.jeopInstanceId = jeopInstanceId;
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

    public String getJeopDescription() {
        return jeopDescription;
    }

    public void setJeopDescription(final String jeopDescription) {
        this.jeopDescription = jeopDescription;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(final Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(final Date endDate) {
        this.endDate = endDate;
    }

    public String getResponsibility() {
        return responsibility;
    }

    public void setResponsibility(final String responsibility) {
        this.responsibility = responsibility;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(final String assignedTo) {
        this.assignedTo = assignedTo;
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
        return jeopInstanceId;
    }

    @Override
    public boolean isNew() {
        return false;
    }

    public String getServiceBilledTo() {
        return serviceBilledTo;
    }

    public void setServiceBilledTo(final String serviceBilledTo) {
        this.serviceBilledTo = serviceBilledTo;
    }
}
