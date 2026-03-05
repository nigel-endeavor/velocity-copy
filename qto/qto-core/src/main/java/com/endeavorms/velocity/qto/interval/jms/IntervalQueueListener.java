package com.endeavorms.velocity.qto.interval.jms;

import com.endeavorms.velocity.qto.common.SchedulerSecurityContext;
import com.endeavorms.velocity.qto.events.handlers.BaseEventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import static com.endeavorms.velocity.qto.interval.jms.IntervalQueueHandler.INTERVALS_QUEUE;

/**
 * @author rcasey
 * @since 3/10/2023
 */
@Component
@org.springframework.boot.autoconfigure.condition.ConditionalOnProperty(name = "qto.jms.enabled", havingValue = "true", matchIfMissing = false)
public class IntervalQueueListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(IntervalQueueListener.class);

    @Autowired
    private BaseEventHandler eventHandler;

    @JmsListener(destination = INTERVALS_QUEUE)
    public void onMessage(final IntervalMessage intervalMessage) {
        try {
            LOGGER.debug("Got interval message: {}", intervalMessage);
            SchedulerSecurityContext.runAsScheduler(() -> {
                if ("service".equalsIgnoreCase(intervalMessage.getMessageType())) {
                    eventHandler.createServiceIntervals(intervalMessage.getMilestoneInstanceId(), intervalMessage.getEntityId());
                } else if ("location".equalsIgnoreCase(intervalMessage.getMessageType())) {
                    //todo
                } else {
                    LOGGER.error("Unrecognized message type: {}", intervalMessage.getMessageType());
                }
            });
        } catch (Exception e) {
            LOGGER.error("Error processing message - {}", e.getMessage());
            throw new RuntimeException(e);
        } finally {
            LOGGER.debug("Done with message");
        }
    }
}
