package com.endeavorms.velocity.qto.interceptors;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.endeavorms.velocity.qto.common.BadRequestError;
import com.endeavorms.velocity.qto.common.ValidationError;
import com.endeavorms.velocity.qto.company.Company;
import com.endeavorms.velocity.qto.company.CompanyManager;
import com.endeavorms.velocity.qto.invoicing.invoice.Invoice;
import com.endeavorms.velocity.qto.invoicing.invoice.InvoiceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Intercepts calls to Invoice Manager.
 */
@Component
public class InvoiceInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceInterceptor.class);

    private final InvoiceManager invoiceManager;
    private final CompanyManager companyManager;

    public InvoiceInterceptor(InvoiceManager invoiceManager, CompanyManager companyManager) {
        this.invoiceManager = invoiceManager;
        this.companyManager = companyManager;
    }

    public BadRequestError validateForCreate(Invoice entity) {
        LOGGER.info("InvoiceInterceptor.validateForCreate() called");
        List<ValidationError> errors = Lists.newArrayList();
        String tenantName = entity.getClientName();
        if (Strings.isNullOrEmpty(tenantName) || tenantName.equalsIgnoreCase("undefined")) {
            errors.add(new ValidationError("Invoice", "Tenant name must be provided."));
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
        return errors.isEmpty() ? null : new BadRequestError(errors);
    }
}
