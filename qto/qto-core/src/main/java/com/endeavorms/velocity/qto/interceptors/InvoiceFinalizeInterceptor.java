package com.endeavorms.velocity.qto.interceptors;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.endeavorms.velocity.qto.common.BadRequestError;
import com.endeavorms.velocity.qto.common.ValidationError;
import com.endeavorms.velocity.qto.invoicing.invoice.Invoice;
import com.endeavorms.velocity.qto.invoicing.invoice.InvoiceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.InvocationContext;
import jakarta.ws.rs.core.Response;
import java.util.List;

public class InvoiceFinalizeInterceptor {
    /**
     * Logging Facade.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceFinalizeInterceptor.class);


    /**
     * The manager for Invoice.
     */
    @Inject
    private InvoiceManager invoiceManager;


    /**
     * the error list.
     */
    private List<ValidationError> errors;

    /**
     * Validates the Invoice Creation request.
     *
     * @param context the context of the invocation.
     * @param <X>     the type of Service.
     * @return the result of the invocation.
     * @throws Exception if the invocation fails.
     */
    @AroundInvoke
    public <X extends Invoice> Object validate(final InvocationContext context) throws Exception {
        LOGGER.info("Invoice.validate() called");
        errors = Lists.newArrayList();
        for (Object param : context.getParameters()) {
            if (param instanceof Long) {
                Long entity = (Long) param;
                Invoice invoice = invoiceManager.retrieve(entity);
                if (!Strings.isNullOrEmpty(invoice.getInvoiceNumber())
                        && invoice.getInvoiceNumber().equalsIgnoreCase("Legacy")) {
                    LOGGER.debug("Trying to unfinalize the Legacy invoice.");
                    errors.add(new ValidationError("Invoice",
                            "This invoice cannot be reopened."));
                } else if (invoice.getInvoiceStatus().equals("Final")) {
                    //trying to unfinalize an invoice.  Check to see there are no later invoices.
                    List<Invoice> existing = invoiceManager.findForUnfinalizeByTenantId(
                            invoice.getId(), invoice.getTenantId());
                    if (!existing.isEmpty()) {
                        LOGGER.debug("Draft Invoice already exists for tenant {}");
                        errors.add(new ValidationError("Invoice",
                                "This invoice cannot be reopened."));
                    }
                }
            }
        }
        if (errors.size() > 0) {
            BadRequestError error = new BadRequestError(errors);
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(error)
                    .build();
        }
        return context.proceed();
    }
}
