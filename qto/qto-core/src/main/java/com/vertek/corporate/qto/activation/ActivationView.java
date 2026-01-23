package com.vertek.corporate.qto.activation;

import com.vertek.corporate.qto.common.AbstractMasterCustomerOwnedEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author rcasey
 * @since 3/1/2023
 */
@Entity
@Table(name = "v_manage_activations")
public class ActivationView extends AbstractMasterCustomerOwnedEntity {

    @Id
    @Column(name = "activation_attempt_id")
    private Long id;

    @Column(name = "service_id")
    private Long serviceId;

    @Column(name = "location_id")
    private Long locationId;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "parent_company_name")
    private String parentCompanyName;

    @Column(name = "client_service_id")
    private String clientServiceId;

    @Column(name = "scheduled_attempt_status")
    private String scheduledAttemptStatus;

    @Column(name = "internal_tech_assigned")
    private String internalTechAssigned;

    @Column(name = "scheduled_check_in_time")
    private Date scheduledCheckInTime;

    @Column(name = "field_tech_check_in")
    private Date fieldTechCheckIn;

    @Column(name = "last_update_by")
    private String lastUpdateBy;

    @Column(name = "client_location_type")
    private String clientLocationType;

    @Column(name = "client_location_info")
    private String clientLocationInfo;

    @Column(name = "client_location_id")
    private String clientLocationId;

    @Column(name = "ttu_equivalent")
    private BigDecimal ttuEquivalent;

    @Column(name = "scheduled_check_in_formatted")
    private String scheduledCheckInFormatted;

    @Column(name = "field_tech_check_in_formatted")
    private String fieldTechCheckInFormatted;

    @Override
    public Long getId() {
        return id;
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

    public String getParentCompanyName() {
        return parentCompanyName;
    }

    public void setParentCompanyName(final String parentCompanyName) {
        this.parentCompanyName = parentCompanyName;
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

    public String getScheduledAttemptStatus() {
        return scheduledAttemptStatus;
    }

    public void setScheduledAttemptStatus(final String scheduledAttemptStatus) {
        this.scheduledAttemptStatus = scheduledAttemptStatus;
    }

    public String getInternalTechAssigned() {
        return internalTechAssigned;
    }

    public void setInternalTechAssigned(final String internalTechAssigned) {
        this.internalTechAssigned = internalTechAssigned;
    }

    public Date getScheduledCheckInTime() {
        return scheduledCheckInTime;
    }

    public void setScheduledCheckInTime(final Date scheduledCheckInTime) {
        this.scheduledCheckInTime = scheduledCheckInTime;
    }

    public Date getFieldTechCheckIn() {
        return fieldTechCheckIn;
    }

    public void setFieldTechCheckIn(final Date fieldTechCheckIn) {
        this.fieldTechCheckIn = fieldTechCheckIn;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(final String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
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

    public String getClientLocationId() {
        return clientLocationId;
    }

    public void setClientLocationId(final String clientLocationId) {
        this.clientLocationId = clientLocationId;
    }

    public BigDecimal getTtuEquivalent() {
        return ttuEquivalent;
    }

    public void setTtuEquivalent(final BigDecimal ttuEquivalent) {
        this.ttuEquivalent = ttuEquivalent;
    }

    public String getScheduledCheckInFormatted() {
        return scheduledCheckInFormatted;
    }

    public void setScheduledCheckInFormatted(final String scheduledCheckInFormatted) {
        this.scheduledCheckInFormatted = scheduledCheckInFormatted;
    }

    public String getFieldTechCheckInFormatted() {
        return fieldTechCheckInFormatted;
    }

    public void setFieldTechCheckInFormatted(final String fieldTechCheckInFormatted) {
        this.fieldTechCheckInFormatted = fieldTechCheckInFormatted;
    }
}
