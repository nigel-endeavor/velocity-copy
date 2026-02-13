package com.endeavorms.velocity.qto.interceptors;

import com.endeavorms.velocity.qto.invoicing.surcharge.service.ServiceSurcharge;
import com.endeavorms.velocity.qto.invoicing.surcharge.service.ServiceSurchargeManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * Intercepts API calls related to surcharges.
 */
@Component
public class SurchargeInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(SurchargeInterceptor.class);

    private final ServiceSurchargeManager serviceSurchargeManager;

    public SurchargeInterceptor(ServiceSurchargeManager serviceSurchargeManager) {
        this.serviceSurchargeManager = serviceSurchargeManager;
    }

    public void validateForEdit(ServiceSurcharge entity) {
        LOGGER.info("SurchargeInterceptor.validateForEdit() called");
        if (entity.getId() != null) {
            ServiceSurcharge existing = serviceSurchargeManager.retrieve(entity.getId());
            if (existing.getInvoiceChargeId() != null) {
                LOGGER.debug("surcharge {} is invoiced and is related to invoice_charge_id: {}, " +
                        "surcharge will not be updated", entity.getId(), entity.getInvoiceChargeId());
                throw new IllegalStateException("Surcharge has already been invoiced.");
            }
        }
    }

    public void validateForRemove(Long id) {
        LOGGER.info("SurchargeInterceptor.validateForRemove() called");
        ServiceSurcharge existing = serviceSurchargeManager.retrieve(id);
        if (existing.getInvoiceChargeId() != null) {
            throw new IllegalStateException("Surcharge has already been invoiced.");
        }
    }
}
