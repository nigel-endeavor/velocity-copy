package com.endeavorms.velocity.qto.milestone;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.endeavorms.velocity.qto.common.AbstractMasterCustomerOwnedEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.util.Date;

/**
 * Models a Milestone Instance.
 * Derived types create relationship to records (OrderMilestoneInstance, SiteMilestoneInstace, etc).
 * @author mmaloney
 */
@Entity
@JacksonXmlRootElement
@Table(name = "milestone_instance")
@Inheritance(strategy = InheritanceType.JOINED)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MilestoneInstance extends AbstractMasterCustomerOwnedEntity {

    /** System identifier. */
    @Id
    @JacksonXmlProperty(isAttribute = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "milestone_instance_id")
    private Long id;

    @Column(name="legacy_id")
    private Long legacyId;

    /** The associated Milestone. */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "milestone_id", referencedColumnName = "milestone_id", nullable = false)
    private Milestone milestone;

    /** The Milestone date. */
    @Column(name = "milestone_date")
    private Date milestoneDate;

    /** A counter for indicating the order in which instances were created. */
    @Column(name = "milestone_instance_count")
    private Integer count;

    /** A indicator that milestone put in history. */
    @Column(name = "milestone_param")
    private String param;

    /** An indicator that milestone can be put into history. */
    @Column(name = "historic")
    private boolean historic;

    @Transient
    private String note;

    @Column(name = "invoice_id")
    private Long invoiceId;

    @Column(name = "parent_milestone_instance_id")
    private Long parentMilestoneInstanceId;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Milestone getMilestone() {
        return milestone;
    }

    public void setMilestone(final Milestone milestone) {
        this.milestone = milestone;
    }

    public Date getMilestoneDate() {
        return milestoneDate;
    }

    public void setMilestoneDate(final Date milestoneDate) {
        this.milestoneDate = milestoneDate;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(final Integer count) {
        this.count = count;
    }

    public String getParam() {
        return param;
    }

    public void setParam(final String param) {
        this.param = param;
    }

    public boolean isHistoric() {
        return historic;
    }

    public void setHistoric(final boolean historic) {
        this.historic = historic;
    }

    public String getNote() {
        return note;
    }

    public void setNote(final String note) {
        this.note = note;
    }

    public Long getLegacyId() {
        return legacyId;
    }

    public void setLegacyId(final Long legacyId) {
        this.legacyId = legacyId;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(final Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Long getParentMilestoneInstanceId() {
        return parentMilestoneInstanceId;
    }

    public void setParentMilestoneInstanceId(final Long parentMilestoneInstanceId) {
        this.parentMilestoneInstanceId = parentMilestoneInstanceId;
    }
}
