package com.vertek.corporate.qto.fileimport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.annotation.Resource;
import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.jms.MapMessage;
import jakarta.jms.Queue;
import jakarta.jms.QueueConnection;
import jakarta.jms.QueueConnectionFactory;
import jakarta.jms.QueueSender;
import jakarta.jms.QueueSession;

/**
 * @author rcasey
 * @since 9/1/2023
 */
@Stateless
public class FileImportQueueHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileImportQueueHandler.class);

    private static final String JMS_CONNECTION_FACTORY_NAME = "java:/JmsXA";

    public static final String FILE_IMPORT_QUEUE = "java:/queue/qto.FileImportQueue";

    /** JMS connection factory. */
    @Resource(mappedName = JMS_CONNECTION_FACTORY_NAME)
    private QueueConnectionFactory connectionFactory;

    /** JMS Queue. */
    @Resource(mappedName = FILE_IMPORT_QUEUE)
    private Queue queue;

    /**
     * Sends ImportActivity ID to JMS Queue.
     * @param id ImportActivity ID.
     */
    public void sendImportToQueue(final Long id, final String type) {

            String jmsQueueName = null;

            try (QueueConnection connection = connectionFactory.createQueueConnection();
                 QueueSession session = connection.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE)) {

                jmsQueueName = queue.getQueueName();
                MapMessage message = session.createMapMessage();
                message.setLong("id", id);
                message.setString("type", type);

                LOGGER.debug("About to send message to: {}, id: {}", jmsQueueName, id);

                try (QueueSender sender = session.createSender(queue)) {
                    sender.send(message);
                }
            } catch (Exception e) {
                LOGGER.error("Error sending message to queue: {}", jmsQueueName, e);
                throw new EJBException(e);
            }
    }

}
