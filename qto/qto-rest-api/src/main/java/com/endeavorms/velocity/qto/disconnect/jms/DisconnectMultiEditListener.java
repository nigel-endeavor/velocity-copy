package com.endeavorms.velocity.qto.disconnect.jms;

import com.endeavorms.velocity.qto.common.SchedulerSecurityContext;
import com.endeavorms.velocity.qto.multiedit.request.MultiEditRequestDto;
import com.endeavorms.velocity.qto.multiedit.response.MultiEditError;
import com.endeavorms.velocity.qto.multiedit.response.MultiEditResponseDto;
import com.endeavorms.velocity.qto.notification.NotificationManager;
import com.endeavorms.velocity.qto.service.ServiceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import static com.endeavorms.velocity.qto.disconnect.multiedit.DisconnectMultiEditQueueHandler.DISCONNECT_MULTI_EDIT_QUEUE;

@Component
public class DisconnectMultiEditListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(DisconnectMultiEditListener.class);

    @Autowired
    private ServiceManager serviceManager;

    @Autowired
    private NotificationManager notificationManager;

    @JmsListener(destination = DISCONNECT_MULTI_EDIT_QUEUE)
    public void onMessage(final MultiEditRequestDto dto) {
        try {
            LOGGER.debug("Got message for disconnect ids: {}", dto != null ? dto.getIds() : "null");
            SchedulerSecurityContext.runAsScheduler(() -> {
                MultiEditResponseDto response = serviceManager.multiEdit(dto);
                String header;
                StringBuilder body = new StringBuilder();
                String icon;
                if (response.getCantEdit().isEmpty()) {
                    header = "Multi edit completed successfully";
                    body = new StringBuilder(dto.getIds().size()
                            + (dto.getIds().size() > 1 ? " disconnects " : " disconnect ") + "updated.");
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
