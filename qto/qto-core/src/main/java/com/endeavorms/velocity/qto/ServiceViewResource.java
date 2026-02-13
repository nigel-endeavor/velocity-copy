package com.endeavorms.velocity.qto;

import com.endeavorms.velocity.qto.common.AbstractResource;
import com.endeavorms.velocity.qto.common.PaginatedResult;
import com.endeavorms.velocity.qto.service.ServiceManager;
import com.endeavorms.velocity.qto.service.view.ServiceView;
import com.endeavorms.velocity.qto.service.view.ServiceViewManager;
import com.endeavorms.velocity.qto.service.view.ServiceViewSearchCriteria;
import org.springframework.security.access.prepost.PreAuthorize;
import org.jboss.resteasy.annotations.Form;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 * @author rcasey
 * @since 2/23/2023
 */
@Path("/serviceViews")
@Consumes("application/json")
@Produces({"application/json", "application/vnd.ms-excel"})
public class ServiceViewResource extends AbstractResource<ServiceView> {

    @Override
    protected String getResourcePath() {
        return "/serviceViews";
    }

    @Inject
    private ServiceViewManager manager;

    @Inject
    private ServiceManager serviceManager;

    @GET
    @PreAuthorize("hasAuthority('order:read')")
    public Response getServiceViews(@Form final ServiceViewSearchCriteria criteria) {
        ServiceViewSearchCriteria crit = getExportCriteria(criteria);
        if (crit.getFields() != null && crit.getFields().contains("address")) {
            crit.setFields(crit.getFields().replace("address", "address1,address2,city,stateProvince,postalCode"));
            crit.setHeaders(crit.getHeaders().replace("Address", "Address 1,Address 2,City,State/Province,Postal Code"));
        }
        PaginatedResult<ServiceView> result = manager.findBySearchCriteria(crit);
        return toResponse(getCollectionResource(result, crit, getLocation(ServiceViewResource.class)));
    }

    @PUT
    @Path("/{id: \\d+}")
    @PreAuthorize("hasAuthority('order:write')")
    public Response edit(@PathParam("id") final Long id, final ServiceView serviceView) {
        try {
            if (!id.equals(serviceView.getId())) {
                throw new IllegalArgumentException("identifier in path does not match that of passed entity");
            }
            ServiceView updated = manager.edit(serviceView);
            return Response.ok(updated).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @GET
    @PreAuthorize("hasAuthority('order:write')")
    @Path("/link")
    public Response getServiceViewsForLink(@Form final ServiceViewSearchCriteria criteria) {
        PaginatedResult<ServiceView> result = manager.getServiceViewsForLink(criteria);
        return toResponse(getCollectionResource(result, criteria, getLocation(ServiceViewResource.class)));
    }

    @GET
    @PreAuthorize("hasAuthority('order:write')")
    @Path("/bundle")
    public Response getServiceViewsForBundle(@Form final ServiceViewSearchCriteria criteria) {
        PaginatedResult<ServiceView> result = manager.getServiceViewsForBundle(criteria);
        return toResponse(getCollectionResource(result, criteria, getLocation(ServiceViewResource.class)));
    }

    @PUT
    @PreAuthorize("hasAuthority('order:write')")
    @Path("/link")
    public Response link(@QueryParam("incomingServiceId") final Long incomingServiceId,
                         @QueryParam("selectedItems") final String selectedItems,
                         @QueryParam("linkType") final String linkType) {
        serviceManager.link(incomingServiceId, selectedItems, linkType);
        return Response.ok().build();
    }

    /**
     * Gets the service types for the worklist based on active services.
     * @return The service types for the worklist.
     */
    @GET
    @PreAuthorize("hasAuthority('inventory:read')")
    @Path("/serviceTypes")
    public Response getServiceTypes() {
        List<String> serviceType = manager.findServiceTypes();
        return Response.ok(serviceType).build();
    }

}
