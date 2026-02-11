package com.endeavorms.velocity.qto.quote.jms;

import com.endeavorms.velocity.qto.quote.Quote;
import com.endeavorms.velocity.qto.quote.QuoteManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import jakarta.jms.Message;

/**
 * Dead letter queue for failed quote orders.
 */
@Component
public class QuoteProcessingFailureListener {

    /** Private Logger for this class. */
    private static final Logger LOGGER = LoggerFactory.getLogger(QuoteProcessingFailureListener.class);

    /** QuoteOrder Dead Letter Queue. */
    public static final String QUOTE_ORDER_FAILURE_QUEUE = "qto.QuoteProcessingFailureQueue";

    /** Manager for a quote. */
    @Autowired
    private QuoteManager quoteManager;

    @JmsListener(destination = QUOTE_ORDER_FAILURE_QUEUE)
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
            throw new RuntimeException(e);
        } finally {
            LOGGER.trace("Done with message {}", jmsMessageId);
        }
    }
}
