package com.endeavorms.velocity.qto.activation.attempt;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.endeavorms.velocity.qto.activation.requirement.ActivationAttemptRequirement;
import com.endeavorms.velocity.qto.common.AbstractMasterCustomerOwnedEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author rcasey
 * @since 3/23/2023
 */
@Entity
@Table(name = "activation_attempt")
public class ActivationAttempt extends AbstractMasterCustomerOwnedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "activation_attempt_id")
    private Long id;

    @Column(name="legacy_id")
    private Long legacyId;

    @Column(name = "scheduled_attempt_status")
    private String scheduledAttemptStatus;

    @Column(name = "internal_tech_assigned")
    private String internalTechAssigned;

    @Column(name = "field_dispatch_vendor")
    private String fieldDispatchVendor;

    @Column(name = "field_tech_name")
    private String fieldTechName;

    @Column(name = "field_tech_phone")
    private String fieldTechPhone;

    @Column(name = "field_tech_check_in")
    private Date fieldTechCheckIn;

    @Column(name = "field_tech_check_out")
    private Date fieldTechCheckOut;

    @Column(name = "tested_download_speed")
    private String testedDownloadSpeed;

    @Column(name = "tested_upload_speed")
    private String testedUploadSpeed;

    @Column(name = "latency")
    private String latency;

    @Column(name = "backup_download_speed")
    private String backupDownloadSpeed;

    @Column(name = "backup_upload_speed")
    private String backupUploadSpeed;

    @Column(name = "signal_rsrp")
    private String signalRsrp;

    @Column(name = "sinr_rsrq")
    private String sinrRsrq;

    @Column(name = "location_downtown_for_cutover")
    private String locationDowntownForCutover;

    @Column(name = "closeout_code")
    private String closeoutCode;

    @Column(name = "network_complete_date")
    private Date networkCompleteDate;

    @Column(name = "voip_complete_date")
    private Date voipCompleteDate;

    @JsonIgnore
    @Column(name = "issue_notes")
    private String issueNotes;

    @Transient
    private String issueNotesDisplay;

    @Column(name = "close_notes")
    private String closeNotes;

    @Column(name = "duplicate_to_related")
    private boolean duplicateToRelated;

    @Column(name = "warning_message")
    private String warningMessage;

    @Column(name = "po_number")
    private String poNumber;

    @Column(name = "service_id")
    private Long serviceId;

    @Column(name = "scheduled_check_in_time")
    private Date scheduledCheckInTime;

    @Column(name = "activation_schedule_id")
    private Long activationScheduleId;

    @Column(name = "ftdi_appointment_id")
    private Long ftdiAppointmentId;

    @Column(name = "attempt_number")
    private Long attemptNumber;

    @Column(name= "replace_4g_5g")
    private String replace4g5g;

//    @Column(name = "network_cutover_date")
//    private Date networkCutoverDate;

    @Column(name = "primary_uid")
    private String primaryUid;

    @Column(name = "secondary_uid")
    private String secondaryUid;

    @Column(name = "ftdi_vendor_id")
    private String ftdiVendorId;

    @Column(name = "ftdi_dispatch_id")
    private Long ftdiDispatchId;

    @Column(name = "managed_router_serial_number")
    private String managedRouterSerialNumber;

    @Column(name = "cancelled_date")
    private Date cancelledDate;

    @Column(name = "cancelled_by")
    private String cancelledBy;

    @Column(name = "same_day_schedule")
    private boolean sameDaySchedule;

    @Column(name = "ftdi_vendor_status")
    private String ftdiVendorStatus;

    @Column(name = "created_date")
    private Date createdDate;

    @OneToMany(fetch = FetchType.EAGER, cascade =  CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "activation_attempt_id", referencedColumnName = "activation_attempt_id")
    private List<ActivationAttemptRequirement> requirements = new ArrayList<>();

    @Transient
    private Boolean useExistingInventoryLocationAddress;

    public ActivationAttempt() {
    }

    public ActivationAttempt(ActivationAttempt activationAttempt) {
        this.scheduledAttemptStatus = activationAttempt.scheduledAttemptStatus;
        this.internalTechAssigned = activationAttempt.internalTechAssigned;
        this.fieldDispatchVendor = activationAttempt.fieldDispatchVendor;
        this.fieldTechName = activationAttempt.fieldTechName;
        this.fieldTechPhone = activationAttempt.fieldTechPhone;
        this.fieldTechCheckIn = activationAttempt.fieldTechCheckIn;
        this.fieldTechCheckOut = activationAttempt.fieldTechCheckOut;
        this.testedDownloadSpeed = activationAttempt.testedDownloadSpeed;
        this.testedUploadSpeed = activationAttempt.testedUploadSpeed;
        this.latency = activationAttempt.latency;
        this.backupDownloadSpeed = activationAttempt.backupDownloadSpeed;
        this.backupUploadSpeed = activationAttempt.backupUploadSpeed;
        this.signalRsrp = activationAttempt.signalRsrp;
        this.sinrRsrq = activationAttempt.sinrRsrq;
        this.locationDowntownForCutover = activationAttempt.locationDowntownForCutover;
        this.closeoutCode = activationAttempt.closeoutCode;
        this.networkCompleteDate = activationAttempt.networkCompleteDate;
        this.voipCompleteDate = activationAttempt.voipCompleteDate;
        this.issueNotes = activationAttempt.issueNotes;
        this.duplicateToRelated = activationAttempt.duplicateToRelated;
        this.warningMessage = activationAttempt.warningMessage;
        this.poNumber = activationAttempt.poNumber;
        this.serviceId = activationAttempt.serviceId;
        this.scheduledCheckInTime = activationAttempt.scheduledCheckInTime;
        this.activationScheduleId = activationAttempt.activationScheduleId;
        this.attemptNumber = activationAttempt.attemptNumber;
        this.replace4g5g = activationAttempt.replace4g5g;
//        this.networkCutoverDate = activationAttempt.networkCutoverDate;
        this.primaryUid = activationAttempt.primaryUid;
        this.secondaryUid = activationAttempt.secondaryUid;
        this.ftdiVendorId = activationAttempt.ftdiVendorId;
        this.managedRouterSerialNumber = activationAttempt.managedRouterSerialNumber;
        this.ftdiVendorStatus = activationAttempt.ftdiVendorStatus;
        this.requirements = new ArrayList<>();
        for (ActivationAttemptRequirement requirement : activationAttempt.getRequirements()) {
            this.requirements.add(new ActivationAttemptRequirement(requirement));
        }
    }

    @Override
    public Long getId() {
        return id;
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

    public String getFieldDispatchVendor() {
        return fieldDispatchVendor;
    }

    public void setFieldDispatchVendor(final String fieldDispatchVendor) {
        this.fieldDispatchVendor = fieldDispatchVendor;
    }

    public String getFieldTechName() {
        return fieldTechName;
    }

    public void setFieldTechName(final String fieldTechName) {
        this.fieldTechName = fieldTechName;
    }

    public String getFieldTechPhone() {
        return fieldTechPhone;
    }

    public void setFieldTechPhone(final String fieldTechPhone) {
        this.fieldTechPhone = fieldTechPhone;
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

    public String getTestedDownloadSpeed() {
        return testedDownloadSpeed;
    }

    public void setTestedDownloadSpeed(final String testedDownloadSpeed) {
        this.testedDownloadSpeed = testedDownloadSpeed;
    }

    public String getTestedUploadSpeed() {
        return testedUploadSpeed;
    }

    public void setTestedUploadSpeed(final String testedUploadSpeed) {
        this.testedUploadSpeed = testedUploadSpeed;
    }

    public String getLatency() {
        return latency;
    }

    public void setLatency(final String latency) {
        this.latency = latency;
    }

    public String getBackupDownloadSpeed() {
        return backupDownloadSpeed;
    }

    public void setBackupDownloadSpeed(final String backupDownloadSpeed) {
        this.backupDownloadSpeed = backupDownloadSpeed;
    }

    public String getBackupUploadSpeed() {
        return backupUploadSpeed;
    }

    public void setBackupUploadSpeed(final String backupUploadSpeed) {
        this.backupUploadSpeed = backupUploadSpeed;
    }

    public String getSignalRsrp() {
        return signalRsrp;
    }

    public void setSignalRsrp(final String signalRsrp) {
        this.signalRsrp = signalRsrp;
    }

    public String getSinrRsrq() {
        return sinrRsrq;
    }

    public void setSinrRsrq(final String sinrRsrq) {
        this.sinrRsrq = sinrRsrq;
    }

    public String getLocationDowntownForCutover() {
        return locationDowntownForCutover;
    }

    public void setLocationDowntownForCutover(final String locationDowntownForCutover) {
        this.locationDowntownForCutover = locationDowntownForCutover;
    }

    public String getCloseoutCode() {
        return closeoutCode;
    }

    public void setCloseoutCode(final String closeoutCode) {
        this.closeoutCode = closeoutCode;
    }

    public Date getNetworkCompleteDate() {
        return networkCompleteDate;
    }

    public void setNetworkCompleteDate(final Date networkCompleteDate) {
        this.networkCompleteDate = networkCompleteDate;
    }

    public Date getVoipCompleteDate() {
        return voipCompleteDate;
    }

    public void setVoipCompleteDate(final Date voipCompleteDate) {
        this.voipCompleteDate = voipCompleteDate;
    }

    public String getIssueNotes() {
        return issueNotes;
    }

    public void setIssueNotes(final String issueNotes) {
        this.issueNotes = issueNotes;
    }

    public String getIssueNotesDisplay() {
        return issueNotesDisplay;
    }

    public void setIssueNotesDisplay(final String issueNotesDisplay) {
        this.issueNotesDisplay = issueNotesDisplay;
    }

    public String getCloseNotes() {
        return closeNotes;
    }

    public void setCloseNotes(final String closeNotes) {
        this.closeNotes = closeNotes;
    }

    public boolean isDuplicateToRelated() {
        return duplicateToRelated;
    }

    public void setDuplicateToRelated(final boolean duplicateToRelated) {
        this.duplicateToRelated = duplicateToRelated;
    }

    public String getWarningMessage() {
        return warningMessage;
    }

    public void setWarningMessage(final String warningMessage) {
        this.warningMessage = warningMessage;
    }

    public String getPoNumber() {
        return poNumber;
    }

    public void setPoNumber(final String poNumber) {
        this.poNumber = poNumber;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(final Long serviceId) {
        this.serviceId = serviceId;
    }

    public Date getScheduledCheckInTime() {
        return scheduledCheckInTime;
    }

    public void setScheduledCheckInTime(final Date scheduledCheckInTime) {
        this.scheduledCheckInTime = scheduledCheckInTime;
    }

    public Long getActivationScheduleId() {
        return activationScheduleId;
    }

    public void setActivationScheduleId(final Long activationScheduleId) {
        this.activationScheduleId = activationScheduleId;
    }

    public Long getFtdiAppointmentId() {
        return ftdiAppointmentId;
    }

    public void setFtdiAppointmentId(final Long ftdiAppointmentId) {
        this.ftdiAppointmentId = ftdiAppointmentId;
    }

    public Long getAttemptNumber() {
        return attemptNumber;
    }

    public void setAttemptNumber(final Long attemptNumber) {
        this.attemptNumber = attemptNumber;
    }

    public List<ActivationAttemptRequirement> getRequirements() {
        return requirements;
    }

    public void setRequirements(final List<ActivationAttemptRequirement> requirements) {
        this.requirements = requirements;
    }

    public String getReplace4g5g() {
        return replace4g5g;
    }

    public void setReplace4g5g(final String replace4g5g) {
        this.replace4g5g = replace4g5g;
    }

    public String getPrimaryUid() {
        return primaryUid;
    }

    public void setPrimaryUid(final String primaryUid) {
        this.primaryUid = primaryUid;
    }

    public String getSecondaryUid() {
        return secondaryUid;
    }

    public void setSecondaryUid(final String secondaryUid) {
        this.secondaryUid = secondaryUid;
    }

    public String getFtdiVendorId() {
        return ftdiVendorId;
    }

    public void setFtdiVendorId(final String ftdiVendorId) {
        this.ftdiVendorId = ftdiVendorId;
    }

    public Long getFtdiDispatchId() {
        return ftdiDispatchId;
    }

    public void setFtdiDispatchId(final Long ftdiDispatchId) {
        this.ftdiDispatchId = ftdiDispatchId;
    }

    public Long getLegacyId() {
        return legacyId;
    }

    public void setLegacyId(final Long legacyId) {
        this.legacyId = legacyId;
    }

    public String getManagedRouterSerialNumber() {
        return managedRouterSerialNumber;
    }

    public void setManagedRouterSerialNumber(final String managedRouterSerialNumber) {
        this.managedRouterSerialNumber = managedRouterSerialNumber;
    }

    public Date getCancelledDate() {
        return cancelledDate;
    }

    public void setCancelledDate(final Date cancelledDate) {
        this.cancelledDate = cancelledDate;
    }

    public String getCancelledBy() {
        return cancelledBy;
    }

    public void setCancelledBy(final String cancelledBy) {
        this.cancelledBy = cancelledBy;
    }

    public boolean isSameDaySchedule() {
        return sameDaySchedule;
    }

    public void setSameDaySchedule(final boolean sameDaySchedule) {
        this.sameDaySchedule = sameDaySchedule;
    }

    public String getFtdiVendorStatus() {
        return ftdiVendorStatus;
    }

    public void setFtdiVendorStatus(final String ftdiVendorStatus) {
        this.ftdiVendorStatus = ftdiVendorStatus;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(final Date createdDate) {
        this.createdDate = createdDate;
    }

    public Boolean getUseExistingInventoryLocationAddress() {
        return useExistingInventoryLocationAddress;
    }

    public void setUseExistingInventoryLocationAddress(final Boolean useExistingInventoryLocationAddress) {
        this.useExistingInventoryLocationAddress = useExistingInventoryLocationAddress;
    }
}
