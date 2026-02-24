package com.endeavorms.velocity.qto.interval.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(prefix = "app.jms", name = "enabled", havingValue = "true")
public class IntervalQueueHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(IntervalQueueHandler.class);

    public static final String INTERVALS_QUEUE = "qto.IntervalQueue";

    @Autowired
    private JmsTemplate jmsTemplate;

    public void sendMessageToQueue(final IntervalMessage intervalMessage) {
        try {
            jmsTemplate.convertAndSend(INTERVALS_QUEUE, intervalMessage);
            LOGGER.debug("Sent message to {}, entityId: {}, milestoneInstanceId: {}, messageType: {}",
                    INTERVALS_QUEUE, intervalMessage.getEntityId(),
                    intervalMessage.getMilestoneInstanceId(), intervalMessage.getMessageType());
        } catch (Exception e) {
            LOGGER.error("Error sending to {}: {}", INTERVALS_QUEUE, e.getMessage());
        }
    }
}
