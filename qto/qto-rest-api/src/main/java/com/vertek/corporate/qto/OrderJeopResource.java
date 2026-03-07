package com.vertek.corporate.qto;

import com.vertek.corporate.qto.authentication.Permissions;
import com.vertek.corporate.qto.common.AbstractResource;
import com.vertek.corporate.qto.common.PaginatedResult;
import com.vertek.corporate.qto.jeop.JeopUnionView;
import com.vertek.corporate.qto.jeop.JeopUnionViewSearchCriteria;
import com.vertek.corporate.qto.jeop.OrderJeop;
import com.vertek.corporate.qto.jeop.OrderJeopManager;
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
@Path("/orderJeops")
@Consumes("application/json")
@Produces("application/json")
public class OrderJeopResource extends AbstractResource<JeopUnionView> {

    /** Business methods for Orders. */
    @Inject
    private OrderJeopManager manager;

    /**
     * Retrieves all OrderJeops matching the given criteria.
     * @param criteria the criteria to filter by.
     * @return matching orderJeops.
     */
    @GET
    @RequiresPermissions(value = {
            Permissions.INVENTORY_READ,
            Permissions.ORDER_READ}, logical = Logical.OR)
    public Response getOrderJeops(@Form final JeopUnionViewSearchCriteria criteria) {
        PaginatedResult<JeopUnionView> result = manager.findBySearchCriteria(criteria);
        return getCollectionResource(result, criteria, getLocation(OrderJeopResource.class));
    }

    @POST
    @RequiresPermissions(value = {
            Permissions.INVENTORY_WRITE,
            Permissions.ORDER_WRITE}, logical = Logical.OR)
    public Response create(final OrderJeop jeop) {
        try {
            OrderJeop returnedJeop;
            if (jeop.getOrderId() == null) {
                throw new IllegalArgumentException("Missing orderId.");
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
    public Response edit(@PathParam("id") final Long id, final OrderJeop jeop) {
        try {
            OrderJeop returnedJeop;
            if (!id.equals(jeop.getId())) {
                throw new IllegalArgumentException("identifier in path does not match that of passed entity");
            } else if (jeop.getOrderId() == null) {
                throw new IllegalArgumentException("Missing orderId.");
            } else {
                returnedJeop = manager.edit(jeop);
            }
            return Response.ok(returnedJeop).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }
}
