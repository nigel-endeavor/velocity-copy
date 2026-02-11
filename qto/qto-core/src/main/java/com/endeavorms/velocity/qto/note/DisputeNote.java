package com.endeavorms.velocity.qto.note;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * @author fcurran
 * @since 9/25/2023
 */
@Entity
@Table(name = "dispute_note")
@JsonIgnoreProperties(ignoreUnknown = true)
public class DisputeNote extends Note {

    @Column(name = "dispute_id")
    private Long disputeId;

    public Long getDisputeId() {
        return disputeId;
    }

    public void setDisputeId(final Long disputeId) {
        this.disputeId = disputeId;
    }
}
