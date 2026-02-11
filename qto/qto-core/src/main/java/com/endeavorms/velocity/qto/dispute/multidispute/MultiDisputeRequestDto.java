package com.endeavorms.velocity.qto.dispute.multidispute;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.endeavorms.velocity.qto.dispute.Dispute;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for creating multiple disputes.
 * @author rcasey
 * @since 6/6/2024
 */
public class MultiDisputeRequestDto implements Serializable {
    /** Service ID(s) we're creating the dispute for. */
    @JsonProperty
    private List<Long> serviceIds;
    /** The dispute to create. */
    @JsonProperty
    private Dispute dispute;
    /** Subject ID for the dispute request. This is used to send a notification after dispute creation. */
    private Long subjectId;

    public List<Long> getServiceIds() {
        return serviceIds;
    }

    public void setServiceIds(final List<Long> serviceIds) {
        this.serviceIds = serviceIds;
    }

    public Dispute getDispute() {
        return dispute;
    }

    public void setDispute(final Dispute dispute) {
        this.dispute = dispute;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(final Long subjectId) {
        this.subjectId = subjectId;
    }
}
