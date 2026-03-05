package com.endeavorms.velocity.qto.interval.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class IntervalQueueHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(IntervalQueueHandler.class);

    public static final String INTERVALS_QUEUE = "qto.IntervalQueue";

    // JMS disabled
    // private JmsTemplate jmsTemplate;

    public void sendMessageToQueue(final IntervalMessage intervalMessage) {
        try {
            // JMS disabled
            LOGGER.debug("Sent message to {}, entityId: {}, milestoneInstanceId: {}, messageType: {}",
                    INTERVALS_QUEUE, intervalMessage.getEntityId(),
                    intervalMessage.getMilestoneInstanceId(), intervalMessage.getMessageType());
        } catch (Exception e) {
            LOGGER.error("Error sending to {}: {}", INTERVALS_QUEUE, e.getMessage());
        }
    }
}
