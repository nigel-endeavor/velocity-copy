package com.vertek.corporate.qto;

import com.vertek.corporate.qto.authentication.Permissions;
import com.vertek.corporate.qto.common.AbstractResource;
import com.vertek.corporate.qto.common.PaginatedResult;
import com.vertek.corporate.qto.note.OrderNote;
import com.vertek.corporate.qto.note.OrderNoteManager;
import com.vertek.corporate.qto.note.OrderNoteSearchCriteria;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.jboss.resteasy.annotations.Form;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * @author llevit
 * @since 1/16/2023
 */
@Path("/orderNotes")
@Consumes("application/json")
@Produces("application/json")
public class OrderNoteResource extends AbstractResource<OrderNote> {

    /** Business methods for Orders. */
    @Inject
    private OrderNoteManager manager;

    /**
     * Retrieves all OrderNotes matching the given criteria.
     * @param criteria the criteria to filter by.
     * @return matching orderNotes.
     */
    @GET
    @RequiresPermissions(value = {
            Permissions.INVENTORY_READ,
            Permissions.ORDER_READ}, logical = Logical.OR)
    public Response getOrderNotes(@Form final OrderNoteSearchCriteria criteria) {
        PaginatedResult<OrderNote> result = manager.findBySearchCriteria(criteria);
        return getCollectionResource(result, criteria, getLocation(OrderNoteResource.class));
    }

    @POST
    @RequiresPermissions(value = {
            Permissions.INVENTORY_WRITE,
            Permissions.ORDER_WRITE}, logical = Logical.OR)
    public Response create(final OrderNote note) {
        try {
            OrderNote returnedNote;
            if (note.getOrderId() == null) {
                throw new IllegalArgumentException("Missing orderId.");
            } else {
                returnedNote = manager.create(note);
            }
            return Response.ok(returnedNote).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }
}
