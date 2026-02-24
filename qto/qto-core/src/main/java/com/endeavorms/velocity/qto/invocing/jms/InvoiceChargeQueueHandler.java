package com.endeavorms.velocity.qto.invocing.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(prefix = "app.jms", name = "enabled", havingValue = "true")
public class InvoiceChargeQueueHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceChargeQueueHandler.class);

    public static final String INVOICE_QUEUE = "qto.InvoiceChargeQueue";

    @Autowired
    private JmsTemplate jmsTemplate;

    public void sendMessageToQueue(final InvoiceChargeMessage invoiceChargeMessage) {
        try {
            jmsTemplate.convertAndSend(INVOICE_QUEUE, invoiceChargeMessage);
            LOGGER.debug("Sent message to {}, invoiceId: {}, messageType: {}",
                    INVOICE_QUEUE, invoiceChargeMessage.getInvoiceId(), invoiceChargeMessage.getMessageType());
        } catch (Exception e) {
            LOGGER.error("Error sending to {}: {}", INVOICE_QUEUE, e.getMessage());
        }
    }
}
