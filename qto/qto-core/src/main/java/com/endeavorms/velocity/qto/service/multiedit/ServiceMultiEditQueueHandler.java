package com.endeavorms.velocity.qto.service.multiedit;

import com.endeavorms.velocity.qto.common.SecurityUtils;
import com.endeavorms.velocity.qto.multiedit.request.MultiEditRequestDto;
import com.endeavorms.velocity.qto.subject.Subject;
import com.endeavorms.velocity.qto.subject.SubjectManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

/**
 * @author rcasey
 * @since 7/14/2023
 */
@Component
public class ServiceMultiEditQueueHandler {

    /** Private logger for this class. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceMultiEditQueueHandler.class);

    /** Queue for incoming messages. */
    public static final String SERVICE_MULTI_EDIT_QUEUE = "qto.ServiceMultiEditQueue";

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private SubjectManager subjectManager;

    public void sendMessageToQueue(final MultiEditRequestDto dto) {
        try {
            String username = SecurityUtils.getLoggedInUser();
            Subject subject = subjectManager.findByUsername(username);
            dto.setSubjectId(subject.getId());

            jmsTemplate.convertAndSend(SERVICE_MULTI_EDIT_QUEUE, dto);
            LOGGER.debug("Sent message to {}, serviceIds: {}", SERVICE_MULTI_EDIT_QUEUE, dto.getIds());
        } catch (Exception e) {
            LOGGER.error("Error sending to {}: {}", SERVICE_MULTI_EDIT_QUEUE, e.getMessage());
        }
    }

}
