package com.endeavorms.velocity.qto.disconnect.multiedit;

import com.endeavorms.velocity.qto.common.SecurityUtils;
import com.endeavorms.velocity.qto.multiedit.request.MultiEditRequestDto;
import com.endeavorms.velocity.qto.subject.Subject;
import com.endeavorms.velocity.qto.subject.SubjectManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class DisconnectMultiEditQueueHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(DisconnectMultiEditQueueHandler.class);

    public static final String DISCONNECT_MULTI_EDIT_QUEUE = "qto.DisconnectMultiEditQueue";

    // JMS disabled
    // private JmsTemplate jmsTemplate;

    @Autowired
    private SubjectManager subjectManager;

    public void sendMessageToQueue(final MultiEditRequestDto dto) {
        try {
            String username = SecurityUtils.getLoggedInUser();
            Subject subject = subjectManager.findByUsername(username);
            dto.setSubjectId(subject.getId());

            // JMS disabled
            LOGGER.debug("Sent message to {}, serviceIds: {}", DISCONNECT_MULTI_EDIT_QUEUE, dto.getIds());
        } catch (Exception e) {
            LOGGER.error("Error sending to {}: {}", DISCONNECT_MULTI_EDIT_QUEUE, e.getMessage());
        }
    }

}
