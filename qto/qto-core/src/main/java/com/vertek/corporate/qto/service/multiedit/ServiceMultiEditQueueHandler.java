package com.vertek.corporate.qto.service.multiedit;

import com.vertek.corporate.qto.common.SecurityUtils;
import com.vertek.corporate.qto.multiedit.request.MultiEditRequestDto;
import com.vertek.corporate.qto.subject.Subject;
import com.vertek.corporate.qto.subject.SubjectManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.annotation.Resource;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
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
 * @since 7/14/2023
 */
@Stateless
public class ServiceMultiEditQueueHandler {

    /** Private logger for this class. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceMultiEditQueueHandler.class);

    /** Connection factory name. */
    private static final String JMS_CONNECTION_FACTORY_NAME = "java:/JmsXA";

    /** JMS Connection factory. */
    @Resource(mappedName = JMS_CONNECTION_FACTORY_NAME)
    private QueueConnectionFactory connectionFactory;

    /** Queue for incoming messages. */
    public static final String SERVICE_MULTI_EDIT_QUEUE = "java:/queue/qto.ServiceMultiEditQueue";

    /** JMS Queue. */
    @Resource(mappedName = SERVICE_MULTI_EDIT_QUEUE)
    private Queue serviceMultiEditQueue;

    @Inject
    private SubjectManager subjectManager;

    public void sendMessageToQueue(final MultiEditRequestDto dto) {
        String jmsMessageId = null;
        String jmsQueueName = null;

        try (QueueConnection connection = connectionFactory.createQueueConnection();
             QueueSession session = connection.createQueueSession(true, Session.SESSION_TRANSACTED);
             QueueSender sender = session.createSender(serviceMultiEditQueue)) {

            String username = SecurityUtils.getLoggedInUser();
            Subject subject = subjectManager.findByUsername(username);
            dto.setSubjectId(subject.getId());

            ObjectMessage message = session.createObjectMessage();
            message.setObject(dto);

            jmsMessageId = message.getJMSMessageID();
            jmsQueueName = serviceMultiEditQueue.getQueueName();

            sender.send(message);
            LOGGER.debug("Sent message to {}, serviceIds: {}", jmsQueueName, dto.getIds());
        } catch (JMSException e) {
            LOGGER.error("Error processing: {} | {} | {}", jmsQueueName, jmsMessageId, e.getMessage());
        }
    }

}
