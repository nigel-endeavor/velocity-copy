package com.endeavorms.velocity.qto.interceptors;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.endeavorms.velocity.qto.common.BadRequestError;
import com.endeavorms.velocity.qto.common.ValidationError;
import com.endeavorms.velocity.qto.invoicing.invoice.Invoice;
import com.endeavorms.velocity.qto.invoicing.invoice.InvoiceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InvoiceFinalizeInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceFinalizeInterceptor.class);

    private final InvoiceManager invoiceManager;

    public InvoiceFinalizeInterceptor(InvoiceManager invoiceManager) {
        this.invoiceManager = invoiceManager;
    }

    public BadRequestError validateForFinalize(Long invoiceId) {
        LOGGER.info("InvoiceFinalizeInterceptor.validateForFinalize() called");
        List<ValidationError> errors = Lists.newArrayList();
        Invoice invoice = invoiceManager.retrieve(invoiceId);
        if (!Strings.isNullOrEmpty(invoice.getInvoiceNumber())
                && invoice.getInvoiceNumber().equalsIgnoreCase("Legacy")) {
            LOGGER.debug("Trying to unfinalize the Legacy invoice.");
            errors.add(new ValidationError("Invoice", "This invoice cannot be reopened."));
        } else if (invoice.getInvoiceStatus().equals("Final")) {
            List<Invoice> existing = invoiceManager.findForUnfinalizeByTenantId(
                    invoice.getId(), invoice.getTenantId());
            if (!existing.isEmpty()) {
                LOGGER.debug("Draft Invoice already exists for tenant");
                errors.add(new ValidationError("Invoice", "This invoice cannot be reopened."));
            }
        }
        return errors.isEmpty() ? null : new BadRequestError(errors);
    }
}
