package com.vertek.corporate.qto;

import com.vertek.corporate.qto.authentication.Permissions;
import com.vertek.corporate.qto.common.AbstractResource;
import com.vertek.corporate.qto.common.PaginatedResult;
import com.vertek.corporate.qto.service.ServiceManager;
import com.vertek.corporate.qto.service.view.ServiceView;
import com.vertek.corporate.qto.service.view.ServiceViewManager;
import com.vertek.corporate.qto.service.view.ServiceViewSearchCriteria;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.jboss.resteasy.annotations.Form;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * @author rcasey
 * @since 2/23/2023
 */
@Path("/serviceViews")
@Consumes("application/json")
@Produces({"application/json", "application/vnd.ms-excel"})
public class ServiceViewResource extends AbstractResource<ServiceView> {

    @Inject
    private ServiceViewManager manager;

    @Inject
    private ServiceManager serviceManager;

    @GET
    @RequiresPermissions(Permissions.ORDER_READ)
    public Response getServiceViews(@Form final ServiceViewSearchCriteria criteria) {
        ServiceViewSearchCriteria crit = getExportCriteria(criteria);
        if (crit.getFields() != null && crit.getFields().contains("address")) {
            crit.setFields(crit.getFields().replace("address", "address1,address2,city,stateProvince,postalCode"));
            crit.setHeaders(crit.getHeaders().replace("Address", "Address 1,Address 2,City,State/Province,Postal Code"));
        }
        PaginatedResult<ServiceView> result = manager.findBySearchCriteria(crit);
        return getCollectionResource(result, crit, getLocation(ServiceViewResource.class));
    }

    @PUT
    @Path("/{id: \\d+}")
    @RequiresPermissions(Permissions.ORDER_WRITE)
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
    @RequiresPermissions(Permissions.ORDER_WRITE)
    @Path("/link")
    public Response getServiceViewsForLink(@Form final ServiceViewSearchCriteria criteria) {
        PaginatedResult<ServiceView> result = manager.getServiceViewsForLink(criteria);
        return getCollectionResource(result, criteria, getLocation(ServiceViewResource.class));
    }

    @GET
    @RequiresPermissions(Permissions.ORDER_WRITE)
    @Path("/bundle")
    public Response getServiceViewsForBundle(@Form final ServiceViewSearchCriteria criteria) {
        PaginatedResult<ServiceView> result = manager.getServiceViewsForBundle(criteria);
        return getCollectionResource(result, criteria, getLocation(ServiceViewResource.class));
    }

    @PUT
    @RequiresPermissions(Permissions.ORDER_WRITE)
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
    @RequiresPermissions(Permissions.INVENTORY_READ)
    @Path("/serviceTypes")
    public Response getServiceTypes() {
        List<String> serviceType = manager.findServiceTypes();
        return Response.ok(serviceType).build();
    }

}
