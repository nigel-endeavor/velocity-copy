package com.endeavorms.velocity.qto;

import com.endeavorms.velocity.qto.common.AbstractResource;
import com.endeavorms.velocity.qto.common.PaginatedResult;
import com.endeavorms.velocity.qto.location.view.LocationView;
import com.endeavorms.velocity.qto.location.view.LocationViewManager;
import com.endeavorms.velocity.qto.location.view.LocationViewSearchCriteria;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @Override
    protected String getResourcePath() {
        return "/locationViews";
    }

    /** Business methods associated with LocationViews. */
    @Inject
    private LocationViewManager manager;

    @GET
    @PreAuthorize("hasAnyAuthority('inventory:read','order:read')")
    public Response getLocationViews(@Form final LocationViewSearchCriteria criteria) {
        LocationViewSearchCriteria crit = getExportCriteria(criteria);
        if (crit.getFields() != null && crit.getFields().contains("address")) {
            crit.setFields(crit.getFields().replace("address", "address1,address2,city,stateProvince,postalCode"));
            crit.setHeaders(crit.getHeaders().replace("Address", "Address 1,Address 2,City,State/Province,Postal Code"));
        }
        PaginatedResult<LocationView> result = manager.findBySearchCriteria(crit);
        return toResponse(getCollectionResource(result, crit, getLocation(LocationViewResource.class)));
    }

    @GET
    @PreAuthorize("hasAuthority('order:write')")
    @Path("/relocate")
    public Response getLocationViewsForRelocate(@Form LocationViewSearchCriteria criteria) {
        PaginatedResult<LocationView> result = manager.findBySearchCriteriaForServiceRelocate(criteria);
        return toResponse(getCollectionResource(result, criteria, getLocation(LocationViewResource.class)));
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

    @PUT
    @Path("/{id: \\d+}")
    @PreAuthorize("hasAnyAuthority('inventory:write','order:write')")
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
