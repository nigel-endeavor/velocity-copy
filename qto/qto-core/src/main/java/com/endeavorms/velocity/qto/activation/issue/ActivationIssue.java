package com.endeavorms.velocity.qto.activation.issue;

import com.endeavorms.velocity.qto.common.AbstractMasterCustomerOwnedEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * @author rcasey
 * @since 3/29/2023
 */
@Entity
@Table(name = "activation_issue")
public class ActivationIssue extends AbstractMasterCustomerOwnedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "activation_issue_id")
    private Long id;

    @Column(name = "activation_attempt_id")
    private Long activationAttemptId;

    @Column(name = "primary_root_cause")
    private String primaryRootCause;

    @Column(name = "secondary_root_cause")
    private String secondaryRootCause;

    @Column(name = "tertiary_root_cause")
    private String tertiaryRootCause;

    @Column(name = "issue_rank")
    private Long rank;

    @Column(name = "note")
    private String note;

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

    public String getPrimaryRootCause() {
        return primaryRootCause;
    }

    public void setPrimaryRootCause(final String primaryRootCause) {
        this.primaryRootCause = primaryRootCause;
    }

    public String getSecondaryRootCause() {
        return secondaryRootCause;
    }

    public void setSecondaryRootCause(final String secondaryRootCause) {
        this.secondaryRootCause = secondaryRootCause;
    }

    public String getTertiaryRootCause() {
        return tertiaryRootCause;
    }

    public void setTertiaryRootCause(final String tertiaryRootCause) {
        this.tertiaryRootCause = tertiaryRootCause;
    }

    public Long getRank() {
        return rank;
    }

    public void setRank(final Long rank) {
        this.rank = rank;
    }

    public String getNote() {
        return note;
    }

    public void setNote(final String note) {
        this.note = note;
    }
}
