package com.vertek.corporate.qto;

import com.vertek.corporate.qto.authentication.Permissions;
import com.vertek.corporate.qto.common.AbstractResource;
import com.vertek.corporate.qto.common.PaginatedResult;
import com.vertek.corporate.qto.jeop.JeopUnionView;
import com.vertek.corporate.qto.jeop.JeopUnionViewSearchCriteria;
import com.vertek.corporate.qto.jeop.ServiceJeop;
import com.vertek.corporate.qto.jeop.ServiceJeopManager;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.jboss.resteasy.annotations.Form;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

/**
 * @author llevit
 */
@Path("/serviceJeops")
@Consumes("application/json")
@Produces("application/json")
public class ServiceJeopResource extends AbstractResource<JeopUnionView> {

    /** Business methods for Services. */
    @Inject
    private ServiceJeopManager manager;

    /**
     * Retrieves all ServiceJeops matching the given criteria.
     * @param criteria the criteria to filter by.
     * @return matching serviceJeops.
     */
    @GET
    @RequiresPermissions(value = {
            Permissions.INVENTORY_READ,
            Permissions.ORDER_READ}, logical = Logical.OR)
    public Response getServiceJeops(@Form final JeopUnionViewSearchCriteria criteria) {
        PaginatedResult<JeopUnionView> result = manager.findBySearchCriteria(criteria);
        return getCollectionResource(result, criteria, getLocation(ServiceJeopResource.class));
    }

    @POST
    @RequiresPermissions(value = {
            Permissions.INVENTORY_WRITE,
            Permissions.ORDER_WRITE}, logical = Logical.OR)
    public Response create(final ServiceJeop jeop) {
        try {
            ServiceJeop returnedJeop;
            if (jeop.getServiceId() == null) {
                throw new IllegalArgumentException("Missing serviceId.");
            } else {
                returnedJeop = manager.create(jeop);
            }
            return Response.ok(returnedJeop).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id: \\d+}")
    @RequiresPermissions(value = {
            Permissions.INVENTORY_WRITE,
            Permissions.ORDER_WRITE}, logical = Logical.OR)
    public Response edit(@PathParam("id") final Long id, final ServiceJeop jeop) {
        try {
            ServiceJeop returnedJeop;
            if (!id.equals(jeop.getId())) {
                throw new IllegalArgumentException("identifier in path does not match that of passed entity");
            } else if (jeop.getServiceId() == null) {
                throw new IllegalArgumentException("Missing serviceId.");
            } else {
                returnedJeop = manager.edit(jeop);
            }
            return Response.ok(returnedJeop).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }
}
