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

import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

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
