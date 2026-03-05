package com.endeavorms.velocity.qto.dispute.jms;

import com.endeavorms.velocity.qto.common.SchedulerSecurityContext;
import com.endeavorms.velocity.qto.dispute.DisputeManager;
import com.endeavorms.velocity.qto.dispute.multidispute.MultiDisputeRequestDto;
import com.endeavorms.velocity.qto.notification.NotificationManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import static com.endeavorms.velocity.qto.dispute.multidispute.MultiDisputeQueueHandler.MULTI_DISPUTE_QUEUE;

@Component
@org.springframework.boot.autoconfigure.condition.ConditionalOnProperty(name = "qto.jms.enabled", havingValue = "true", matchIfMissing = false)
public class MultiDisputeListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(MultiDisputeListener.class);

    @Autowired
    private DisputeManager disputeManager;

    @Autowired
    private NotificationManager notificationManager;

    @JmsListener(destination = MULTI_DISPUTE_QUEUE)
    public void onMessage(final MultiDisputeRequestDto dto) {
        try {
            LOGGER.debug("Got message for multi dispute");
            SchedulerSecurityContext.runAsScheduler(() -> {
                Long numDisputesCreated = disputeManager.multiCreate(dto);
                String body = numDisputesCreated + (numDisputesCreated > 1 ? " disputes" : " dispute") + " created";
                notificationManager.create(dto.getSubjectId(), "Multi Dispute Creation Completed Successfully", body, "done", null);
            });
        } catch (Exception e) {
            LOGGER.error("Error processing message - {}", e.getMessage());
            if (dto != null && dto.getSubjectId() != null) {
                notificationManager.create(dto.getSubjectId(), "Multi Dispute Creation Failed", e.getMessage(), "error", null);
            }
        } finally {
            LOGGER.debug("Done with message");
        }
    }
}
