package com.endeavorms.velocity.qto;

import com.endeavorms.velocity.qto.common.AbstractResource;
import com.endeavorms.velocity.qto.common.PaginatedResult;
import com.endeavorms.velocity.qto.interceptors.LocationDeleteInterceptor;
import com.endeavorms.velocity.qto.interceptors.LocationInterceptor;
import com.endeavorms.velocity.qto.location.Location;
import com.endeavorms.velocity.qto.location.LocationManager;
import com.endeavorms.velocity.qto.location.LocationSearchCriteria;
import org.springframework.security.access.prepost.PreAuthorize;
import org.jboss.resteasy.annotations.Form;

import jakarta.inject.Inject;
import jakarta.interceptor.Interceptors;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

/**
 * @author rcasey
 * @since 1/10/2023
 */
@Path("/locations")
@Consumes("application/json")
@Produces("application/json")
public class LocationResource extends AbstractResource<Location> {

    @Override
    protected String getResourcePath() {
        return "/locations";
    }

    /** Business methods for Locations. */
    @Inject
    private LocationManager manager;

    /**
     * Retrieves all Locations matching the given criteria.
     * @param criteria the criteria to filter by.
     * @return matching locations.
     */
    @GET
    @PreAuthorize("hasAnyAuthority('inventory:read','order:read')")
    public Response getLocations(@Form final LocationSearchCriteria criteria) {
        PaginatedResult<Location> result = manager.findBySearchCriteria(criteria);
        return toResponse(getCollectionResource(result, criteria, getLocation(LocationResource.class)));
    }

    @Interceptors({LocationInterceptor.class})
    @POST
    @PreAuthorize("hasAuthority('order:write')")
    public Response create(final Location location) {
        try {
            Location created = manager.create(location);
            return Response.ok(created).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @Interceptors({LocationInterceptor.class})
    @PUT
    @Path("/{id: \\d+}")
    @PreAuthorize("hasAnyAuthority('inventory:write','order:write')")
    public Response edit(@PathParam("id") final Long id, final Location location) {
        try {
            if (!id.equals(location.getId())) {
                throw new IllegalArgumentException("identifier in path does not match that of passed entity");
            }
            Location edited = manager.edit(location);
            return Response.ok(edited).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }


    @Interceptors({LocationDeleteInterceptor.class})
    @DELETE
    @Path("/{id}")
    @PreAuthorize("hasAnyAuthority('inventory:write','order:write')")
    public Response delete(@PathParam("id") final Long id) {
        manager.markForDeletion(id);
        return Response.ok().build();
    }
}
