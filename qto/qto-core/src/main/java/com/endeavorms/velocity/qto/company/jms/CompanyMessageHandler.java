package com.endeavorms.velocity.qto.company.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class CompanyMessageHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyMessageHandler.class);

    public static final String COMPANY_QUEUE = "qto.CompanyMessageQueue";

    @Autowired
    private JmsTemplate jmsTemplate;

    public void sendMessageToQueue(final CompanyMessage companyMessage) {
        try {
            jmsTemplate.convertAndSend(COMPANY_QUEUE, companyMessage);
            LOGGER.debug("Sent message to {}", COMPANY_QUEUE);
        } catch (Exception e) {
            LOGGER.error("Error sending to {}: {}", COMPANY_QUEUE, e.getMessage());
        }
    }

}
