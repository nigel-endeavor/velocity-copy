package com.vertek.corporate.qto;

import com.vertek.corporate.qto.authentication.Permissions;
import com.vertek.corporate.qto.common.AbstractResource;
import com.vertek.corporate.qto.message.Message;
import com.vertek.corporate.qto.message.MessageThread;
import com.vertek.corporate.qto.message.MessageThreadManager;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 * @author rcasey
 * @since 4/26/2023
 */
@Path("/messageThreads")
@Consumes("application/json")
@Produces("application/json")
@RequiresPermissions(value = {
        Permissions.INVENTORY_READ,
        Permissions.ORDER_READ}, logical = Logical.OR)
public class MessageThreadResource extends AbstractResource<MessageThread> {

    /** Business methods for MessageThreads. */
    @Inject
    private MessageThreadManager manager;

    @GET
    public Response getMessageThreads(@QueryParam("locationId") final Long locationId) {
        List<MessageThread> messageThreads = manager.findByLocationId(locationId);
        return Response.ok(messageThreads).build();
    }

    @POST
    public Response create(final MessageThread messageThread) {
        try {
            MessageThread created = manager.create(messageThread);
            return Response.ok(created).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id: \\d+}")
    public Response createMessage(@PathParam("id") final Long messageThreadId, final Message message) {
        try {
            MessageThread updated = manager.createMessage(messageThreadId, message);
            return Response.ok(updated).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id: \\d+}/subjects")
    public Response setSubjects(@PathParam("id") final Long messageThreadId, final List<Integer> subjectIds) {
        try {
            MessageThread updated = manager.setSubjects(messageThreadId, subjectIds);
            return Response.ok(updated).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }
}
