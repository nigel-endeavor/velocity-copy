package com.vertek.corporate.qto.activation.schedule;

import com.vertek.corporate.qto.common.AbstractMasterCustomerOwnedEntity;
import com.vertek.corporate.qto.ftdi.FtdiDispatch;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author rcasey
 * @since 3/22/2023
 */
@Entity
@Table(name = "activation_schedule")
public class ActivationSchedule extends AbstractMasterCustomerOwnedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "activation_schedule_id")
    private Long id;

    @Column(name="legacy_id")
    private Long legacyId;

    @Column(name="legacy_dispatch_id")
    private Long legacyDispatchId;

    @Column(name="legacy_ctn_id")
    private Long legacyCtnId;

    @Column(name = "service_id")
    private Long serviceId;

    @Column(name = "vendor")
    private String vendor;

    @Column(name = "requested_date")
    private Date requestedDate;

    @Column(name = "ftdi_order_type_id")
    private Long ftdiOrderTypeId;

    @Column(name = "latest_requested_date")
    private Date latestRequestedDate;

    @Column(name = "requested_days")
    private String requestedDays;

    @Column(name = "technical_note")
    private String technicalNote;

    @Column(name = "description")
    private String description;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "activation_schedule_id", referencedColumnName = "activation_schedule_id")
    private List<ActivationScheduleCustom> customFields = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinColumn(name = "activation_schedule_id", referencedColumnName = "activation_schedule_id")
    private List<ActivationScheduleEquipment> equipment = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinColumn(name = "schedule_id", referencedColumnName = "activation_schedule_id")
    private List<FtdiDispatch> dispatches = new ArrayList<>();
    /** Flag to indicate if the turn up is requested the same day, */
    @Transient
    private boolean applySameDayTurnUpSurcharge;

    public ActivationSchedule() {
    }

    public ActivationSchedule(ActivationSchedule activationSchedule) {
        this.serviceId = activationSchedule.serviceId;
        this.vendor = activationSchedule.vendor;
        this.requestedDate = activationSchedule.requestedDate;
        this.ftdiOrderTypeId = activationSchedule.ftdiOrderTypeId;
        this.latestRequestedDate = activationSchedule.latestRequestedDate;
        this.requestedDays = activationSchedule.requestedDays;
        this.technicalNote = activationSchedule.technicalNote;
        this.description = activationSchedule.description;
    }

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

    public String getVendor() {
        return vendor;
    }

    public void setVendor(final String vendor) {
        this.vendor = vendor;
    }

    public Date getRequestedDate() {
        return requestedDate;
    }

    public void setRequestedDate(final Date requestedDate) {
        this.requestedDate = requestedDate;
    }

    public Long getFtdiOrderTypeId() {
        return ftdiOrderTypeId;
    }

    public void setFtdiOrderTypeId(final Long ftdiOrderTypeId) {
        this.ftdiOrderTypeId = ftdiOrderTypeId;
    }

    public List<FtdiDispatch> getDispatches() {
        return dispatches;
    }

    public void setDispatches(final List<FtdiDispatch> dispatches) {
        this.dispatches = dispatches;
    }

    public Date getLatestRequestedDate() {
        return latestRequestedDate;
    }

    public void setLatestRequestedDate(final Date latestRequestedDate) {
        this.latestRequestedDate = latestRequestedDate;
    }

    public String getRequestedDays() {
        return requestedDays;
    }

    public void setRequestedDays(final String requestedDays) {
        this.requestedDays = requestedDays;
    }

    public String getTechnicalNote() {
        return technicalNote;
    }

    public void setTechnicalNote(final String technicalNote) {
        this.technicalNote = technicalNote;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public List<ActivationScheduleCustom> getCustomFields() {
        return customFields;
    }

    public void setCustomFields(final List<ActivationScheduleCustom> customFields) {
        this.customFields = customFields;
    }

    public List<ActivationScheduleEquipment> getEquipment() {
        return equipment;
    }

    public void setEquipment(final List<ActivationScheduleEquipment> equipment) {
        this.equipment = equipment;
    }

    public Long getLegacyId() {
        return legacyId;
    }

    public void setLegacyId(final Long legacyId) {
        this.legacyId = legacyId;
    }

    public Long getLegacyCtnId() {
        return legacyCtnId;
    }

    public void setLegacyCtnId(final Long legacyCtnId) {
        this.legacyCtnId = legacyCtnId;
    }

    public Long getLegacyDispatchId() {
        return legacyDispatchId;
    }

    public void setLegacyDispatchId(final Long legacyDispatchId) {
        this.legacyDispatchId = legacyDispatchId;
    }

    public boolean isApplySameDayTurnUpSurcharge() {
        return applySameDayTurnUpSurcharge;
    }

    public void setApplySameDayTurnUpSurcharge(final boolean applySameDayTurnUpSurcharge) {
        this.applySameDayTurnUpSurcharge = applySameDayTurnUpSurcharge;
    }
}
