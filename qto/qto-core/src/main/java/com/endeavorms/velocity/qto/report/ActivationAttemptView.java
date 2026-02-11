package com.endeavorms.velocity.qto.report;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.endeavorms.velocity.qto.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;
@Entity
@Table(name = "v_activation_attempts")
public class ActivationAttemptView implements BaseEntity<Long> {

    @Id
    @Column(name= "activation_attempt_id")
    private Long activationAttemptId;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "master_company_name")
    private String masterCompanyName;

    @JsonIgnore
    @Column(name = "tenant_id")
    private Long tenantId;

    @JsonIgnore
    @Column(name = "master_customer_id")
    private Long masterCustomerId;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "client_order_id")
    private String clientOrderId;

    @Column(name = "location_id")
    private Long locationId;

    @Column(name = "location_name")
    private String locationName;

    @Column(name = "client_location_id")
    private String clientLocationId;

    @Column(name = "service_id")
    private Long serviceId;

    @Column(name = "client_service_id")
    private String clientServiceId;

    @Column(name = "provider")
    private String provider;

    @Column(name = "service_type")
    private String serviceType;

    @Column(name = "client_location_type")
    private String clientLocationType;

    @Column(name = "client_location_info")
    private String clientLocationInfo;

    @Column(name = "attempt_number")
    private Long attemptNumber;

    @Column(name = "field_tech_check_in")
    private Date fieldTechCheckIn;

    @Column(name = "field_tech_check_out")
    private Date fieldTechCheckOut;

    @Column(name = "aa_month")
    private Long aaMonth;

    @Column(name = "aa_year")
    private Long aaYear;

    @Column(name = "service_activation_interval")
    private Integer serviceActivationInterval;

    @Column(name = "scheduled_attempt_status")
    private String scheduledAttemptStatus;

    @Column(name = "location_activation_interval")
    private Long locationActivationInterval;

    @Column(name = "data_provisioning_complete")
    private Date dataProvisioningComplete;

    @Column(name = "service_complete")
    private Date serviceComplete;

    @Column(name = "service_billed_to")
    private String serviceBilledTo;


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

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(final Long orderId) {
        this.orderId = orderId;
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

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(final String locationName) {
        this.locationName = locationName;
    }

    public String getClientLocationId() {
        return clientLocationId;
    }

    public void setClientLocationId(final String clientLocationId) {
        this.clientLocationId = clientLocationId;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(final Long serviceId) {
        this.serviceId = serviceId;
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

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(final String serviceType) {
        this.serviceType = serviceType;
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

    public Long getAttemptNumber() {
        return attemptNumber;
    }

    public void setAttemptNumber(final Long attemptNumber) {
        this.attemptNumber = attemptNumber;
    }

    public Date getFieldTechCheckIn() {
        return fieldTechCheckIn;
    }

    public void setFieldTechCheckIn(final Date fieldTechCheckIn) {
        this.fieldTechCheckIn = fieldTechCheckIn;
    }

    public Date getFieldTechCheckOut() {
        return fieldTechCheckOut;
    }

    public void setFieldTechCheckOut(final Date fieldTechCheckOut) {
        this.fieldTechCheckOut = fieldTechCheckOut;
    }

    public Long getAaMonth() {
        return aaMonth;
    }

    public void setAaMonth(final Long aaMonth) {
        this.aaMonth = aaMonth;
    }

    public Long getAaYear() {
        return aaYear;
    }

    public void setAaYear(final Long aaYear) {
        this.aaYear = aaYear;
    }

    public Integer getServiceActivationInterval() {
        return serviceActivationInterval;
    }

    public void setServiceActivationInterval(final Integer serviceActivationInterval) {
        this.serviceActivationInterval = serviceActivationInterval;
    }

    public Long getLocationActivationInterval() {
        return locationActivationInterval;
    }

    public void setLocationActivationInterval(final Long locationActivationInterval) {
        this.locationActivationInterval = locationActivationInterval;
    }

    public Date getDataProvisioningComplete() {
        return dataProvisioningComplete;
    }

    public void setDataProvisioningComplete(final Date dataProvisioningComplete) {
        this.dataProvisioningComplete = dataProvisioningComplete;
    }

    public Date getServiceComplete() {
        return serviceComplete;
    }

    public void setServiceComplete(final Date serviceComplete) {
        this.serviceComplete = serviceComplete;
    }

    public String getScheduledAttemptStatus() {
        return scheduledAttemptStatus;
    }

    public void setScheduledAttemptStatus(final String scheduledAttemptStatus) {
        this.scheduledAttemptStatus = scheduledAttemptStatus;
    }

    @Override
    public Long getId() {
        return activationAttemptId;
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
