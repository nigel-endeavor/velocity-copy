package com.vertek.corporate.qto;

import com.vertek.corporate.qto.authentication.Permissions;
import com.vertek.corporate.qto.common.AbstractResource;
import com.vertek.corporate.qto.common.PaginatedResult;
import com.vertek.corporate.qto.location.inventoryview.InventoryWorklistMeta;
import com.vertek.corporate.qto.service.ServiceManager;
import com.vertek.corporate.qto.service.inventoryview.ServiceInventoryView;
import com.vertek.corporate.qto.service.inventoryview.ServiceInventoryViewManager;
import com.vertek.corporate.qto.service.inventoryview.ServiceInventoryViewSearchCriteria;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.jboss.resteasy.annotations.Form;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * @author rcasey
 * @since 2/23/2023
 */
@Path("/serviceInventoryViews")
@Consumes("application/json")
@Produces({"application/json", "application/vnd.ms-excel"})
public class ServiceInventoryViewResource extends AbstractResource<ServiceInventoryView> {

    @Inject
    private ServiceInventoryViewManager manager;

    @Inject
    private ServiceManager serviceManager;

    @GET
    @RequiresPermissions(Permissions.INVENTORY_READ)
    public Response getInventoryServiceViews(@Form final ServiceInventoryViewSearchCriteria criteria) {
        ServiceInventoryViewSearchCriteria crit = getExportCriteria(criteria);
        if (crit.getFields() != null && crit.getFields().contains("address")) {
            crit.setFields(crit.getFields().replace("address", "address1,address2,city,stateProvince,postalCode"));
            crit.setHeaders(crit.getHeaders().replace("Address", "Address 1,Address 2,City,State/Province,Postal Code"));
        }
        PaginatedResult<ServiceInventoryView> result = manager.findInventoryBySearchCriteria(crit);
        return getCollectionResource(result, crit, getLocation(ServiceInventoryViewResource.class));
    }

    @GET
    @RequiresPermissions(Permissions.INVENTORY_READ)
    @Path("/inventory")
    public Response getInventoryServiceViewsForLink(@Form final ServiceInventoryViewSearchCriteria criteria) {
        PaginatedResult<ServiceInventoryView> result = manager.findBySearchCriteriaForLink(criteria);
        return getCollectionResource(result, criteria, getLocation(ServiceInventoryViewResource.class));
    }

    @PUT
    @RequiresPermissions(Permissions.INVENTORY_WRITE)
    @Path("/inventory/link")
    public Response linkInventory(@QueryParam("incomingServiceId") final Long incomingServiceId,
                                  @QueryParam("selectedItems") final String selectedItem,
                                  @QueryParam("linkType") final String linkType) {
        serviceManager.link(incomingServiceId, selectedItem, linkType);
        return Response.ok().build();
    }

    @GET
    @RequiresPermissions(Permissions.INVENTORY_READ)
    @Path("/meta")
    public Response getInventoryServiceWorklistMeta(@Form final ServiceInventoryViewSearchCriteria criteria) {
        InventoryWorklistMeta meta = manager.getInventoryWorklistMeta(criteria);
        return Response.ok(meta).build();
    }

    @PUT
    @Path("/{id: \\d+}")
    @RequiresPermissions(Permissions.INVENTORY_WRITE)
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
    @RequiresPermissions(Permissions.ORDER_WRITE)
    @Path("/link")
    public Response getServiceViewsForLink(@Form final ServiceInventoryViewSearchCriteria criteria) {
        PaginatedResult<ServiceInventoryView> result = manager.getServiceInventoryViewsForLink(criteria);
        return getCollectionResource(result, criteria, getLocation(ServiceInventoryViewResource.class));
    }

    @GET
    @RequiresPermissions(Permissions.ORDER_WRITE)
    @Path("/bundle")
    public Response getServiceViewsForBundle(@Form final ServiceInventoryViewSearchCriteria criteria) {
        PaginatedResult<ServiceInventoryView> result = manager.getServiceInventoryViewsForBundle(criteria);
        return getCollectionResource(result, criteria, getLocation(ServiceInventoryViewResource.class));
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

    @GET
    @RequiresPermissions(Permissions.INVENTORY_READ)
    @Path("/serviceTypes")
    public Response getServiceTypes() {
        List<String> serviceType = manager.findServiceTypes();
        return Response.ok(serviceType).build();
    }

}
