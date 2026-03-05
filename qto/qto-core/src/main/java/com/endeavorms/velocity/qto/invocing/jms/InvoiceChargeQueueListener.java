package com.endeavorms.velocity.qto.invocing.jms;

import com.endeavorms.velocity.qto.common.SchedulerSecurityContext;
import com.endeavorms.velocity.qto.invoicing.invoice.InvoiceManager;
import com.endeavorms.velocity.qto.invoicing.invoiceCharge.InvoiceChargeManager;
import com.endeavorms.velocity.qto.notification.NotificationManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import static com.endeavorms.velocity.qto.invocing.jms.InvoiceChargeQueueHandler.INVOICE_QUEUE;

/**
 * @author mwelicka
 * @since 08/10/2023
 */
@Component
@org.springframework.boot.autoconfigure.condition.ConditionalOnProperty(name = "qto.jms.enabled", havingValue = "true", matchIfMissing = false)
public class InvoiceChargeQueueListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceChargeQueueListener.class);

    @Autowired
    private InvoiceChargeManager invoiceChargeManager;

    @Autowired
    private InvoiceManager invoiceManager;

    @Autowired
    private NotificationManager notificationManager;

    @JmsListener(destination = INVOICE_QUEUE)
    public void onMessage(final InvoiceChargeMessage invoiceChargeMessage) {
        final Long subjectId = invoiceChargeMessage.getSubjectId();
        try {
            LOGGER.debug("Got invoice message: {}", invoiceChargeMessage);
            SchedulerSecurityContext.runAsScheduler(() ->
                invoiceChargeManager.generateInvoiceCharges(invoiceChargeMessage.getInvoiceId(), subjectId, invoiceChargeMessage.getMessageType()));
        } catch (Exception e) {
            LOGGER.error("Error processing message - {}", e.getMessage());
            notificationManager.create(subjectId, "Invoice Generation Failed", e.getMessage(), "error", null);
            throw new RuntimeException(e);
        } finally {
            LOGGER.debug("Done with message");
        }
    }
}
