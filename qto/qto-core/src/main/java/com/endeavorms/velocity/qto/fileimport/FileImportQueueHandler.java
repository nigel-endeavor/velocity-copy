package com.endeavorms.velocity.qto.fileimport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(prefix = "app.jms", name = "enabled", havingValue = "true")
public class FileImportQueueHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileImportQueueHandler.class);

    public static final String FILE_IMPORT_QUEUE = "qto.FileImportQueue";

    @Autowired
    private JmsTemplate jmsTemplate;

    public void sendImportToQueue(final Long id, final String type) {
        try {
            jmsTemplate.convertAndSend(FILE_IMPORT_QUEUE, new FileImportMessageDto(id, type));
            LOGGER.debug("Sent message to {}, id: {}, type: {}", FILE_IMPORT_QUEUE, id, type);
        } catch (Exception e) {
            LOGGER.error("Error sending message to queue: {}", FILE_IMPORT_QUEUE, e);
            throw new RuntimeException(e);
        }
    }

}
