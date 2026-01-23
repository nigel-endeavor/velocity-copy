package com.vertek.corporate.qto.dispute;

import com.vertek.corporate.qto.authentication.Permissions;
import com.vertek.corporate.qto.common.AbstractResource;
import com.vertek.corporate.qto.common.PaginatedResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.jboss.resteasy.annotations.Form;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * @author fcurran
 * @since 9/18/2023
 */
@Path("/disputeViews")
@Consumes("application/json")
@Produces({"application/json", "application/vnd.ms-excel"})
public class DisputeViewResource extends AbstractResource<DisputeView> {
    /** Business methods associated with DisputeViews. */
    @Inject
    private DisputeViewManager manager;

    /**
     * Get a list of DisputeViews.
     * @param criteria The search criteria.
     * @return A list of DisputeViews.
     */
    @GET
    public Response getDisputeViews(@Form final DisputeViewSearchCriteria criteria) {
        DisputeViewSearchCriteria crit = getExportCriteria(criteria);
        if (crit.getFields() != null && crit.getFields().contains("address")) {
            crit.setFields(crit.getFields().replace("address", "address1,address2,city,stateProvince,postalCode"));
            crit.setHeaders(crit.getHeaders().replace("Address", "Address 1,Address 2,City,State/Province,Postal Code"));
        }
        PaginatedResult<DisputeView> result = manager.findBySearchCriteria(crit);
        return getCollectionResource(result, crit, getLocation(DisputeViewResource.class));
    }

    /**
     * Gets the meta data for the worklist built from the provided search criteria.
     * @param criteria The search criteria.
     * @return The meta data for the worklist.
     */
    @GET
    @Path("/meta")
    public Response getDisputeWorklistMeta(@Form final DisputeViewSearchCriteria criteria) {
        DisputeWorklistMeta meta = manager.getDisputeWorklistMeta(criteria);
        return Response.ok(meta).build();
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
