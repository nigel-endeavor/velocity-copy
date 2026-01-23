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

import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

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
