package com.endeavorms.velocity.qto.company.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CompanyMessageHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyMessageHandler.class);

    public static final String COMPANY_QUEUE = "qto.CompanyMessageQueue";

    public void sendMessageToQueue(final CompanyMessage companyMessage) {
        // JMS disabled for standalone testing - log instead
        LOGGER.info("JMS disabled - would send to {}: {}", COMPANY_QUEUE, companyMessage);
    }

}
