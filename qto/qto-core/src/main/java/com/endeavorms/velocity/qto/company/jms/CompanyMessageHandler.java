package com.endeavorms.velocity.qto.company.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class CompanyMessageHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyMessageHandler.class);

    public static final String COMPANY_QUEUE = "qto.CompanyMessageQueue";

    private final JmsTemplate jmsTemplate;
    private final boolean jmsEnabled;

    public CompanyMessageHandler(
            @Nullable JmsTemplate jmsTemplate,
            @org.springframework.beans.factory.annotation.Value("${app.jms.enabled:false}") boolean jmsEnabled
    ) {
        this.jmsTemplate = jmsTemplate;
        this.jmsEnabled = jmsEnabled;
    }

    public void sendMessageToQueue(final CompanyMessage companyMessage) {
        if (!jmsEnabled) {
            LOGGER.debug("JMS disabled (app.jms.enabled=false). Skipping send to {}", COMPANY_QUEUE);
            return;
        }
        if (jmsTemplate == null) {
            LOGGER.warn("JMS enabled but no JmsTemplate bean configured. Skipping send to {}", COMPANY_QUEUE);
            return;
        }
        try {
            jmsTemplate.convertAndSend(COMPANY_QUEUE, companyMessage);
            LOGGER.debug("Sent message to {}", COMPANY_QUEUE);
        } catch (Exception e) {
            LOGGER.error("Error sending to {}: {}", COMPANY_QUEUE, e.getMessage());
        }
    }
}
