package com.vertek.corporate.qto.dispute.multiedit;

import com.vertek.corporate.qto.common.SecurityUtils;
import com.vertek.corporate.qto.multiedit.request.MultiEditRequestDto;
import com.vertek.corporate.qto.subject.Subject;
import com.vertek.corporate.qto.subject.SubjectManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;

/**
 * @author fcurran
 * @since 9/27/2023
 */
@Stateless
public class DisputeMultiEditQueueHandler {

    /** Private logger for this class. */
    private static final Logger LOGGER = LoggerFactory.getLogger(DisputeMultiEditQueueHandler.class);

    /** Connection factory name. */
    private static final String JMS_CONNECTION_FACTORY_NAME = "java:/JmsXA";

    /** JMS Connection factory. */
    @Resource(mappedName = JMS_CONNECTION_FACTORY_NAME)
    private QueueConnectionFactory connectionFactory;

    /** Queue for incoming messages. */
    public static final String DISPUTE_MULTI_EDIT_QUEUE = "java:/queue/qto.DisputeMultiEditQueue";

    /** JMS Queue. */
    @Resource(mappedName = DISPUTE_MULTI_EDIT_QUEUE)
    private Queue disputeMultiEditQueue;

    @Inject
    private SubjectManager subjectManager;

    public void sendMessageToQueue(final MultiEditRequestDto dto) {
        String jmsMessageId = null;
        String jmsQueueName = null;

        try (QueueConnection connection = connectionFactory.createQueueConnection();
            QueueSession session = connection.createQueueSession(true, Session.SESSION_TRANSACTED);
            QueueSender sender = session.createSender(disputeMultiEditQueue)) {

            String username = SecurityUtils.getLoggedInUser();
            Subject subject = subjectManager.findByUsername(username);
            dto.setSubjectId(subject.getId());

            ObjectMessage message = session.createObjectMessage();
            message.setObject(dto);

            jmsMessageId = message.getJMSMessageID();
            jmsQueueName = disputeMultiEditQueue.getQueueName();

            sender.send(message);
            LOGGER.debug("Sent message to {}, disputeIds: {}", jmsQueueName, dto.getIds());
        } catch (JMSException e) {
            LOGGER.error("Error processing: {} | {} | {}", jmsQueueName, jmsMessageId, e.getMessage());
        }
    }

}
