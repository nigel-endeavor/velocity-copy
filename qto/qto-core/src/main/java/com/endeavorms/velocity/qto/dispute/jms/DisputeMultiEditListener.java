package com.endeavorms.velocity.qto.dispute.jms;

import com.endeavorms.velocity.qto.common.SchedulerSecurityContext;
import com.endeavorms.velocity.qto.dispute.DisputeManager;
import com.endeavorms.velocity.qto.notification.NotificationManager;
import com.endeavorms.velocity.qto.multiedit.request.MultiEditRequestDto;
import com.endeavorms.velocity.qto.multiedit.response.MultiEditError;
import com.endeavorms.velocity.qto.multiedit.response.MultiEditResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import static com.endeavorms.velocity.qto.dispute.multiedit.DisputeMultiEditQueueHandler.DISPUTE_MULTI_EDIT_QUEUE;

@Component
@org.springframework.boot.autoconfigure.condition.ConditionalOnProperty(name = "qto.jms.enabled", havingValue = "true", matchIfMissing = false)
public class DisputeMultiEditListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(DisputeMultiEditListener.class);

    @Autowired
    private DisputeManager disputeManager;

    @Autowired
    private NotificationManager notificationManager;

    @JmsListener(destination = DISPUTE_MULTI_EDIT_QUEUE)
    public void onMessage(final MultiEditRequestDto dto) {
        try {
            LOGGER.debug("Got message for dispute ids: {}", dto != null ? dto.getIds() : "null");
            SchedulerSecurityContext.runAsScheduler(() -> {
                MultiEditResponseDto response = disputeManager.multiEdit(dto);
                String header;
                StringBuilder body = new StringBuilder();
                String icon;
                if (response.getCantEdit().isEmpty()) {
                    header = "Multi edit completed successfully";
                    body = new StringBuilder(dto.getIds().size() + (dto.getIds().size() > 1 ? " disputes " : " dispute ") + "updated.");
                    icon = "done";
                } else {
                    header = "Multi edit completed with errors";
                    for (MultiEditError error : response.getCantEdit()) {
                        body.append(error.getDescription().getDisplayName()).append(" - ").append(error.getFields().toString() + "\n");
                    }
                    icon = "warning";
                }
                notificationManager.create(dto.getSubjectId(), header, body.toString(), icon, null);
            });
        } catch (Exception e) {
            LOGGER.error("Error processing message - {}", e.getMessage());
            if (dto != null && dto.getSubjectId() != null) {
                notificationManager.create(dto.getSubjectId(), "Multi edit failed", e.getMessage(), "error", null);
            }
        } finally {
            LOGGER.debug("Done with message");
        }
    }
}
