package com.vertek.corporate.qto.disconnect;

import com.vertek.corporate.qto.authentication.Permissions;
import com.vertek.corporate.qto.common.AbstractResource;
import com.vertek.corporate.qto.common.PaginatedResult;
import com.vertek.corporate.qto.disconnect.multiedit.DisconnectMultiEditQueueHandler;
import com.vertek.corporate.qto.multiedit.request.MultiEditRequestDto;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.jboss.resteasy.annotations.Form;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 * @author fcurran
 * @since 1.3.0
 */
@Path("/disconnectViews")
@Consumes("application/json")
@Produces({"application/json", "application/vnd.ms-excel"})
public class DisconnectViewResource extends AbstractResource<DisconnectView> {
    /** Business methods associated with DisconnectViews. */
    @Inject
    private DisconnectViewManager manager;

    /** MultiEdit Message Queue Handler. */
    @Inject
    private DisconnectMultiEditQueueHandler multiEditQueueHandler;

    /**
     * Get a list of DisconnectViews.
     * @param criteria The search criteria.
     * @return A list of DisconnectViews.
     */
    @GET
    @RequiresPermissions(Permissions.ORDER_READ)
    public Response getDisconnectViews(@Form final DisconnectViewSearchCriteria criteria) {
        DisconnectViewSearchCriteria crit = getExportCriteria(criteria);
        if (crit.getFields() != null && crit.getFields().contains("address")) {
            crit.setFields(crit.getFields().replace("address", "address1,address2,city,stateProvince,postalCode"));
            crit.setHeaders(crit.getHeaders().replace("Address", "Address 1,Address 2,City,State/Province,Postal Code"));
        }
        PaginatedResult<DisconnectView> result = manager.findBySearchCriteria(crit);
        return getCollectionResource(result, crit, getLocation(DisconnectViewResource.class));
    }

    /**
     * Gets the meta data for the worklist built from the provided search criteria.
     * @param criteria The search criteria.
     * @return The meta data for the worklist.
     */
    @GET
    @Path("/meta")
    @RequiresPermissions(Permissions.ORDER_READ)
    public Response getDisconnectWorklistMeta(@Form final DisconnectViewSearchCriteria criteria) {
        DisconnectWorklistMeta meta = manager.getWorklistMeta(criteria);
        return Response.ok(meta).build();
    }

    /**
     * For editing a disconnect (a service) from the preview pane.
     * @param id the id of the service.
     * @param disconnectView the service to edit.
     * @return the updated record if successful.
     */
    @PUT
    @Path("/{id: \\d+}")
    @RequiresPermissions(Permissions.ORDER_WRITE)
    public Response edit(@PathParam("id") final Long id, final DisconnectView disconnectView) {
        try {
            if (!id.equals(disconnectView.getId())) {
                throw new IllegalArgumentException("identifier in path does not match that of passed entity");
            }
            DisconnectView updated = manager.edit(disconnectView);
            return Response.ok(updated).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    /**
     * Given a collection of dispute IDs and fields to process, loops through the disputes and makes the appropriate
     * changes/additions.
     * @param multiEditRequest the request.
     * @return a response indicating any failures.
     */
    @POST
    @Path("/multiEdit")
    @RequiresPermissions(value = {
            Permissions.INVENTORY_WRITE,
            Permissions.ORDER_WRITE}, logical = Logical.OR)
    public Response multiEdit(final MultiEditRequestDto multiEditRequest) {
        try {
            multiEditQueueHandler.sendMessageToQueue(multiEditRequest);
            return Response.accepted().build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }

    /**
     * Gets the service types for the worklist based on active services.
     * @return The service types for the worklist.
     */
    @GET
    @RequiresPermissions(Permissions.INVENTORY_READ)
    @Path("/serviceTypes")
    public Response getServiceTypes() {
        List<String> serviceType = manager.findServiceTypes();
        return Response.ok(serviceType).build();
    }
}
