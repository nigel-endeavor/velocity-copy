package com.endeavorms.velocity.qto.milestone;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.endeavorms.velocity.qto.common.StandardVersionedBaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Models the Association between a MilestoneDisplaySet and the corresponding Milestone.
 * @author rconnolly
 * @since 1.2.0
 */
@Entity
@JacksonXmlRootElement
@Table(name = "milestone_display_set_include")
public class MilestoneDisplaySetInclude extends StandardVersionedBaseEntity {

    /** System identifier.*/
    @Id
    @JacksonXmlProperty(isAttribute = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "milestone_display_set_include_id")
    private Long id;

    /** The milestone display set ID. */
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "milestone_display_set_id")
    private MilestoneDisplaySet milestoneDisplaySet;

    /** The associated Milestone. */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "milestone_id", referencedColumnName = "milestone_id", nullable = false)
    private Milestone milestone;

    /** Flag indicating active status. */
    @Column(name = "milestone_active", columnDefinition = "boolean DEFAULT true")
    private boolean active;

    /** Sequence used for ordering. */
    @Column(name = "milestone_sequence")
    private Integer sequence;

    @Column(name = "milestone_required")
    private boolean required;

    @Column(name = "adjustable")
    private boolean adjustable;

    @Column(name = "workflow_driven")
    private boolean workflowDriven;

    @Column(name = "has_time")
    private boolean hasTime;

    @Column(name = "disallow_future")
    private boolean disallowFuture;

    @Column(name = "status")
    private String status;

    @Column(name = "inventory_flag")
    private boolean inventoryFlag;

    @Column(name = "progress_percentage")
    private Long progressPercentage;

    /**
     * Detail on the purpose of the milestone and its use for the related display set.
     */
    @Column
    private String description;

    @Override
    public Long getId() {
        return id;
    }

    public Milestone getMilestone() {
        return milestone;
    }
    public void setMilestone(final Milestone milestone) {
        this.milestone = milestone;
    }

    public MilestoneDisplaySet getMilestoneDisplaySet() {
        return milestoneDisplaySet;
    }
    public void setMilestoneDisplaySet(final MilestoneDisplaySet milestoneDisplaySet) {
        this.milestoneDisplaySet = milestoneDisplaySet;
    }

    public boolean isActive() {
        return active;
    }
    public void setActive(final boolean active) {
        this.active = active;
    }

    public Integer getSequence() {
        return sequence;
    }
    public void setSequence(final Integer sequence) {
        this.sequence = sequence;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(final boolean required) {
        this.required = required;
    }

    public boolean isAdjustable() {
        return adjustable;
    }

    public void setAdjustable(final boolean adjustable) {
        this.adjustable = adjustable;
    }

    public boolean isWorkflowDriven() {
        return workflowDriven;
    }

    public void setWorkflowDriven(final boolean workflowDriven) {
        this.workflowDriven = workflowDriven;
    }

    public boolean isHasTime() {
        return hasTime;
    }

    public void setHasTime(final boolean hasTime) {
        this.hasTime = hasTime;
    }

    public boolean isDisallowFuture() {
        return disallowFuture;
    }

    public void setDisallowFuture(final boolean disallowFuture) {
        this.disallowFuture = disallowFuture;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public boolean isInventoryFlag() {
        return inventoryFlag;
    }

    public void setInventoryFlag(final boolean inventoryFlag) {
        this.inventoryFlag = inventoryFlag;
    }

    public Long getProgressPercentage() {
        return progressPercentage;
    }

    public void setProgressPercentage(final Long progressPercentage) {
        this.progressPercentage = progressPercentage;
    }
}
