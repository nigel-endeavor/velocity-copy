package com.vertek.corporate.qto.interval;

import com.vertek.corporate.qto.common.StandardVersionedBaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @author rcasey
 * @since 3/9/2023
 */
@Entity
@Table(name = "interval_instance")
@Inheritance(strategy = InheritanceType.JOINED)
public class IntervalInstance extends StandardVersionedBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name =  "interval_instance_id")
    private Long id;

    @Column(name =  "interval_type_id")
    private Long intervalTypeId;

    @Column(name =  "open_milestone_instance_id")
    private Long openMilestoneInstanceId;

    @Column(name =  "close_milestone_instance_id")
    private Long closeMilestoneInstanceId;

    @Column(name =  "business_day_interval_time")
    private BigDecimal businessDayIntervalTime;

    @Column(name =  "calendar_day_interval_time")
    private BigDecimal calendarDayIntervalTime;

    @Column(name =  "client_business_day_deduct_time")
    private BigDecimal clientBusinessDayDeductTime;

    @Column(name =  "client_calendar_day_deduct_time")
    private BigDecimal clientCalendarDayDeductTime;

    @Column(name =  "customer_business_day_deduct_time")
    private BigDecimal customerBusinessDayDeductTime;

    @Column(name =  "customer_calendar_day_deduct_time")
    private BigDecimal customerCalendarDayDeductTime;

    @Column(name =  "provider_business_day_deduct_time")
    private BigDecimal providerBusinessDayDeductTime;

    @Column(name =  "provider_calendar_day_deduct_time")
    private BigDecimal providerCalendarDayDeductTime;

    public IntervalInstance() {
        businessDayIntervalTime = BigDecimal.ZERO;
        calendarDayIntervalTime = BigDecimal.ZERO;
        clientBusinessDayDeductTime = BigDecimal.ZERO;
        clientCalendarDayDeductTime = BigDecimal.ZERO;
        customerBusinessDayDeductTime = BigDecimal.ZERO;
        customerCalendarDayDeductTime = BigDecimal.ZERO;
        providerBusinessDayDeductTime = BigDecimal.ZERO;
        providerCalendarDayDeductTime = BigDecimal.ZERO;
    }

    @Override
    public Long getId() {
        return id;
    }

    public Long getIntervalTypeId() {
        return intervalTypeId;
    }

    public void setIntervalTypeId(final Long intervalTypeId) {
        this.intervalTypeId = intervalTypeId;
    }

    public Long getOpenMilestoneInstanceId() {
        return openMilestoneInstanceId;
    }

    public void setOpenMilestoneInstanceId(final Long openMilestoneInstanceId) {
        this.openMilestoneInstanceId = openMilestoneInstanceId;
    }

    public Long getCloseMilestoneInstanceId() {
        return closeMilestoneInstanceId;
    }

    public void setCloseMilestoneInstanceId(final Long closeMilestoneInstanceId) {
        this.closeMilestoneInstanceId = closeMilestoneInstanceId;
    }

    public BigDecimal getBusinessDayIntervalTime() {
        return businessDayIntervalTime;
    }

    public void setBusinessDayIntervalTime(final BigDecimal businessDayIntervalTime) {
        this.businessDayIntervalTime = businessDayIntervalTime;
    }

    public BigDecimal getCalendarDayIntervalTime() {
        return calendarDayIntervalTime;
    }

    public void setCalendarDayIntervalTime(final BigDecimal calendarDayIntervalTime) {
        this.calendarDayIntervalTime = calendarDayIntervalTime;
    }

    public BigDecimal getClientBusinessDayDeductTime() {
        return clientBusinessDayDeductTime;
    }

    public void setClientBusinessDayDeductTime(final BigDecimal clientBusinessDayDeductTime) {
        this.clientBusinessDayDeductTime = clientBusinessDayDeductTime;
    }

    public BigDecimal getClientCalendarDayDeductTime() {
        return clientCalendarDayDeductTime;
    }

    public void setClientCalendarDayDeductTime(final BigDecimal clientCalendarDayDeductTime) {
        this.clientCalendarDayDeductTime = clientCalendarDayDeductTime;
    }

    public BigDecimal getCustomerBusinessDayDeductTime() {
        return customerBusinessDayDeductTime;
    }

    public void setCustomerBusinessDayDeductTime(final BigDecimal customerBusinessDayDeductTime) {
        this.customerBusinessDayDeductTime = customerBusinessDayDeductTime;
    }

    public BigDecimal getCustomerCalendarDayDeductTime() {
        return customerCalendarDayDeductTime;
    }

    public void setCustomerCalendarDayDeductTime(final BigDecimal customerCalendarDayDeductTime) {
        this.customerCalendarDayDeductTime = customerCalendarDayDeductTime;
    }

    public BigDecimal getProviderBusinessDayDeductTime() {
        return providerBusinessDayDeductTime;
    }

    public void setProviderBusinessDayDeductTime(final BigDecimal providerBusinessDayDeductTime) {
        this.providerBusinessDayDeductTime = providerBusinessDayDeductTime;
    }

    public BigDecimal getProviderCalendarDayDeductTime() {
        return providerCalendarDayDeductTime;
    }

    public void setProviderCalendarDayDeductTime(final BigDecimal providerCalendarDayDeductTime) {
        this.providerCalendarDayDeductTime = providerCalendarDayDeductTime;
    }
}
