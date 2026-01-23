package com.vertek.corporate.qto.interceptors;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.vertek.corporate.qto.common.BadRequestError;
import com.vertek.corporate.qto.common.ValidationError;
import com.vertek.corporate.qto.company.Company;
import com.vertek.corporate.qto.company.CompanyManager;
import com.vertek.corporate.qto.invoicing.invoice.Invoice;
import com.vertek.corporate.qto.invoicing.invoice.InvoiceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Intercepts calls to Invoice Manager.
 */
public class InvoiceInterceptor {
    /**
     * Logging Facade.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceInterceptor.class);


    /**
     * The manager for Invoice.
     */
    @Inject
    private InvoiceManager invoiceManager;

    /**
     * The manager for Company.
     */
    @Inject
    private CompanyManager companyManager;

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
        String tenantName = null;
        for (Object param : context.getParameters()) {
            if (param instanceof Invoice) {
                Invoice entity = (Invoice) param;
                tenantName = entity.getClientName();
                if (Strings.isNullOrEmpty(tenantName) || tenantName.equalsIgnoreCase("undefined")) {
                    errors.add(new ValidationError("Invoice",
                            "Tenant name must be provided."));
                } else {
                    Company tenant = companyManager.findTenantByName(tenantName);
                    Long tenantId = tenant.getTenantId();
                    List<Invoice> existing = invoiceManager.findDraftByTenantId(tenantId);
                    if (!existing.isEmpty()) {
                        LOGGER.debug("Draft Invoice already exists for tenant {}", tenantName);
                        errors.add(new ValidationError("Invoice",
                                "You must finalize the existing draft invoice before creating a new one."));
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
