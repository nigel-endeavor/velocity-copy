package com.endeavorms.velocity.qto.equipment;

import com.endeavorms.velocity.qto.common.AbstractResource;
import com.endeavorms.velocity.qto.common.PaginatedResult;
import com.endeavorms.velocity.qto.common.PreconditionsUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.jboss.resteasy.annotations.Form;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

@Path("/serviceEquipment")
@Consumes("application/json")
@Produces("application/json")
public class ServiceEquipmentResource extends AbstractResource<ServiceEquipment> {

    @Override
    protected String getResourcePath() {
        return "/serviceEquipment";
    }

    /** Business methods for services. */
    @Inject
    private ServiceEquipmentManager manager;

    /**
     * Retrieves all ServiceEquipment matching the given criteria.
     * @param criteria the criteria to filter by.
     * @return matching serviceEquipment.
     */
    @GET
    @PreAuthorize("hasAnyAuthority('inventory:read','order:read')")
    public Response getServiceEquipment(@Form final ServiceEquipmentSearchCriteria criteria) {
        PaginatedResult<ServiceEquipment> result = manager.findBySearchCriteria(criteria);
        return toResponse(getCollectionResource(result, criteria, getLocation(ServiceEquipmentResource.class)));
    }

    @POST
    @PreAuthorize("hasAnyAuthority('inventory:write','order:write')")
    public Response create(final ServiceEquipment equipment) {
        manager.create(equipment);
        return Response.ok().build();
    }

    @GET
    @Path("/{id}")
    @PreAuthorize("hasAnyAuthority('inventory:read','order:read')")
    public Response getServiceEquipment(@PathParam("id") final Long id) {
        return Response.ok(manager.retrieve(id)).build();
    }

    @PUT
    @Path("/{id}")
    @PreAuthorize("hasAnyAuthority('inventory:write','order:write')")
    public Response update(@PathParam("id") final Long id, final ServiceEquipment equipment) {
        PreconditionsUtil.checkArgument(id, "Customer Task ID is required");
        manager.edit(equipment);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    @PreAuthorize("hasAnyAuthority('inventory:write','order:write')")
    public Response delete(@PathParam("id") final Long id) {
        PreconditionsUtil.checkArgument(id, "Customer Task ID is required");
        manager.remove(id);
        return Response.ok().build();
    }
}
