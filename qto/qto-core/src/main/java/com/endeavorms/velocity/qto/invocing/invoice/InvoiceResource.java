package com.endeavorms.velocity.qto.invocing.invoice;

import com.endeavorms.velocity.qto.common.AbstractResource;
import com.endeavorms.velocity.qto.common.BadRequestError;
import com.endeavorms.velocity.qto.common.PaginatedResult;
import com.endeavorms.velocity.qto.interceptors.InvoiceFinalizeInterceptor;
import com.endeavorms.velocity.qto.interceptors.InvoiceInterceptor;
import com.endeavorms.velocity.qto.invoicing.invoice.Invoice;
import com.endeavorms.velocity.qto.invoicing.invoice.InvoiceManager;
import com.endeavorms.velocity.qto.invoicing.invoice.InvoiceSearchCriteria;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author mwelicka
 * @since 8/09/2023
 */
@RestController
@RequestMapping("/api/invoices")
public class InvoiceResource extends AbstractResource<Invoice> {

    @Override
    protected String getResourcePath() {
        return "/invoices";
    }

    @Autowired
    private InvoiceManager manager;

    @Autowired
    private InvoiceInterceptor invoiceInterceptor;

    @Autowired
    private InvoiceFinalizeInterceptor invoiceFinalizeInterceptor;

    @GetMapping
    @PreAuthorize("hasAuthority('invoice:read')")
    public ResponseEntity<?> getInvoices(@ModelAttribute final InvoiceSearchCriteria criteria) {
        InvoiceSearchCriteria crit = getExportCriteria(criteria);
        PaginatedResult<Invoice> result = manager.findBySearchCriteria(crit);
        return getCollectionResource(result, criteria, getLocation(InvoiceResource.class));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('invoice:read')")
    public ResponseEntity<?> getInvoice(@PathVariable("id") final Long id) {
        Invoice invoice = manager.retrieve(id);
        return ResponseEntity.ok(invoice);
    }

    @PostMapping("/{client}")
    @PreAuthorize("hasAuthority('invoice:write')")
    public ResponseEntity<?> create(@PathVariable("client") final String tenantName, @RequestBody final Invoice invoice) {
        try {
            invoice.setClientName(tenantName);
            BadRequestError validationError = invoiceInterceptor.validateForCreate(invoice);
            if (validationError != null) {
                return ResponseEntity.badRequest().body(validationError);
            }
            Invoice createdInvoice = manager.createInvoice(invoice, tenantName);
            return ResponseEntity.ok(createdInvoice);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('invoice:write')")
    public ResponseEntity<?> edit(@PathVariable("id") final Long id) {
        try {
            manager.sendMessageToChargeQueue(id, null);
            return ResponseEntity.accepted().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{id}/finalize")
    @PreAuthorize("hasAuthority('invoice:write')")
    public ResponseEntity<?> finalize(@PathVariable("id") final Long id) {
        try {
            BadRequestError validationError = invoiceFinalizeInterceptor.validateForFinalize(id);
            if (validationError != null) {
                return ResponseEntity.badRequest().body(validationError);
            }
            manager.sendMessageToChargeQueue(id, "finalize");
            return ResponseEntity.accepted().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
