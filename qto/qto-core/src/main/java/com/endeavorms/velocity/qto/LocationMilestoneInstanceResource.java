package com.endeavorms.velocity.qto;

import com.endeavorms.velocity.qto.common.AbstractResource;
import com.endeavorms.velocity.qto.interceptors.LocationMilestoneInterceptor;
import com.endeavorms.velocity.qto.milestone.LocationMilestoneInstance;
import com.endeavorms.velocity.qto.milestone.LocationMilestoneInstanceManager;
import org.springframework.security.access.prepost.PreAuthorize;

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
 * @since 2/16/2023
 */
@Path("/locationMilestoneInstances")
@Consumes("application/json")
@Produces("application/json")
public class LocationMilestoneInstanceResource extends AbstractResource<LocationMilestoneInstance> {

    @Override
    protected String getResourcePath() {
        return "/locationMilestoneInstances";
    }

    @Inject
    private LocationMilestoneInstanceManager manager;

    @GET
    @PreAuthorize("hasAnyAuthority('inventory:read','order:read')")
    public Response getMilestoneInstances(@QueryParam("locationId") final Long locationId) {
        List<LocationMilestoneInstance> milestoneInstances = manager.listByRecord(locationId);
        return Response.ok(milestoneInstances).build();
    }

    @POST
    @Interceptors({LocationMilestoneInterceptor.class})
    @PreAuthorize("hasAnyAuthority('inventory:write','order:write')")
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
    @PreAuthorize("hasAnyAuthority('inventory:write','order:write')")
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
