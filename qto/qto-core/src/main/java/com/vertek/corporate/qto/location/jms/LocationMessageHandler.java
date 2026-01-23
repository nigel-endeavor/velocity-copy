package com.vertek.corporate.qto.location.jms;

import com.vertek.corporate.qto.location.Location;
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

@Stateless
public class LocationMessageHandler {
     /** Private logger for this class. */
    private static final Logger LOGGER = LoggerFactory.getLogger(LocationMessageHandler.class);

    /** Connection factory name. */
    private static final String JMS_CONNECTION_FACTORY_NAME = "java:/JmsXA";

    /** JMS Connection factory. */
    @Resource(mappedName = JMS_CONNECTION_FACTORY_NAME)
    private QueueConnectionFactory connectionFactory;

    /** Queue for incoming messages. */
    public static final String LOCATION_QUEUE = "java:/queue/qto.LocationMessageQueue";

    /** JMS Queue. */
    @Resource(mappedName = LOCATION_QUEUE)
    private Queue locationQueue;
    public void sendMessageToQueue(final LocationMessage locationMessage) {
        String jmsMessageId = null;
        String jmsQueueName = null;

        try (QueueConnection connection = connectionFactory.createQueueConnection();
             QueueSession session = connection.createQueueSession(true, Session.SESSION_TRANSACTED);
             QueueSender sender = session.createSender(locationQueue)) {

            ObjectMessage message = session.createObjectMessage();
            message.setObject(locationMessage);

            jmsMessageId = message.getJMSMessageID();
            jmsQueueName = locationQueue.getQueueName();

            sender.send(message);
            LOGGER.debug("Sent message to {}, locationId: {}, messageType: {}",
                    jmsQueueName, locationMessage.getLocationId(), locationMessage.getMessageType());
        } catch (JMSException e) {
            LOGGER.error("Error processing: {} | {} | {}", jmsQueueName, jmsMessageId, e.getMessage());
        }
    }
}

