package com.vertek.corporate.qto.invocing.invoice;

import com.vertek.corporate.qto.authentication.Permissions;
import com.vertek.corporate.qto.common.AbstractResource;
import com.vertek.corporate.qto.common.PaginatedResult;
import com.vertek.corporate.qto.interceptors.InvoiceFinalizeInterceptor;
import com.vertek.corporate.qto.interceptors.InvoiceInterceptor;
import com.vertek.corporate.qto.invocing.jms.InvoiceChargeQueueHandler;
import com.vertek.corporate.qto.invoicing.invoice.Invoice;
import com.vertek.corporate.qto.invoicing.invoice.InvoiceManager;
import com.vertek.corporate.qto.invoicing.invoice.InvoiceSearchCriteria;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.jboss.resteasy.annotations.Form;

import jakarta.inject.Inject;
import jakarta.interceptor.Interceptors;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

/**
 * @author mwelicka
 * @since 8/09/2023
 */
@Path("/invoices")
@Consumes("application/json")
@Produces({"application/json", "application/vnd.ms-excel"})
public class InvoiceResource extends AbstractResource<Invoice> {

    @Inject
    private InvoiceManager manager;

    @Inject
    protected InvoiceChargeQueueHandler invoiceChargeQueueHandler;

    /**
     * Returns invoices that match the provided search criteria.
     *
     * @param criteria what to match invoices on.
     * @return the matching invoices, if any.
     */
    @GET
    @RequiresPermissions(Permissions.INVOICE_READ)
    public Response getInvoices(@Form final InvoiceSearchCriteria criteria) {
        InvoiceSearchCriteria crit = getExportCriteria(criteria);
        PaginatedResult<Invoice> result = manager.findBySearchCriteria(crit);
        return getCollectionResource(result, criteria, getLocation(InvoiceResource.class));
    }

    @GET
    @RequiresPermissions(Permissions.INVOICE_READ)
    @Path("/{id: \\d+}")
    public Response getInvoice(@PathParam("id") final Long id) {
        Invoice invoice = manager.retrieve(id);
        return Response.ok(invoice).build();
    }

    /**
     * Attempts to persist the provided invoice.
     *
     * @param tenantName the tenant to persist the invoice for.
     * @param invoice    the invoice to persist.
     * @return the persisted invoice.
     */
    @POST
    @RequiresPermissions(Permissions.INVOICE_WRITE)
    @Path("/{client: \\w+}")
    @Interceptors({InvoiceInterceptor.class})
    public Response create(@PathParam("client") final String tenantName, final Invoice invoice) {
        try {
            Invoice createdInvoice = manager.createInvoice(invoice, tenantName);
            return Response.ok(createdInvoice).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @PUT
    @RequiresPermissions(Permissions.INVOICE_WRITE)
    @Path("/{id: \\d+}")
    public Response edit(@PathParam("id") final Long id) {
        try {
            manager.sendMessageToChargeQueue(id, null);
            return Response.accepted().build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }

    @PUT
    @RequiresPermissions(Permissions.INVOICE_WRITE)
    @Path("/{id: \\d+}/finalize")
    @Interceptors({InvoiceFinalizeInterceptor.class})
    public Response finalize(@PathParam("id") final Long id) {
        try {
            manager.sendMessageToChargeQueue(id, "finalize");
            return Response.accepted().build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }
}
