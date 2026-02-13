package com.endeavorms.velocity.qto.note;

import com.endeavorms.velocity.qto.common.BaseSearchCriteria;


/**
 * Simple search criteria for DisputeNotes.
 */
public class DisputeNoteSearchCriteria extends BaseSearchCriteria<DisputeNote> {
    /** The dispute id. */
    private Long disputeId;

    public Long getDisputeId() {
        return disputeId;
    }

    public void setDisputeId(final Long disputeId) {
        this.disputeId = disputeId;
    }
}
