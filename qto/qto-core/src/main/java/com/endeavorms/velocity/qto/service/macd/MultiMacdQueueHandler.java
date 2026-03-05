package com.endeavorms.velocity.qto.service.macd;

import com.endeavorms.velocity.qto.common.SecurityUtils;
import com.endeavorms.velocity.qto.subject.Subject;
import com.endeavorms.velocity.qto.subject.SubjectManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

/**
 * This class is responsible for sending messages to the MultiMacdQueueHandler.
 * @author fcurran
 * @since 1.3.0
 */
@Component
public class MultiMacdQueueHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(MultiMacdQueueHandler.class);

    public static final String MULTI_MACD_QUEUE = "qto.MultiMacdQueue";

    // JMS disabled
    // private JmsTemplate jmsTemplate;

    @Autowired
    private SubjectManager subjectManager;

    public void sendMessageToQueue(final MultiMacdRequestDto dto) {
        try {
            String username = SecurityUtils.getLoggedInUser();
            Subject subject = subjectManager.findByUsername(username);
            dto.setSubjectId(subject.getId());

            // JMS disabled
            LOGGER.debug("Sent message to {}, serviceIds: {}", MULTI_MACD_QUEUE, dto.getIds());
        } catch (Exception e) {
            LOGGER.error("Error sending to {}: {}", MULTI_MACD_QUEUE, e.getMessage());
        }
    }

}
