package com.endeavorms.velocity.qto.fileimport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class FileImportQueueHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileImportQueueHandler.class);

    public static final String FILE_IMPORT_QUEUE = "qto.FileImportQueue";

    public void sendImportToQueue(final Long id, final String type) {
        // JMS disabled for standalone testing
        LOGGER.info("JMS disabled - would send to {}, id: {}, type: {}", FILE_IMPORT_QUEUE, id, type);
    }

}
