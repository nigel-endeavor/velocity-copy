package com.endeavorms.velocity.qto.report;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.endeavorms.velocity.qto.common.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;

/**
 * A view need for Provider dashboards.
 * @author llevit
 */
@Entity
@Table(name = "v_provider_intervals")
public class ProviderIntervalsView implements BaseEntity<Long> {

    @Id
    @Column(name = "interval_instance_id")
    Long intervalInstanceId;

    @Column(name = "company_name")
    String companyName;

    @Column(name = "master_company_name")
    private String masterCompanyName;

    @Column(name = "order_id")
    Long orderId;

    @Column(name = "client_order_id")
    String clientOrderId;

    @Column(name = "location_id")
    Long locationId;

    @Column(name = "location_name")
    String locationName;

    @Column(name = "client_location_id")
    String clientLocationId;

    @Column(name = "service_id")
    Long serviceId;

    @Column(name = "client_service_id")
    String clientServiceId;

    @Column(name = "provider")
    String provider;

    @Column(name = "service_type")
    String serviceType;

    @Column(name = "interval_type_code")
    String intervalTypeCode;

    @Column(name = "interval_type_desc")
    String intervalTypeDesc;

    @Column(name = "open_milestone_code")
    String openMilestoneCode;

    @Column(name = "start_date")
    Date startDate;

    @Column(name = "close_milestone_code")
    String closeMilestoneCode;

    @Column(name = "end_date")
    Date endDate;

    @Column(name = "calendar_day_interval_time")
    Double calendarDayIntervalTime;

    @Column(name = "provider_calendar_day_deduct_time")
    Double providerCalendarDayDeductTime;

    @Column(name = "customer_calendar_day_deduct_time")
    Double customerCalendarDayDeductTime;

    @Column(name = "client_calendar_day_deduct_time")
    Double clientCalendarDayDeductTime;

    @Column(name = "business_day_interval_time")
    Double businessDayIntervalTime;

    @Column(name = "provider_business_day_deduct_time")
    Double providerBusinessDayDeductTime;

    @Column(name = "client_business_day_deduct_time")
    Double clientBusinessDayDeductTime;

    @Column(name = "active")
    Boolean serviceActive;

    @JsonIgnore
    @Column(name = "tenant_id")
    private Long tenantId;

    @JsonIgnore
    @Column(name = "master_customer_id")
    private Long masterCustomerId;

    @Column(name = "service_billed_to")
    private String serviceBilledTo;

    public Long getIntervalInstanceId() {
        return intervalInstanceId;
    }

    public void setIntervalInstanceId(final Long intervalInstanceId) {
        this.intervalInstanceId = intervalInstanceId;
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

    public String getIntervalTypeCode() {
        return intervalTypeCode;
    }

    public void setIntervalTypeCode(final String intervalTypeCode) {
        this.intervalTypeCode = intervalTypeCode;
    }

    public String getIntervalTypeDesc() {
        return intervalTypeDesc;
    }

    public void setIntervalTypeDesc(final String intervalTypeDesc) {
        this.intervalTypeDesc = intervalTypeDesc;
    }

    public String getOpenMilestoneCode() {
        return openMilestoneCode;
    }

    public void setOpenMilestoneCode(final String openMilestoneCode) {
        this.openMilestoneCode = openMilestoneCode;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(final Date startDate) {
        this.startDate = startDate;
    }

    public String getCloseMilestoneCode() {
        return closeMilestoneCode;
    }

    public void setCloseMilestoneCode(final String closeMilestoneCode) {
        this.closeMilestoneCode = closeMilestoneCode;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(final Date endDate) {
        this.endDate = endDate;
    }

    public Double getCalendarDayIntervalTime() {
        return calendarDayIntervalTime;
    }

    public void setCalendarDayIntervalTime(final Double calendarDayIntervalTime) {
        this.calendarDayIntervalTime = calendarDayIntervalTime;
    }

    public Double getProviderCalendarDayDeductTime() {
        return providerCalendarDayDeductTime;
    }

    public void setProviderCalendarDayDeductTime(final Double providerCalendarDayDeductTime) {
        this.providerCalendarDayDeductTime = providerCalendarDayDeductTime;
    }

    public Double getCustomerCalendarDayDeductTime() {
        return customerCalendarDayDeductTime;
    }

    public void setCustomerCalendarDayDeductTime(final Double customerCalendarDayDeductTime) {
        this.customerCalendarDayDeductTime = customerCalendarDayDeductTime;
    }

    public Double getClientCalendarDayDeductTime() {
        return clientCalendarDayDeductTime;
    }

    public void setClientCalendarDayDeductTime(final Double clientCalendarDayDeductTime) {
        this.clientCalendarDayDeductTime = clientCalendarDayDeductTime;
    }

    public Double getBusinessDayIntervalTime() {
        return businessDayIntervalTime;
    }

    public void setBusinessDayIntervalTime(final Double businessDayIntervalTime) {
        this.businessDayIntervalTime = businessDayIntervalTime;
    }

    public Double getProviderBusinessDayDeductTime() {
        return providerBusinessDayDeductTime;
    }

    public void setProviderBusinessDayDeductTime(final Double providerBusinessDayDeductTime) {
        this.providerBusinessDayDeductTime = providerBusinessDayDeductTime;
    }

    public Double getClientBusinessDayDeductTime() {
        return clientBusinessDayDeductTime;
    }

    public void setClientBusinessDayDeductTime(final Double clientBusinessDayDeductTime) {
        this.clientBusinessDayDeductTime = clientBusinessDayDeductTime;
    }

    public Boolean getServiceActive() {
        return serviceActive;
    }

    public void setServiceActive(final Boolean serviceActive) {
        this.serviceActive = serviceActive;
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
        return intervalInstanceId;
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
