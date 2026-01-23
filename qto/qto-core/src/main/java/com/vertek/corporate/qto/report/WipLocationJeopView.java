package com.vertek.corporate.qto.report;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vertek.corporate.qto.common.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * A Location based view needed for WIP dashboards.
 * @author llevit
 */
@Entity
@Table(name = "v_wip_location_jeop")
public class WipLocationJeopView implements BaseEntity<Long>, WipService  {

    @Id
    @Column(name = "jeop_instance_id")
    private Long jeopInstanceId;

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
    private String clientOrderId ;

    @Column(name = "client_location_id")
    private String clientLocationId;

    @Column(name = "client_location_type")
    private String clientLocationType;

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

    @Column(name = "location_status")
    private String locationStatus;

    // jeop related
    @Column(name = "jeop_description")
    private String jeopDescription;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "responsibility")
    private String responsibility;

    @Column(name = "assigned_to")
    private String assignedTo;

    @JsonIgnore
    @Column(name = "tenant_id")
    private Long tenantId;

    @JsonIgnore
    @Column(name = "master_customer_id")
    private Long masterCustomerId;

    public Long getJeopInstanceId() {
        return jeopInstanceId;
    }

    public void setJeopInstanceId(final Long jeopInstanceId) {
        this.jeopInstanceId = jeopInstanceId;
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

    public String getClientLocationType() {
        return clientLocationType;
    }

    public void setClientLocationType(final String clientLocationType) {
        this.clientLocationType = clientLocationType;
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

    public String getLocationStatus() {
        return locationStatus;
    }

    public void setLocationStatus(final String locationStatus) {
        this.locationStatus = locationStatus;
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
}
