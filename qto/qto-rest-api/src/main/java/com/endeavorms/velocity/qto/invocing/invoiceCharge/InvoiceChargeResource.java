package com.endeavorms.velocity.qto.invocing.invoiceCharge;

import com.endeavorms.velocity.qto.common.AbstractResource;
import com.endeavorms.velocity.qto.common.PaginatedResult;
import com.endeavorms.velocity.qto.common.PreconditionsUtil;
import com.endeavorms.velocity.qto.invoicing.invoiceCharge.InvoiceCharge;
import com.endeavorms.velocity.qto.invoicing.invoiceCharge.InvoiceChargeManager;
import com.endeavorms.velocity.qto.invoicing.invoiceCharge.InvoiceChargeSearchCriteria;
import org.springframework.security.access.prepost.PreAuthorize;
import org.jboss.resteasy.annotations.Form;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

/**
 * @author mwelicka
 * @since 8/09/2023
 */
@Path("/invoiceCharges")
@Consumes("application/json")
@Produces({"application/json", "application/vnd.ms-excel"})
public class InvoiceChargeResource extends AbstractResource<InvoiceCharge> {

    @Override
    protected String getResourcePath() {
        return "/invoiceCharges";
    }

    @Inject
    private InvoiceChargeManager manager;
    /**
     * Returns invoiceCharges that match the provided search criteria.
     *
     * @param criteria what to match invoices on.
     * @return the matching invoices, if any.
     */
    @GET
    @PreAuthorize("hasAuthority('invoice:read')")
    public Response getInvoiceCharges(@Form final InvoiceChargeSearchCriteria criteria) {
        PreconditionsUtil.checkArgument(criteria.getInvoiceId(), "An Invoice ID is required");
        InvoiceChargeSearchCriteria crit = getExportCriteria(criteria);
        PaginatedResult<InvoiceCharge> result = manager.findBySearchCriteria(crit);
        return toResponse(getCollectionResource(result, criteria, getLocation(InvoiceChargeResource.class)));
    }

}
