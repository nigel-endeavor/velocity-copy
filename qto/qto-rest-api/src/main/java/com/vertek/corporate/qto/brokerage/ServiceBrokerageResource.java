package com.vertek.corporate.qto.brokerage;

import com.vertek.corporate.qto.authentication.Permissions;
import com.vertek.corporate.qto.common.AbstractResource;
import com.vertek.corporate.qto.interceptors.ServiceBrokerageInterceptor;
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
import javax.ws.rs.core.Response;

/**
 * @author fcurran
 * @since 6/19/2024
 */
@Path("/serviceBrokerages")
@Consumes("application/json")
@Produces("application/json")
public class ServiceBrokerageResource extends AbstractResource<ServiceBrokerage> {
    /** The manager for the concrete Resource. */
    @Inject
    private ServiceBrokerageManager manager;

    /**
     * Retrieves an object of type ServiceBrokerage.
     * @param serviceId the serviceId of the desired entity.
     * @return the matching entity if it exists.
     */
    @GET
    @Path("/{serviceId: \\d+}")
    @RequiresPermissions(value = {
            Permissions.INVENTORY_READ,
            Permissions.ORDER_READ}, logical = Logical.OR)
    public Response retrieve(@PathParam("serviceId") final Long serviceId) {
        ServiceBrokerage retrieved = manager.findByServiceId(serviceId);
        return Response.ok(retrieved).build();
    }

    /**
     * Persists the incoming entity.
     * @param entity the entity to persist.
     * @return the created entity, with an ID.
     */
    @POST
    @Interceptors({ServiceBrokerageInterceptor.class})
    @RequiresPermissions(value = {
            Permissions.INVENTORY_WRITE,
            Permissions.ORDER_WRITE}, logical = Logical.OR)
    public Response create(final ServiceBrokerage entity) {
        ServiceBrokerage created = manager.create(entity);
        return Response.ok(created).build();
    }

    /**
     * Updates the incoming entity.
     * @param entity the entity to update.
     * @return the updated entity.
     */
    @PUT
    @Path("/{id: \\d+}")
    @Interceptors({ServiceBrokerageInterceptor.class})
    @RequiresPermissions(value = {
            Permissions.INVENTORY_WRITE,
            Permissions.ORDER_WRITE}, logical = Logical.OR)
    public Response edit(@PathParam("id") final Long id, final ServiceBrokerage entity) {
        ServiceBrokerage updated = manager.edit(entity);
        return Response.ok(updated).build();
    }

}
