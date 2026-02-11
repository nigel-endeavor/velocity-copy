package com.endeavorms.velocity.qto;

import com.endeavorms.velocity.qto.common.AbstractResource;
import com.endeavorms.velocity.qto.common.PaginatedResult;
import com.endeavorms.velocity.qto.note.OrderNote;
import com.endeavorms.velocity.qto.note.OrderNoteManager;
import com.endeavorms.velocity.qto.note.OrderNoteSearchCriteria;
import org.springframework.security.access.prepost.PreAuthorize;
import org.jboss.resteasy.annotations.Form;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

/**
 * @author llevit
 * @since 1/16/2023
 */
@Path("/orderNotes")
@Consumes("application/json")
@Produces("application/json")
public class OrderNoteResource extends AbstractResource<OrderNote> {

    @Override
    protected String getResourcePath() {
        return "/orderNotes";
    }

    /** Business methods for Orders. */
    @Inject
    private OrderNoteManager manager;

    /**
     * Retrieves all OrderNotes matching the given criteria.
     * @param criteria the criteria to filter by.
     * @return matching orderNotes.
     */
    @GET
    @PreAuthorize("hasAnyAuthority('inventory:read','order:read')")
    public Response getOrderNotes(@Form final OrderNoteSearchCriteria criteria) {
        PaginatedResult<OrderNote> result = manager.findBySearchCriteria(criteria);
        return toResponse(getCollectionResource(result, criteria, getLocation(OrderNoteResource.class)));
    }

    @POST
    @PreAuthorize("hasAnyAuthority('inventory:write','order:write')")
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
