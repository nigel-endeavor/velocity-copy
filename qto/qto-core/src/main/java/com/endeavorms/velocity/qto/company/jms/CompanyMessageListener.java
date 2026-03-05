package com.endeavorms.velocity.qto.company.jms;

import com.endeavorms.velocity.qto.common.SchedulerSecurityContext;
import com.endeavorms.velocity.qto.company.CompanyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import static com.endeavorms.velocity.qto.company.jms.CompanyMessageHandler.COMPANY_QUEUE;

@Component
@org.springframework.boot.autoconfigure.condition.ConditionalOnProperty(name = "qto.jms.enabled", havingValue = "true", matchIfMissing = false)
public class CompanyMessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyMessageListener.class);

    @Autowired
    private CompanyManager companyManager;

    @JmsListener(destination = COMPANY_QUEUE)
    public void onMessage(final CompanyMessage companyMessage) {
        try {
            SchedulerSecurityContext.runAsScheduler(() -> {
                if (companyMessage.getMessageType().equals("updateCounts")) {
                    companyManager.updateInventoryCounts(companyMessage.getCompanyId());
                } else {
                    LOGGER.error("Unknown message type: {}", companyMessage.getMessageType());
                }
            });
        } catch (Exception e) {
            LOGGER.error("Error processing message: {} {} ", companyMessage != null ? companyMessage.getCompanyId() : null, companyMessage != null ? companyMessage.getMessageType() : null);
        }
    }
}
