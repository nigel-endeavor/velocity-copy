package com.endeavorms.velocity.qto.location.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(prefix = "app.jms", name = "enabled", havingValue = "true")
public class LocationMessageHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(LocationMessageHandler.class);

    public static final String LOCATION_QUEUE = "qto.LocationMessageQueue";

    @Autowired
    private JmsTemplate jmsTemplate;

    public void sendMessageToQueue(final LocationMessage locationMessage) {
        try {
            jmsTemplate.convertAndSend(LOCATION_QUEUE, locationMessage);
            LOGGER.debug("Sent message to {}, locationId: {}, messageType: {}",
                    LOCATION_QUEUE, locationMessage.getLocationId(), locationMessage.getMessageType());
        } catch (Exception e) {
            LOGGER.error("Error sending to {}: {}", LOCATION_QUEUE, e.getMessage());
        }
    }
}

