package com.vertek.corporate.qto.interval.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.annotation.Resource;
import jakarta.ejb.Stateless;
import jakarta.jms.JMSException;
import jakarta.jms.ObjectMessage;
import jakarta.jms.Queue;
import jakarta.jms.QueueConnection;
import jakarta.jms.QueueConnectionFactory;
import jakarta.jms.QueueSender;
import jakarta.jms.QueueSession;
import jakarta.jms.Session;

/**
 * @author rcasey
 * @since 3/13/2023
 */
@Stateless
public class IntervalQueueHandler {

    /** Private logger for this class. */
    private static final Logger LOGGER = LoggerFactory.getLogger(IntervalQueueHandler.class);

    /** Connection factory name. */
    private static final String JMS_CONNECTION_FACTORY_NAME = "java:/JmsXA";

    /** JMS Connection factory. */
    @Resource(mappedName = JMS_CONNECTION_FACTORY_NAME)
    private QueueConnectionFactory connectionFactory;

    /** Queue for incoming messages. */
    public static final String INTERVALS_QUEUE = "java:/queue/qto.IntervalQueue";

    /** JMS Queue. */
    @Resource(mappedName = INTERVALS_QUEUE)
    private Queue intervalQueue;

    public void sendMessageToQueue(final IntervalMessage intervalMessage) {
        String jmsMessageId = null;
        String jmsQueueName = null;

        try (QueueConnection connection = connectionFactory.createQueueConnection();
             QueueSession session = connection.createQueueSession(true, Session.SESSION_TRANSACTED);
             QueueSender sender = session.createSender(intervalQueue)) {

            ObjectMessage message = session.createObjectMessage();
            message.setObject(intervalMessage);

            jmsMessageId = message.getJMSMessageID();
            jmsQueueName = intervalQueue.getQueueName();

            sender.send(message);
            LOGGER.debug("Sent message to {}, entityId: {}, milestoneInstanceId: {}, messageType: {}",
                    jmsQueueName, intervalMessage.getEntityId(),
                    intervalMessage.getMilestoneInstanceId(), intervalMessage.getMessageType());
        } catch (JMSException e) {
            LOGGER.error("Error processing: {} | {} | {}", jmsQueueName, jmsMessageId, e.getMessage());
        }
    }
}
