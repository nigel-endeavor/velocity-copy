package com.vertek.corporate.qto;

import com.vertek.corporate.qto.authentication.Permissions;
import com.vertek.corporate.qto.common.AbstractResource;
import com.vertek.corporate.qto.order.Order;
import com.vertek.corporate.qto.order.OrderManager;
import com.vertek.corporate.qto.order.dto.OrderCreateDtoWrapper;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

/**
 * @author rcasey
 * @since 1/10/2023
 */
@Path("/orders")
@Consumes("application/json")
@Produces("application/json")
public class OrderResource extends AbstractResource<Order> {

    /** Business methods for Orders.*/
    @Inject
    private OrderManager manager;

    @GET
    @Path("/{id: \\d+}")
    @RequiresPermissions(value = {
            Permissions.INVENTORY_READ,
            Permissions.ORDER_READ}, logical = Logical.OR)
    public Response retrieve(@PathParam("id") final Long id) {
        try {
            Order retrieved = manager.retrieve(id);
            return Response.ok(retrieved).build();

        } catch (Exception e) {
            String errorMessage = "Access denied: contact a platform admin to view this client group.";
            LOGGER.error(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(errorMessage)
                    .build();
        }
    }

    @POST
    @RequiresPermissions(Permissions.ORDER_CREATE)
    public Response create(final OrderCreateDtoWrapper dtoWrapper) {
        try {
            OrderCreateDtoWrapper created = manager.createFromDto(dtoWrapper);
            return Response.ok(created).build();
        } catch (BadRequestException e) {
            // Handle BadRequestException with simple message directly
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id: \\d+}")
    @RequiresPermissions(value = {
            Permissions.INVENTORY_WRITE,
            Permissions.ORDER_WRITE}, logical = Logical.OR)
    public Response edit(@PathParam("id") final Long id, final Order order) {
        try {
            if (!id.equals(order.getId())) {
                throw new IllegalArgumentException("identifier in path does not match that of passed entity");
            }
            Order updated = manager.edit(order);
            return Response.ok(updated).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }
}
