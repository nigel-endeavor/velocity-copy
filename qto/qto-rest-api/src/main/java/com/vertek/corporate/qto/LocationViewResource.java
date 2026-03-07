package com.vertek.corporate.qto;

import com.vertek.corporate.qto.authentication.Permissions;
import com.vertek.corporate.qto.common.AbstractResource;
import com.vertek.corporate.qto.common.PaginatedResult;
import com.vertek.corporate.qto.location.view.LocationView;
import com.vertek.corporate.qto.location.view.LocationViewManager;
import com.vertek.corporate.qto.location.view.LocationViewSearchCriteria;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.jboss.resteasy.annotations.Form;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 * @author rcasey
 * @since 1/9/2023
 */
@Path("/locationViews")
@Consumes("application/json")
@Produces({"application/json", "application/vnd.ms-excel"})
public class LocationViewResource extends AbstractResource<LocationView> {

    /** Business methods associated with LocationViews. */
    @Inject
    private LocationViewManager manager;

    @GET
    @RequiresPermissions(value = {
            Permissions.INVENTORY_READ,
            Permissions.ORDER_READ}, logical = Logical.OR)
    public Response getLocationViews(@Form final LocationViewSearchCriteria criteria) {
        LocationViewSearchCriteria crit = getExportCriteria(criteria);
        if (crit.getFields() != null && crit.getFields().contains("address")) {
            crit.setFields(crit.getFields().replace("address", "address1,address2,city,stateProvince,postalCode"));
            crit.setHeaders(crit.getHeaders().replace("Address", "Address 1,Address 2,City,State/Province,Postal Code"));
        }
        PaginatedResult<LocationView> result = manager.findBySearchCriteria(crit);
        return getCollectionResource(result, crit, getLocation(LocationViewResource.class));
    }

    @GET
    @RequiresPermissions(Permissions.ORDER_WRITE)
    @Path("/relocate")
    public Response getLocationViewsForRelocate(@Form LocationViewSearchCriteria criteria) {
        PaginatedResult<LocationView> result = manager.findBySearchCriteriaForServiceRelocate(criteria);
        return getCollectionResource(result, criteria, getLocation(LocationViewResource.class));
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

    @PUT
    @Path("/{id: \\d+}")
    @RequiresPermissions(value = {
            Permissions.INVENTORY_WRITE,
            Permissions.ORDER_WRITE}, logical = Logical.OR)
    public Response edit(@PathParam("id") final Long id, final LocationView locationView) {
        try {
            if (!id.equals(locationView.getId())) {
                throw new IllegalArgumentException("identifier in path does not match that of passed entity");
            }
            LocationView updated = manager.edit(locationView);
            return Response.ok(updated).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }
}
