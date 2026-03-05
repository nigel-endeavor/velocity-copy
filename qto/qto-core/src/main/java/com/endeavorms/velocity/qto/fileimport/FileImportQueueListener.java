package com.endeavorms.velocity.qto.fileimport;

import com.endeavorms.velocity.qto.common.SchedulerSecurityContext;
import com.endeavorms.velocity.qto.fileimport.broadband.BroadbandImporter;
import com.endeavorms.velocity.qto.fileimport.crossconnect.CrossConnectImporter;
import com.endeavorms.velocity.qto.fileimport.customer.CustomerImporter;
import com.endeavorms.velocity.qto.fileimport.customer.EndCustomerImporter;
import com.endeavorms.velocity.qto.fileimport.dia.DiaImporter;
import com.endeavorms.velocity.qto.fileimport.ethernet.EthernetImporter;
import com.endeavorms.velocity.qto.fileimport.importactivity.ImportActivity;
import com.endeavorms.velocity.qto.fileimport.importactivity.ImportActivityManager;
import com.endeavorms.velocity.qto.fileimport.importactivity.ImportActivityWebsocket;
import com.endeavorms.velocity.qto.fileimport.mpls.MplsImporter;
import com.endeavorms.velocity.qto.fileimport.television.TelevisionImporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import static com.endeavorms.velocity.qto.fileimport.FileImportQueueHandler.FILE_IMPORT_QUEUE;

@Component
@org.springframework.boot.autoconfigure.condition.ConditionalOnProperty(name = "qto.jms.enabled", havingValue = "true", matchIfMissing = false)
public class FileImportQueueListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileImportQueueListener.class);

    @Autowired
    private CustomerImporter masterCustomerImporter;

    @Autowired
    private EndCustomerImporter endCustomerImporter;

    @Autowired
    private BroadbandImporter broadbandImporter;

    @Autowired
    private DiaImporter diaImporter;

    @Autowired
    private CrossConnectImporter crossConnectImporter;

    @Autowired
    private EthernetImporter ethernetImporter;

    @Autowired
    private TelevisionImporter televisionImporter;

    @Autowired
    private MplsImporter mplsImporter;

    @Autowired
    private ImportActivityManager importActivityManager;

    @Autowired
    private ImportActivityWebsocket importActivityWebsocket;

    @JmsListener(destination = FILE_IMPORT_QUEUE)
    public void onMessage(final FileImportMessageDto message) {
        try {
            SchedulerSecurityContext.runAsScheduler(() -> {
                Long id = message.id();
                String type = message.type();
                LOGGER.debug("Got message for ImportActivity: {}, type: {}", id, type);

                switch (type) {
                    case "Master Customer":
                        masterCustomerImporter.importFile(id);
                        break;
                    case "End Customer":
                        endCustomerImporter.importFile(id);
                        break;
                    case "Broadband":
                        broadbandImporter.importFile(id);
                        break;
                    case "DIA":
                        diaImporter.importFile(id);
                        break;
                    case "Cross Connect":
                        crossConnectImporter.importFile(id);
                        break;
                    case "Ethernet":
                        ethernetImporter.importFile(id);
                        break;
                    case "Television":
                        televisionImporter.importFile(id);
                        break;
                    case "MPLS":
                        mplsImporter.importFile(id);
                        break;
                    default:
                        ImportActivity activity = importActivityManager.retrieve(id);
                        activity.setStatus(ImportActivityStatus.SYSTEM_ERROR);
                        activity.setStatusDetails(type + " is not a supported import type");
                        importActivityManager.edit(activity);
                        importActivityWebsocket.sendRefreshMessage();
                }
            });
        } catch (Exception e) {
            LOGGER.error("Error processing file import message", e);
            throw new RuntimeException("Error processing file import message", e);
        }
    }
}
