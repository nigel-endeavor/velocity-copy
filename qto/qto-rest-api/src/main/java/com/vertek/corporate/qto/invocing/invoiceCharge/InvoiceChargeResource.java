package com.vertek.corporate.qto.invocing.invoiceCharge;

import com.vertek.corporate.qto.authentication.Permissions;
import com.vertek.corporate.qto.common.AbstractResource;
import com.vertek.corporate.qto.common.PaginatedResult;
import com.vertek.corporate.qto.common.PreconditionsUtil;
import com.vertek.corporate.qto.invoicing.invoiceCharge.InvoiceCharge;
import com.vertek.corporate.qto.invoicing.invoiceCharge.InvoiceChargeManager;
import com.vertek.corporate.qto.invoicing.invoiceCharge.InvoiceChargeSearchCriteria;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.jboss.resteasy.annotations.Form;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * @author mwelicka
 * @since 8/09/2023
 */
@Path("/invoiceCharges")
@Consumes("application/json")
@Produces({"application/json", "application/vnd.ms-excel"})
public class InvoiceChargeResource extends AbstractResource<InvoiceCharge> {

    @Inject
    private InvoiceChargeManager manager;
    /**
     * Returns invoiceCharges that match the provided search criteria.
     *
     * @param criteria what to match invoices on.
     * @return the matching invoices, if any.
     */
    @GET
    @RequiresPermissions(Permissions.INVOICE_READ)
    public Response getInvoiceCharges(@Form final InvoiceChargeSearchCriteria criteria) {
        PreconditionsUtil.checkArgument(criteria.getInvoiceId(), "An Invoice ID is required");
        InvoiceChargeSearchCriteria crit = getExportCriteria(criteria);
        PaginatedResult<InvoiceCharge> result = manager.findBySearchCriteria(crit);
        return getCollectionResource(result, criteria, getLocation(InvoiceChargeResource.class));
    }

}
