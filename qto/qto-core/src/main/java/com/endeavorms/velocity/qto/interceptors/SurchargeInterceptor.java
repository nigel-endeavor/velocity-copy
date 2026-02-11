package com.endeavorms.velocity.qto.interceptors;

import com.endeavorms.velocity.qto.invoicing.surcharge.service.ServiceSurcharge;
import com.endeavorms.velocity.qto.invoicing.surcharge.service.ServiceSurchargeManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.InvocationContext;

/**
 * Intercepts API calls related to surcharges.
 */
public class SurchargeInterceptor {
    /**
     * Logging Facade.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SurchargeInterceptor.class);

    /** Business logic for surcharges. */
    @Inject
    private ServiceSurchargeManager serviceSurchargeManager;

    /**
     * Validates the incoming Surcharge.
     * @param context the context of the invocation.
     * @return the result of the invocation.
     * @throws Exception if the invocation fails.
     */
    @AroundInvoke
    public Object validate(final InvocationContext context) throws Exception {
        LOGGER.info("SurchargeInterceptor.validate() called");
        for (Object param : context.getParameters()) {
            if (param instanceof ServiceSurcharge) {
                ServiceSurcharge entity = (ServiceSurcharge) param;
                if (entity.getId() != null) {
                    ServiceSurcharge existing = serviceSurchargeManager.retrieve(entity.getId());
                    if (existing.getInvoiceChargeId() != null) {
                        //TODO: once we have invoices and invoice charges, this will need to evolve
                        LOGGER.debug("surcharge {} is invoiced and is related to invoice_charge_id: {}, " +
                                "surcharge will not be updated", entity.getId(), entity.getInvoiceChargeId());
                        throw new IllegalStateException("Surcharge has already been invoiced.");
                    }
                }
            }
        }
        return context.proceed();
    }
}
