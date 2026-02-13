package com.endeavorms.velocity.qto;

import com.endeavorms.velocity.qto.common.AbstractResource;
import com.endeavorms.velocity.qto.service.historyview.ServiceHistoryView;
import com.endeavorms.velocity.qto.service.historyview.ServiceHistoryViewManager;
import org.springframework.security.access.prepost.PreAuthorize;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

/**
 * @author rcasey
 * @since 10/27/2023
 */
@Path("/serviceHistory")
@Consumes("application/json")
@Produces("application/json")
@PreAuthorize("hasAnyAuthority('inventory:write','order:write')")
public class ServiceHistoryViewResource extends AbstractResource<ServiceHistoryView> {

    @Override
    protected String getResourcePath() {
        return "/serviceHistory";
    }

    @Inject
    private ServiceHistoryViewManager manager;

    @GET
    public Response getServiceHistory(@QueryParam("serviceId") final Long serviceId,
                                      @QueryParam("sortDir") final String sortDir,
                                      @QueryParam("sortField") final String sortField) {
        try {
            return Response.ok(manager.getServiceTreePaginatedResult(serviceId, sortDir, sortField)).build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }
}
