package com.endeavorms.velocity.qto;

import com.endeavorms.velocity.qto.common.AbstractResource;
import com.endeavorms.velocity.qto.common.PaginatedResult;
import com.endeavorms.velocity.qto.location.inventoryview.InventoryWorklistMeta;
import com.endeavorms.velocity.qto.service.ServiceManager;
import com.endeavorms.velocity.qto.service.inventoryview.ServiceInventoryView;
import com.endeavorms.velocity.qto.service.inventoryview.ServiceInventoryViewManager;
import com.endeavorms.velocity.qto.service.inventoryview.ServiceInventoryViewSearchCriteria;
import org.springframework.security.access.prepost.PreAuthorize;
import org.jboss.resteasy.annotations.Form;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 * @author rcasey
 * @since 2/23/2023
 */
@Path("/serviceInventoryViews")
@Consumes("application/json")
@Produces({"application/json", "application/vnd.ms-excel"})
public class ServiceInventoryViewResource extends AbstractResource<ServiceInventoryView> {

    @Override
    protected String getResourcePath() {
        return "/serviceInventoryViews";
    }

    @Inject
    private ServiceInventoryViewManager manager;

    @Inject
    private ServiceManager serviceManager;

    @GET
    @PreAuthorize("hasAuthority('inventory:read')")
    public Response getInventoryServiceViews(@Form final ServiceInventoryViewSearchCriteria criteria) {
        ServiceInventoryViewSearchCriteria crit = getExportCriteria(criteria);
        if (crit.getFields() != null && crit.getFields().contains("address")) {
            crit.setFields(crit.getFields().replace("address", "address1,address2,city,stateProvince,postalCode"));
            crit.setHeaders(crit.getHeaders().replace("Address", "Address 1,Address 2,City,State/Province,Postal Code"));
        }
        PaginatedResult<ServiceInventoryView> result = manager.findInventoryBySearchCriteria(crit);
        return toResponse(getCollectionResource(result, crit, getLocation(ServiceInventoryViewResource.class)));
    }

    @GET
    @PreAuthorize("hasAuthority('inventory:read')")
    @Path("/inventory")
    public Response getInventoryServiceViewsForLink(@Form final ServiceInventoryViewSearchCriteria criteria) {
        PaginatedResult<ServiceInventoryView> result = manager.findBySearchCriteriaForLink(criteria);
        return toResponse(getCollectionResource(result, criteria, getLocation(ServiceInventoryViewResource.class)));
    }

    @PUT
    @PreAuthorize("hasAuthority('inventory:write')")
    @Path("/inventory/link")
    public Response linkInventory(@QueryParam("incomingServiceId") final Long incomingServiceId,
                                  @QueryParam("selectedItems") final String selectedItem,
                                  @QueryParam("linkType") final String linkType) {
        serviceManager.link(incomingServiceId, selectedItem, linkType);
        return Response.ok().build();
    }

    @GET
    @PreAuthorize("hasAuthority('inventory:read')")
    @Path("/meta")
    public Response getInventoryServiceWorklistMeta(@Form final ServiceInventoryViewSearchCriteria criteria) {
        InventoryWorklistMeta meta = manager.getInventoryWorklistMeta(criteria);
        return Response.ok(meta).build();
    }

    @PUT
    @Path("/{id: \\d+}")
    @PreAuthorize("hasAuthority('inventory:write')")
    public Response edit(@PathParam("id") final Long id, final ServiceInventoryView serviceView) {
        try {
            if (!id.equals(serviceView.getId())) {
                throw new IllegalArgumentException("identifier in path does not match that of passed entity");
            }
            ServiceInventoryView updated = manager.edit(serviceView);
            return Response.ok(updated).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

     @GET
    @PreAuthorize("hasAuthority('order:write')")
    @Path("/link")
    public Response getServiceViewsForLink(@Form final ServiceInventoryViewSearchCriteria criteria) {
        PaginatedResult<ServiceInventoryView> result = manager.getServiceInventoryViewsForLink(criteria);
        return toResponse(getCollectionResource(result, criteria, getLocation(ServiceInventoryViewResource.class)));
    }

    @GET
    @PreAuthorize("hasAuthority('order:write')")
    @Path("/bundle")
    public Response getServiceViewsForBundle(@Form final ServiceInventoryViewSearchCriteria criteria) {
        PaginatedResult<ServiceInventoryView> result = manager.getServiceInventoryViewsForBundle(criteria);
        return toResponse(getCollectionResource(result, criteria, getLocation(ServiceInventoryViewResource.class)));
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

    @GET
    @PreAuthorize("hasAuthority('inventory:read')")
    @Path("/serviceTypes")
    public Response getServiceTypes() {
        List<String> serviceType = manager.findServiceTypes();
        return Response.ok(serviceType).build();
    }

}
