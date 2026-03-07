package com.vertek.corporate.qto;

import com.vertek.corporate.qto.authentication.Permissions;
import com.vertek.corporate.qto.common.AbstractResource;
import com.vertek.corporate.qto.interceptors.ServiceMilestoneInterceptor;
import com.vertek.corporate.qto.milestone.ServiceMilestoneInstance;
import com.vertek.corporate.qto.milestone.ServiceMilestoneInstanceManager;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import jakarta.inject.Inject;
import jakarta.interceptor.Interceptors;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 * @author rcasey
 * @since 2/20/2023
 */
@Path("/serviceMilestoneInstances")
@Consumes("application/json")
@Produces("application/json")
public class ServiceMilestoneInstanceResource extends AbstractResource<ServiceMilestoneInstance> {

    @Inject
    private ServiceMilestoneInstanceManager manager;

    @GET
    @RequiresPermissions(value = {
            Permissions.INVENTORY_READ,
            Permissions.ORDER_READ}, logical = Logical.OR)
    public Response getMilestoneInstances(@QueryParam("serviceId") final Long serviceId) {
        List<ServiceMilestoneInstance> milestoneInstances = manager.listByRecord(serviceId);
        return Response.ok(milestoneInstances).build();
    }

    @POST
    @Interceptors({ServiceMilestoneInterceptor.class})
    @RequiresPermissions(value = {
            Permissions.INVENTORY_WRITE,
            Permissions.ORDER_WRITE}, logical = Logical.OR)
    public Response create(final ServiceMilestoneInstance milestoneInstance) {
        try {
            ServiceMilestoneInstance created = manager.create(milestoneInstance);
            return Response.ok(created).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @PUT
    @Interceptors({ServiceMilestoneInterceptor.class})
    @Path("/{id: \\d+}")
    @RequiresPermissions(value = {
            Permissions.INVENTORY_WRITE,
            Permissions.ORDER_WRITE}, logical = Logical.OR)
    public Response edit(@PathParam("id") final Long id, final ServiceMilestoneInstance milestoneInstance) {
        try {
            if (!id.equals(milestoneInstance.getId())) {
                throw new IllegalArgumentException("identifier in path does not match that of passed entity");
            }
            ServiceMilestoneInstance updated = manager.edit(milestoneInstance);
            return Response.ok(updated).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }
}
