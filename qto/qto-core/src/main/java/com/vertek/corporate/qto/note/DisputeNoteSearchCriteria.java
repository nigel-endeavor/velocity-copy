package com.vertek.corporate.qto.note;

import com.vertek.corporate.qto.common.BaseSearchCriteria;

import jakarta.ws.rs.QueryParam;

/**
 * Simple search criteria for DisputeNotes.
 */
public class DisputeNoteSearchCriteria extends BaseSearchCriteria<DisputeNote> {
    /** The dispute id. */
    @QueryParam("disputeId")
    private Long disputeId;

    public Long getDisputeId() {
        return disputeId;
    }

    public void setDisputeId(final Long disputeId) {
        this.disputeId = disputeId;
    }
}
