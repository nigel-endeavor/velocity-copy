package com.endeavorms.velocity.qto;

import com.endeavorms.velocity.qto.common.AbstractResource;
import com.endeavorms.velocity.qto.common.PaginatedResult;
import com.endeavorms.velocity.qto.jeop.LocationJeop;
import com.endeavorms.velocity.qto.jeop.LocationJeopManager;
import com.endeavorms.velocity.qto.jeop.JeopUnionView;
import com.endeavorms.velocity.qto.jeop.JeopUnionViewSearchCriteria;
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
@Path("/locationJeops")
@Consumes("application/json")
@Produces("application/json")
public class LocationJeopResource extends AbstractResource<JeopUnionView> {

    @Override
    protected String getResourcePath() {
        return "/locationJeops";
    }

    /** Business methods for Locations. */
    @Inject
    private LocationJeopManager manager;

    /**
     * Retrieves all LocationJeops matching the given criteria.
     * @param criteria the criteria to filter by.
     * @return matching locationJeops.
     */
    @GET
    @PreAuthorize("hasAnyAuthority('inventory:read','order:read')")
    public Response getLocationJeops(@Form final JeopUnionViewSearchCriteria criteria) {
        PaginatedResult<JeopUnionView> result = manager.findBySearchCriteria(criteria);
        return toResponse(getCollectionResource(result, criteria, getLocation(LocationJeopResource.class)));
    }

    @POST
    @PreAuthorize("hasAnyAuthority('inventory:write','order:write')")
    public Response create(final LocationJeop jeop) {
        try {
            LocationJeop returnedJeop;
            if (jeop.getLocationId() == null) {
                throw new IllegalArgumentException("Missing locationId.");
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
    public Response edit(@PathParam("id") final Long id, final LocationJeop jeop) {
        try {
            LocationJeop returnedJeop;
            if (!id.equals(jeop.getId())) {
                throw new IllegalArgumentException("identifier in path does not match that of passed entity");
            } else if (jeop.getLocationId() == null) {
                throw new IllegalArgumentException("Missing locationId.");
            } else {
                returnedJeop = manager.edit(jeop);
            }
            return Response.ok(returnedJeop).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }
}
