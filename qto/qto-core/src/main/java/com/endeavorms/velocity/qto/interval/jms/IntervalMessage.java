package com.endeavorms.velocity.qto.interval.jms;

import java.io.Serializable;

/**
 * @author rcasey
 * @since 3/10/2023
 */
public class IntervalMessage implements Serializable {
    /** Entity this call is associated with. */
    private Long entityId;

    /** Milestone instance id. */
    private Long milestoneInstanceId;

    /** Message type. */
    private String messageType;

    /**
     * Constructor.
     * @param entityId entity ID
     * @param milestoneInstanceId milestone instance ID
     * @param messageType message type
     */
    public IntervalMessage(final Long entityId, final Long milestoneInstanceId, final String messageType) {
        this.entityId = entityId;
        this.milestoneInstanceId = milestoneInstanceId;
        this.messageType = messageType;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(final Long entityId) {
        this.entityId = entityId;
    }

    public Long getMilestoneInstanceId() {
        return milestoneInstanceId;
    }

    public void setMilestoneInstanceId(final Long milestoneInstanceId) {
        this.milestoneInstanceId = milestoneInstanceId;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(final String messageType) {
        this.messageType = messageType;
    }

    @Override
    public String toString() {
        return "IntervalMessage{"
                + "entityId=" + entityId
                + ", milestoneInstanceId='" + milestoneInstanceId + '\''
                + ", messageType='" + messageType + '\''
                + '}';
    }
}
