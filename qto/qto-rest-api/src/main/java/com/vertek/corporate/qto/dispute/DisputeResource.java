package com.vertek.corporate.qto.dispute;

import com.vertek.corporate.qto.authentication.Permissions;
import com.vertek.corporate.qto.common.AbstractResource;
import com.vertek.corporate.qto.common.PaginatedResult;
import com.vertek.corporate.qto.dispute.multidispute.MultiDisputeQueueHandler;
import com.vertek.corporate.qto.dispute.multidispute.MultiDisputeRequestDto;
import com.vertek.corporate.qto.dispute.multiedit.DisputeMultiEditQueueHandler;
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
 * @author llevit
 */
@Path("/disputes")
@Consumes("application/json")
@Produces("application/json")
public class DisputeResource extends AbstractResource<Dispute> {

    /** Business methods for Orders. */
    @Inject
    private DisputeManager manager;

    /** MultiEdit Message Queue Handler. */
    @Inject
    private DisputeMultiEditQueueHandler multiEditQueueHandler;

    /** Multi Dispute Message Queue Handler. */
    @Inject
    private MultiDisputeQueueHandler multiDisputeQueueHandler;

    /**
     * Retrieves all disputes matching the given criteria.
     * @param criteria the criteria to filter by.
     * @return matching disputes.
     */
    @GET
    @RequiresPermissions(value = {
            Permissions.INVENTORY_READ,
            Permissions.ORDER_READ}, logical = Logical.OR)
    public Response getDisputes(@Form final DisputeSearchCriteria criteria) {
        PaginatedResult<Dispute> result = manager.findBySearchCriteria(criteria);
        return getCollectionResource(result, criteria, getLocation(DisputeResource.class));
    }

    /**
     * Retrieves all disputes for a given service.
     * @param serviceId the service id to filter by.
     * @return matching disputes.
     */
    @GET
    @Path("/service/{serviceId: \\d+}")
    @RequiresPermissions(value = {
            Permissions.INVENTORY_READ,
            Permissions.ORDER_READ}, logical = Logical.OR)
    public Response getDisputes(@PathParam("serviceId") final Long serviceId) {
        List<Dispute> disputes = manager.findByServiceId(serviceId);
        return Response.ok(disputes).build();
    }

    @POST
    @RequiresPermissions(value = {
            Permissions.INVENTORY_WRITE,
            Permissions.ORDER_WRITE}, logical = Logical.OR)
    public Response create(final Dispute dispute) {
        try {
            Dispute returnedDispute;
            if (dispute.getServiceId() == null) {
                throw new IllegalArgumentException("Missing serviceId.");
            } else {
                returnedDispute = manager.create(dispute);
            }
            return Response.ok(returnedDispute).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id: \\d+}")
    @RequiresPermissions(value = {
            Permissions.INVENTORY_WRITE,
            Permissions.ORDER_WRITE}, logical = Logical.OR)
    public Response edit(@PathParam("id") final Long id, final Dispute dispute) {
        try {
            Dispute returnedDispute;
            if (!id.equals(dispute.getId())) {
                throw new IllegalArgumentException("identifier in path does not match that of passed entity");
            } else if (dispute.getServiceId() == null) {
                throw new IllegalArgumentException("Missing serviceId.");
            } else {
                returnedDispute = manager.edit(dispute);
            }
            return Response.ok(returnedDispute).build();
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
     * Gets the meta data for the grid built from the provided search criteria.
     * @param criteria The search criteria.
     * @return The meta data for the grid.
     */
    @GET
    @Path("/meta")
    @RequiresPermissions(value = {
            Permissions.INVENTORY_READ,
            Permissions.ORDER_READ}, logical = Logical.OR)
    public Response getDisputesGridMeta(@Form final DisputeSearchCriteria criteria) {
        DisputeGridMeta meta = manager.getDisputesGridMeta(criteria);
        return Response.ok(meta).build();
    }

    /**
     * Sends a message to the MultiDisputeQueue to create multiple disputes.
     * @param dto the request.
     * @return a response indicating the status of the request.
     */
    @POST
    @Path("/multiDispute")
    @RequiresPermissions(Permissions.INVENTORY_WRITE)
    public Response multiDispute(final MultiDisputeRequestDto dto) {
        try {
            multiDisputeQueueHandler.sendMessageToQueue(dto);
            return Response.accepted().build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }

}
