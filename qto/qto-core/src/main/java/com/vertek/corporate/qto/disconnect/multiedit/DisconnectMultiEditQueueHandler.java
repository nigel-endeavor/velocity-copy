package com.vertek.corporate.qto.disconnect.multiedit;

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
 * @author fcurran
 * @since 9/27/2023
 */
@Stateless
public class DisconnectMultiEditQueueHandler {

    /** Private logger for this class. */
    private static final Logger LOGGER = LoggerFactory.getLogger(DisconnectMultiEditQueueHandler.class);

    /** Connection factory name. */
    private static final String JMS_CONNECTION_FACTORY_NAME = "java:/JmsXA";

    /** JMS Connection factory. */
    @Resource(mappedName = JMS_CONNECTION_FACTORY_NAME)
    private QueueConnectionFactory connectionFactory;

    /** Queue for incoming messages. */
    public static final String DISCONNECT_MULTI_EDIT_QUEUE = "java:/queue/qto.DisconnectMultiEditQueue";

    /** JMS Queue. */
    @Resource(mappedName = DISCONNECT_MULTI_EDIT_QUEUE)
    private Queue disconnectMultiEditQueue;

    @Inject
    private SubjectManager subjectManager;

    public void sendMessageToQueue(final MultiEditRequestDto dto) {
        String jmsMessageId = null;
        String jmsQueueName = null;

        try (QueueConnection connection = connectionFactory.createQueueConnection();
            QueueSession session = connection.createQueueSession(true, Session.SESSION_TRANSACTED);
            QueueSender sender = session.createSender(disconnectMultiEditQueue)) {

            String username = SecurityUtils.getLoggedInUser();
            Subject subject = subjectManager.findByUsername(username);
            dto.setSubjectId(subject.getId());

            ObjectMessage message = session.createObjectMessage();
            message.setObject(dto);

            jmsMessageId = message.getJMSMessageID();
            jmsQueueName = disconnectMultiEditQueue.getQueueName();

            sender.send(message);
            LOGGER.debug("Sent message to {}, serviceIds: {}", jmsQueueName, dto.getIds());
        } catch (JMSException e) {
            LOGGER.error("Error processing: {} | {} | {}", jmsQueueName, jmsMessageId, e.getMessage());
        }
    }

}
