package com.endeavorms.velocity.qto.invocing.invoiceCharge;

import com.endeavorms.velocity.qto.common.AbstractResource;
import com.endeavorms.velocity.qto.common.PaginatedResult;
import com.endeavorms.velocity.qto.common.PreconditionsUtil;
import com.endeavorms.velocity.qto.invoicing.invoiceCharge.InvoiceCharge;
import com.endeavorms.velocity.qto.invoicing.invoiceCharge.InvoiceChargeManager;
import com.endeavorms.velocity.qto.invoicing.invoiceCharge.InvoiceChargeSearchCriteria;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author mwelicka
 * @since 8/09/2023
 */
@RestController
@RequestMapping("/api/invoiceCharges")
public class InvoiceChargeResource extends AbstractResource<InvoiceCharge> {

    @Override
    protected String getResourcePath() {
        return "/invoiceCharges";
    }

    @Autowired
    private InvoiceChargeManager manager;

    @GetMapping
    @PreAuthorize("hasAuthority('invoice:read')")
    public ResponseEntity<?> getInvoiceCharges(@ModelAttribute final InvoiceChargeSearchCriteria criteria) {
        PreconditionsUtil.checkArgument(criteria.getInvoiceId(), "An Invoice ID is required");
        InvoiceChargeSearchCriteria crit = getExportCriteria(criteria);
        PaginatedResult<InvoiceCharge> result = manager.findBySearchCriteria(crit);
        return getCollectionResource(result, criteria, getLocation(InvoiceChargeResource.class));
    }
}
