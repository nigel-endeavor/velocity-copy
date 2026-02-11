package com.endeavorms.velocity.qto.service.jms;

import com.endeavorms.velocity.qto.common.SchedulerSecurityContext;
import com.endeavorms.velocity.qto.notification.NotificationManager;
import com.endeavorms.velocity.qto.service.ServiceManager;
import com.endeavorms.velocity.qto.service.macd.MultiMacdRequestDto;
import com.endeavorms.velocity.qto.service.macd.response.MultiMacdError;
import com.endeavorms.velocity.qto.service.macd.response.MultiMacdResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import static com.endeavorms.velocity.qto.service.macd.MultiMacdQueueHandler.MULTI_MACD_QUEUE;

/**
 * Message driven bean for macd creation.
 * @author fcurran
 * @since 1.3.0
 */
@Component
public class MultiMacdListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(MultiMacdListener.class);

    @Autowired
    private ServiceManager serviceManager;

    @Autowired
    private NotificationManager notificationManager;

    @JmsListener(destination = MULTI_MACD_QUEUE)
    public void onMessage(final MultiMacdRequestDto dto) {
        try {
            LOGGER.debug("Got message for serviceIds: {}", dto != null ? dto.getIds() : "null");
            SchedulerSecurityContext.runAsScheduler(() -> {
                MultiMacdResponseDto response = serviceManager.multiCreateMacd(dto);
                String header;
                StringBuilder body = new StringBuilder();
                String icon;
                if (response.getCantEdit().isEmpty()) {
                    header = "Multi MACD creation completed successfully";
                    body.append(response.getCreated().size())
                            .append(response.getCreated().size() > 1 ? " services " : " service ")
                            .append("created.");
                    icon = "done";
                } else {
                    header = "Multi MACD creation completed with errors";
                    for (MultiMacdError error : response.getCantEdit()) {
                        body.append(error.getDescription().getDisplayName()).append(" - ")
                                .append(error.getError()).append("\n");
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
