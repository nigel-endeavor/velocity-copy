package com.vertek.corporate.qto.dispute;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vertek.corporate.qto.common.AbstractMasterCustomerOwnedEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author rcasey
 * @since 1/13/2023
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "dispute")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Dispute extends AbstractMasterCustomerOwnedEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dispute_id")
    private Long id;

    @Column(name="open_date")
    private Date openDate;

    @Column(name="dispute_status")
    private String disputeStatus;

    @Column(name="dispute_type")
    private String disputeType;

    @Column(name = "dispute_assignment")
    private String disputeAssignment;

    @Column(name="invoice_num")
    private String invoiceNum;

    @Column(name="amount_disputed_mrc")
    private BigDecimal amountDisputedMrc;

    @Column(name="amount_disputed_nrc")
    private BigDecimal amountDisputedNrc;

    @Column(name="vendor_tracking_num")
    private String vendorTrackingNum;

    @Column(name="dispute_follow_up_date")
    private Date disputeFollowUpDate;

    @Column(name="credit_recognized")
    private Date creditRecognized;

    @Column(name="billing_review_complete_date")
    private Date billingReviewComplete;

    @Column(name="dispute_closed_date")
    private Date disputeClosedDate;

    @Column(name="service_id")
    private Long serviceId;

    @Column(name = "realized_credit")
    private BigDecimal realizedCredit;

    @Column(name = "realized_mrc_adjustment")
    private BigDecimal realizedMrcAdjustment;

    @Column(name = "annualized_mrc_save")
    private BigDecimal annualizedMrcSave;

    @Transient
    private String initialNote;

    @Transient
    private boolean initialNoteInternalOnly;

    /** Used by multi-dispute creation to track the subject that created the dispute. */
    @Transient
    private Long createdBySubjectId;


    @Override
    public Long getId() {
        return id;
    }

    public Date getOpenDate() {
        return openDate;
    }

    public void setOpenDate(final Date openDate) {
        this.openDate = openDate;
    }

    public String getDisputeStatus() {
        return disputeStatus;
    }

    public void setDisputeStatus(final String disputeStatus) {
        this.disputeStatus = disputeStatus;
    }

    public String getDisputeType() {
        return disputeType;
    }

    public void setDisputeType(final String disputeType) {
        this.disputeType = disputeType;
    }

    public String getInvoiceNum() {
        return invoiceNum;
    }

    public void setInvoiceNum(final String invoiceNum) {
        this.invoiceNum = invoiceNum;
    }

    public BigDecimal getAmountDisputedMrc() {
        return amountDisputedMrc;
    }

    public void setAmountDisputedMrc(final BigDecimal amountDisputedMrc) {
        this.amountDisputedMrc = amountDisputedMrc;
    }

    public BigDecimal getAmountDisputedNrc() {
        return amountDisputedNrc;
    }

    public void setAmountDisputedNrc(final BigDecimal amountDisputedNrc) {
        this.amountDisputedNrc = amountDisputedNrc;
    }

    public String getVendorTrackingNum() {
        return vendorTrackingNum;
    }

    public void setVendorTrackingNum(final String vendorTrackingNum) {
        this.vendorTrackingNum = vendorTrackingNum;
    }

    public Date getDisputeFollowUpDate() {
        return disputeFollowUpDate;
    }

    public void setDisputeFollowUpDate(final Date disputeFollowUpDate) {
        this.disputeFollowUpDate = disputeFollowUpDate;
    }

    public Date getCreditRecognized() {
        return creditRecognized;
    }

    public void setCreditRecognized(final Date creditRecognized) {
        this.creditRecognized = creditRecognized;
    }

    public Date getBillingReviewComplete() {
        return billingReviewComplete;
    }

    public void setBillingReviewComplete(final Date billingReviewComplete) {
        this.billingReviewComplete = billingReviewComplete;
    }

    public Date getDisputeClosedDate() {
        return disputeClosedDate;
    }

    public void setDisputeClosedDate(final Date disputeClosedDate) {
        this.disputeClosedDate = disputeClosedDate;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(final Long serviceId) {
        this.serviceId = serviceId;
    }

    public BigDecimal getRealizedCredit() {
        return realizedCredit;
    }

    public void setRealizedCredit(final BigDecimal realizedCredit) {
        this.realizedCredit = realizedCredit;
    }

    public BigDecimal getRealizedMrcAdjustment() {
        return realizedMrcAdjustment;
    }

    public void setRealizedMrcAdjustment(final BigDecimal realizedMrcAdjustment) {
        this.realizedMrcAdjustment = realizedMrcAdjustment;
    }

    public BigDecimal getAnnualizedMrcSave() {
        return annualizedMrcSave;
    }

    public void setAnnualizedMrcSave(final BigDecimal annualizedMrcSave) {
        this.annualizedMrcSave = annualizedMrcSave;
    }

    public String getInitialNote() {
        return initialNote;
    }

    public void setInitialNote(final String initialNote) {
        this.initialNote = initialNote;
    }

    public boolean isInitialNoteInternalOnly() {
        return initialNoteInternalOnly;
    }

    public void setInitialNoteInternalOnly(final boolean initialNoteInternalOnly) {
        this.initialNoteInternalOnly = initialNoteInternalOnly;
    }

    public String getDisputeAssignment() {
        return disputeAssignment;
    }

    public void setDisputeAssignment(final String disputeAssignment) {
        this.disputeAssignment = disputeAssignment;
    }

    public Long getCreatedBySubjectId() {
        return createdBySubjectId;
    }

    public void setCreatedBySubjectId(final Long createdBySubjectId) {
        this.createdBySubjectId = createdBySubjectId;
    }
}
