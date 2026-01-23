package com.vertek.corporate.qto;

import com.vertek.corporate.qto.authentication.Permissions;
import com.vertek.corporate.qto.common.AbstractResource;
import com.vertek.corporate.qto.interceptors.LocationMilestoneInterceptor;
import com.vertek.corporate.qto.milestone.LocationMilestoneInstance;
import com.vertek.corporate.qto.milestone.LocationMilestoneInstanceManager;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * @author rcasey
 * @since 2/16/2023
 */
@Path("/locationMilestoneInstances")
@Consumes("application/json")
@Produces("application/json")
public class LocationMilestoneInstanceResource extends AbstractResource<LocationMilestoneInstance> {

    @Inject
    private LocationMilestoneInstanceManager manager;

    @GET
    @RequiresPermissions(value = {
            Permissions.INVENTORY_READ,
            Permissions.ORDER_READ}, logical = Logical.OR)
    public Response getMilestoneInstances(@QueryParam("locationId") final Long locationId) {
        List<LocationMilestoneInstance> milestoneInstances = manager.listByRecord(locationId);
        return Response.ok(milestoneInstances).build();
    }

    @POST
    @Interceptors({LocationMilestoneInterceptor.class})
    @RequiresPermissions(value = {
            Permissions.INVENTORY_WRITE,
            Permissions.ORDER_WRITE}, logical = Logical.OR)
    public Response create(final LocationMilestoneInstance milestoneInstance) {
        try {
            LocationMilestoneInstance created = manager.create(milestoneInstance);
            return Response.ok(created).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id: \\d+}")
    @RequiresPermissions(value = {
            Permissions.INVENTORY_WRITE,
            Permissions.ORDER_WRITE}, logical = Logical.OR)
    public Response edit(@PathParam("id") final Long id, final LocationMilestoneInstance milestoneInstance) {
        try {
            if (!id.equals(milestoneInstance.getId())) {
                throw new IllegalArgumentException("identifier in path does not match that of passed entity");
            }
            LocationMilestoneInstance updated = manager.edit(milestoneInstance);
            return Response.ok(updated).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }
}
