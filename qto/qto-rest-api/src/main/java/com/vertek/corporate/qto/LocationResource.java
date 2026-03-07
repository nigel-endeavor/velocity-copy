package com.vertek.corporate.qto;

import com.vertek.corporate.qto.authentication.Permissions;
import com.vertek.corporate.qto.common.AbstractResource;
import com.vertek.corporate.qto.common.PaginatedResult;
import com.vertek.corporate.qto.interceptors.LocationDeleteInterceptor;
import com.vertek.corporate.qto.interceptors.LocationInterceptor;
import com.vertek.corporate.qto.location.Location;
import com.vertek.corporate.qto.location.LocationManager;
import com.vertek.corporate.qto.location.LocationSearchCriteria;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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

    /** Business methods for Locations. */
    @Inject
    private LocationManager manager;

    /**
     * Retrieves all Locations matching the given criteria.
     * @param criteria the criteria to filter by.
     * @return matching locations.
     */
    @GET
    @RequiresPermissions(value = {
            Permissions.INVENTORY_READ,
            Permissions.ORDER_READ}, logical = Logical.OR)
    public Response getLocations(@Form final LocationSearchCriteria criteria) {
        PaginatedResult<Location> result = manager.findBySearchCriteria(criteria);
        return getCollectionResource(result, criteria, getLocation(LocationResource.class));
    }

    @Interceptors({LocationInterceptor.class})
    @POST
    @RequiresPermissions(Permissions.ORDER_WRITE)
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
    @RequiresPermissions(value = {
            Permissions.INVENTORY_WRITE,
            Permissions.ORDER_WRITE}, logical = Logical.OR)
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
    @RequiresPermissions(value = {
            Permissions.INVENTORY_WRITE,
            Permissions.ORDER_WRITE}, logical = Logical.OR)
    public Response delete(@PathParam("id") final Long id) {
        manager.markForDeletion(id);
        return Response.ok().build();
    }
}
