package com.vertek.corporate.qto.invocing.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;

/**
 * @author mwelicka
 * @since 08/10/2023
 */
@Stateless
public class InvoiceChargeQueueHandler {
     /** Private logger for this class. */
    private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceChargeQueueHandler.class);

    /** Connection factory name. */
    private static final String JMS_CONNECTION_FACTORY_NAME = "java:/JmsXA";

    /** JMS Connection factory. */
    @Resource(mappedName = JMS_CONNECTION_FACTORY_NAME)
    private QueueConnectionFactory connectionFactory;

    /** Queue for incoming messages. */
    public static final String INVOICE_QUEUE = "java:/queue/qto.InvoiceChargeQueue";

    /** JMS Queue. */
    @Resource(mappedName = INVOICE_QUEUE)
    private Queue invoiceQueue;

    public void sendMessageToQueue(final InvoiceChargeMessage invoiceChargeMessage) {
        String jmsMessageId = null;
        String jmsQueueName = null;

        try (QueueConnection connection = connectionFactory.createQueueConnection();
             QueueSession session = connection.createQueueSession(true, Session.SESSION_TRANSACTED);
             QueueSender sender = session.createSender(invoiceQueue)) {

            ObjectMessage message = session.createObjectMessage();
            message.setObject(invoiceChargeMessage);

            jmsMessageId = message.getJMSMessageID();
            jmsQueueName = invoiceQueue.getQueueName();

            sender.send(message);
            LOGGER.debug("Sent message to {}, invoiceId: {}, messageType: {}",
                    jmsQueueName, invoiceChargeMessage.getInvoiceId(), invoiceChargeMessage.getMessageType());
        } catch (JMSException e) {
            LOGGER.error("Error processing: {} | {} | {}", jmsQueueName, jmsMessageId, e.getMessage());
        }
    }
}
