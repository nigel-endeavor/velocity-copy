package com.vertek.corporate.qto.company.jms;

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
public class CompanyMessageHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyMessageHandler.class);

    private static final String JMS_CONNECTION_FACTORY_NAME = "java:/JmsXA";

    @Resource(mappedName = JMS_CONNECTION_FACTORY_NAME)
    private QueueConnectionFactory connectionFactory;

    public static final String COMPANY_QUEUE = "java:/queue/qto.CompanyMessageQueue";

    @Resource(mappedName = COMPANY_QUEUE)
    private Queue companyQueue;

    public void sendMessageToQueue(final CompanyMessage companyMessage) {
        String jmsMessageId = null;
        String jmsQueueName = null;

        try (QueueConnection connection = connectionFactory.createQueueConnection();
             QueueSession session = connection.createQueueSession(true, Session.SESSION_TRANSACTED);
             QueueSender sender = session.createSender(companyQueue)) {

            ObjectMessage message = session.createObjectMessage();
            message.setObject(companyMessage);

            jmsMessageId = message.getJMSMessageID();
            jmsQueueName = companyQueue.getQueueName();

            sender.send(message);
        } catch (JMSException e) {
            LOGGER.error("Error processing: {} | {} | {}", jmsQueueName, jmsMessageId, e.getMessage());
        }
    }

}
