package com.vertek.corporate.qto.quote.jms;

import com.vertek.corporate.qto.quote.Quote;
import com.vertek.corporate.qto.quote.QuoteManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.DependsOn;
import jakarta.ejb.EJBException;
import jakarta.ejb.MessageDriven;
import jakarta.inject.Inject;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;

/**
 * Dead letter queue for failed quote orders.
 */
@DependsOn("LiquibaseStartupBean")
@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "jakarta.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destination",
                propertyValue = QuoteProcessingFailureListener.QUOTE_ORDER_FAILURE_QUEUE),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")
})
public class QuoteProcessingFailureListener implements MessageListener {

    /** Private Logger for this class. */
    private static final Logger LOGGER = LoggerFactory.getLogger(QuoteProcessingFailureListener.class);

    /** QuoteOrder Dead Letter Queue. */
    public static final String QUOTE_ORDER_FAILURE_QUEUE = "java:/queue/qto.QuoteProcessingFailureQueue";

    /** Manager for a quote. */
    @Inject
    private QuoteManager quoteManager;

    @Override
    public void onMessage(final Message message) {
        String jmsMessageId = null;

        try {
            jmsMessageId = message.getJMSMessageID();
            LOGGER.trace("Got message JMSMessageId = {}", jmsMessageId);
            Long quoteId = message.getLongProperty("quoteId");
            Quote quote = quoteManager.retrieve(quoteId);
            LOGGER.debug("Received failed quote {}.", quote.getId());

            //Notify responsible party of failed quote order.
            //TODO: Email to a config property? dev@vertek.com
        } catch (Exception e) {
            throw new EJBException(e);
        } finally {
            LOGGER.trace("Done with message {}", jmsMessageId);
        }
    }
}
