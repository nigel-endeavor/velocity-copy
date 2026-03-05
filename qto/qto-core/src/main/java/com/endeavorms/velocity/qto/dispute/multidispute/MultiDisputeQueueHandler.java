package com.endeavorms.velocity.qto.dispute.multidispute;

import com.endeavorms.velocity.qto.common.SecurityUtils;
import com.endeavorms.velocity.qto.subject.Subject;
import com.endeavorms.velocity.qto.subject.SubjectManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class MultiDisputeQueueHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(MultiDisputeQueueHandler.class);

    public static final String MULTI_DISPUTE_QUEUE = "qto.MultiDisputeQueue";

    // JMS disabled
    // private JmsTemplate jmsTemplate;

    @Autowired
    private SubjectManager subjectManager;

    public void sendMessageToQueue(final MultiDisputeRequestDto dto) {
        try {
            String username = SecurityUtils.getLoggedInUser();
            Subject subject = subjectManager.findByUsername(username);
            dto.setSubjectId(subject.getId());

            // JMS disabled
            LOGGER.debug("Sent message to {}, serviceIds: {}", MULTI_DISPUTE_QUEUE, dto.getServiceIds());
        } catch (Exception e) {
            LOGGER.error("Error sending to {}: {}", MULTI_DISPUTE_QUEUE, e.getMessage());
        }
    }

}
