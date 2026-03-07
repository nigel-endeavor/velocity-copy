package com.vertek.corporate.qto.activation.requirement;

import com.vertek.corporate.qto.common.StandardVersionedBaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 * @author rcasey
 * @since 3/3/2023
 */
@Entity
@Table(name = "activation_attempt_requirement")
public class ActivationAttemptRequirement extends StandardVersionedBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "activation_attempt_requirement_id")
    private Long id;

    @Column(name = "activation_attempt_id")
    private Long activationAttemptId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "requirement_id", referencedColumnName = "requirement_id", updatable = false)
    private Requirement requirement;

    @Column(name = "complete")
    private boolean complete;

    @Column(name = "comment")
    private String comment;

    public ActivationAttemptRequirement() {
    }

    public ActivationAttemptRequirement(ActivationAttemptRequirement activationAttemptRequirement) {
        this.activationAttemptId = activationAttemptRequirement.activationAttemptId;
        this.requirement = activationAttemptRequirement.requirement;
        this.complete = activationAttemptRequirement.complete;
        this.comment = activationAttemptRequirement.comment;
    }

    @Override
    public Long getId() {
        return id;
    }

    public Long getActivationAttemptId() {
        return activationAttemptId;
    }

    public void setActivationAttemptId(final Long activationAttemptId) {
        this.activationAttemptId = activationAttemptId;
    }

    public Requirement getRequirement() {
        return requirement;
    }

    public void setRequirement(final Requirement requirement) {
        this.requirement = requirement;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(final boolean complete) {
        this.complete = complete;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(final String comment) {
        this.comment = comment;
    }
}
