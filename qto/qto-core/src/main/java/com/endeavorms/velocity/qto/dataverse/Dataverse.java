package com.endeavorms.velocity.qto.dataverse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Stub implementation - Azure/Dataverse removed per migration plan.
 * Replace with CRM integration as needed.
 */
@Component
public class Dataverse {

    private static final Logger LOGGER = LoggerFactory.getLogger(Dataverse.class);

    public String pullFromCrm(final String opportunityNum) {
        LOGGER.warn("Dataverse stub: Azure removed. pullFromCrm({}) not implemented", opportunityNum);
        throw new UnsupportedOperationException("Dataverse integration removed - configure CRM integration");
    }
}
