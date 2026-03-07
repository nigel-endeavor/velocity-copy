package com.vertek.corporate.qto;

import com.vertek.corporate.qto.authentication.Permissions;
import com.vertek.corporate.qto.common.AbstractResource;
import com.vertek.corporate.qto.common.PaginatedResult;
import com.vertek.corporate.qto.location.inventoryview.InventoryWorklistMeta;
import com.vertek.corporate.qto.location.inventoryview.LocationInventoryView;
import com.vertek.corporate.qto.location.inventoryview.LocationInventoryViewManager;
import com.vertek.corporate.qto.location.inventoryview.LocationInventoryViewSearchCriteria;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.jboss.resteasy.annotations.Form;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 * @author rcasey
 * @since 1/9/2023
 */
@Path("/locationInventoryViews")
@Consumes("application/json")
@Produces({"application/json", "application/vnd.ms-excel"})
public class LocationInventoryViewResource extends AbstractResource<LocationInventoryView> {

    /** Private logger for this class. */
    private static final Logger LOGGER = LoggerFactory.getLogger(LocationInventoryViewResource.class);

    /** Business methods associated with LocationInventoryViews. */
    @Inject
    private LocationInventoryViewManager manager;

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

    @GET
    @RequiresPermissions(Permissions.INVENTORY_READ)
    public Response getInventoryLocationViews(@Form final LocationInventoryViewSearchCriteria criteria) {
        Long start = System.currentTimeMillis();
        LOGGER.debug("getInventoryLocationViews called");
        LocationInventoryViewSearchCriteria crit = getExportCriteria(criteria);
        if (crit.getFields() != null && crit.getFields().contains("address")) {
            crit.setFields(crit.getFields().replace("address", "address1,address2,city,stateProvince,postalCode"));
            crit.setHeaders(crit.getHeaders().replace("Address", "Address 1,Address 2,City,State/Province,Postal Code"));
        }
        PaginatedResult<LocationInventoryView> result = manager.findInventoryBySearchCriteria(crit);
        LOGGER.debug("getInventoryLocationViews took " + (System.currentTimeMillis() - start) + "ms");
        return getCollectionResource(result, crit, getLocation(LocationInventoryViewResource.class));
    }

    @GET
    @RequiresPermissions(Permissions.INVENTORY_READ)
    @Path("/link")
    public Response getInventoryLocationViewsForLink(@Form final LocationInventoryViewSearchCriteria criteria) {
        PaginatedResult<LocationInventoryView> result = manager.findBySearchCriteriaForLink(criteria);
        return getCollectionResource(result, criteria, getLocation(LocationInventoryViewResource.class));
    }

    @GET
    @RequiresPermissions(Permissions.INVENTORY_READ)
    @Path("/meta")
    public Response getInventoryLocationWorklistMeta(@Form final LocationInventoryViewSearchCriteria criteria) {
        InventoryWorklistMeta meta = manager.getInventoryWorklistMeta(criteria);
        return Response.ok(meta).build();
    }

    @PUT
    @Path("/{id: \\d+}")
    @RequiresPermissions(Permissions.INVENTORY_WRITE)
    public Response edit(@PathParam("id") final Long id, final LocationInventoryView locationView) {
        try {
            if (!id.equals(locationView.getId())) {
                throw new IllegalArgumentException("identifier in path does not match that of passed entity");
            }
            LocationInventoryView updated = manager.edit(locationView);
            return Response.ok(updated).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    /**
     * Retrieves location views for the inventory service relocation.
     * @param criteria the criteria to filter by.
     * @return matching location views.
     */
    @GET
    @RequiresPermissions(Permissions.INVENTORY_WRITE)
    @Path("/relocate")
    public Response getLocationViewsForRelocate(@Form LocationInventoryViewSearchCriteria criteria) {
        PaginatedResult<LocationInventoryView> result = manager.findBySearchCriteriaForServiceRelocate(criteria);
        return getCollectionResource(result, criteria, getLocation(LocationInventoryViewResource.class));
    }
}
