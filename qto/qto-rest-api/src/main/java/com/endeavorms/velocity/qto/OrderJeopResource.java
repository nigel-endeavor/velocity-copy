package com.endeavorms.velocity.qto;

import com.endeavorms.velocity.qto.common.AbstractResource;
import com.endeavorms.velocity.qto.common.PaginatedResult;
import com.endeavorms.velocity.qto.jeop.JeopUnionView;
import com.endeavorms.velocity.qto.jeop.JeopUnionViewSearchCriteria;
import com.endeavorms.velocity.qto.jeop.OrderJeop;
import com.endeavorms.velocity.qto.jeop.OrderJeopManager;
import org.springframework.security.access.prepost.PreAuthorize;
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

/**
 * @author llevit
 */
@Path("/orderJeops")
@Consumes("application/json")
@Produces("application/json")
public class OrderJeopResource extends AbstractResource<JeopUnionView> {

    @Override
    protected String getResourcePath() {
        return "/orderJeops";
    }

    /** Business methods for Orders. */
    @Inject
    private OrderJeopManager manager;

    /**
     * Retrieves all OrderJeops matching the given criteria.
     * @param criteria the criteria to filter by.
     * @return matching orderJeops.
     */
    @GET
    @PreAuthorize("hasAnyAuthority('inventory:read','order:read')")
    public Response getOrderJeops(@Form final JeopUnionViewSearchCriteria criteria) {
        PaginatedResult<JeopUnionView> result = manager.findBySearchCriteria(criteria);
        return toResponse(getCollectionResource(result, criteria, getLocation(OrderJeopResource.class)));
    }

    @POST
    @PreAuthorize("hasAnyAuthority('inventory:write','order:write')")
    public Response create(final OrderJeop jeop) {
        try {
            OrderJeop returnedJeop;
            if (jeop.getOrderId() == null) {
                throw new IllegalArgumentException("Missing orderId.");
            } else {
                returnedJeop = manager.create(jeop);
            }
            return Response.ok(returnedJeop).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id: \\d+}")
    @PreAuthorize("hasAnyAuthority('inventory:write','order:write')")
    public Response edit(@PathParam("id") final Long id, final OrderJeop jeop) {
        try {
            OrderJeop returnedJeop;
            if (!id.equals(jeop.getId())) {
                throw new IllegalArgumentException("identifier in path does not match that of passed entity");
            } else if (jeop.getOrderId() == null) {
                throw new IllegalArgumentException("Missing orderId.");
            } else {
                returnedJeop = manager.edit(jeop);
            }
            return Response.ok(returnedJeop).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }
}
