package com.vertek.corporate.qto;

import com.vertek.corporate.qto.authentication.Permissions;
import com.vertek.corporate.qto.common.AbstractResource;
import com.vertek.corporate.qto.message.Message;
import com.vertek.corporate.qto.message.MessageThread;
import com.vertek.corporate.qto.message.MessageThreadManager;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
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
