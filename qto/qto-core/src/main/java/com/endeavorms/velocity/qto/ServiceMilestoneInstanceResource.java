package com.endeavorms.velocity.qto;

import com.endeavorms.velocity.qto.common.AbstractResource;
import com.endeavorms.velocity.qto.interceptors.ServiceMilestoneInterceptor;
import com.endeavorms.velocity.qto.milestone.ServiceMilestoneInstance;
import com.endeavorms.velocity.qto.milestone.ServiceMilestoneInstanceManager;
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
 * @since 2/20/2023
 */
@Path("/serviceMilestoneInstances")
@Consumes("application/json")
@Produces("application/json")
public class ServiceMilestoneInstanceResource extends AbstractResource<ServiceMilestoneInstance> {

    @Override
    protected String getResourcePath() {
        return "/serviceMilestoneInstances";
    }

    @Inject
    private ServiceMilestoneInstanceManager manager;

    @GET
    @PreAuthorize("hasAnyAuthority('inventory:read','order:read')")
    public Response getMilestoneInstances(@QueryParam("serviceId") final Long serviceId) {
        List<ServiceMilestoneInstance> milestoneInstances = manager.listByRecord(serviceId);
        return Response.ok(milestoneInstances).build();
    }

    @POST
    @Interceptors({ServiceMilestoneInterceptor.class})
    @PreAuthorize("hasAnyAuthority('inventory:write','order:write')")
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
    @PreAuthorize("hasAnyAuthority('inventory:write','order:write')")
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
