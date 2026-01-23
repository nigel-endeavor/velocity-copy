package com.vertek.corporate.qto.equipment;

import com.vertek.corporate.qto.authentication.Permissions;
import com.vertek.corporate.qto.common.AbstractResource;
import com.vertek.corporate.qto.common.PaginatedResult;
import com.vertek.corporate.qto.common.PreconditionsUtil;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.jboss.resteasy.annotations.Form;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/serviceEquipment")
@Consumes("application/json")
@Produces("application/json")
public class ServiceEquipmentResource extends AbstractResource<ServiceEquipment> {
    /** Business methods for services. */
    @Inject
    private ServiceEquipmentManager manager;

    /**
     * Retrieves all ServiceEquipment matching the given criteria.
     * @param criteria the criteria to filter by.
     * @return matching serviceEquipment.
     */
    @GET
    @RequiresPermissions(value = {
            Permissions.INVENTORY_READ,
            Permissions.ORDER_READ}, logical = Logical.OR)
    public Response getServiceEquipment(@Form final ServiceEquipmentSearchCriteria criteria) {
        PaginatedResult<ServiceEquipment> result = manager.findBySearchCriteria(criteria);
        return getCollectionResource(result, criteria, getLocation(ServiceEquipmentResource.class));
    }

    @POST
    @RequiresPermissions(value = {
            Permissions.INVENTORY_WRITE,
            Permissions.ORDER_WRITE}, logical = Logical.OR)
    public Response create(final ServiceEquipment equipment) {
        manager.create(equipment);
        return Response.ok().build();
    }

    @GET
    @Path("/{id}")
    @RequiresPermissions(value = {
            Permissions.INVENTORY_READ,
            Permissions.ORDER_READ}, logical = Logical.OR)
    public Response getServiceEquipment(@PathParam("id") final Long id) {
        return Response.ok(manager.retrieve(id)).build();
    }

    @PUT
    @Path("/{id}")
    @RequiresPermissions(value = {
            Permissions.INVENTORY_WRITE,
            Permissions.ORDER_WRITE}, logical = Logical.OR)
    public Response update(@PathParam("id") final Long id, final ServiceEquipment equipment) {
        PreconditionsUtil.checkArgument(id, "Customer Task ID is required");
        manager.edit(equipment);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    @RequiresPermissions(value = {
            Permissions.INVENTORY_WRITE,
            Permissions.ORDER_WRITE}, logical = Logical.OR)
    public Response delete(@PathParam("id") final Long id) {
        PreconditionsUtil.checkArgument(id, "Customer Task ID is required");
        manager.remove(id);
        return Response.ok().build();
    }
}
