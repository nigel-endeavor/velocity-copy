package com.vertek.corporate.qto;

import com.vertek.corporate.qto.authentication.Permissions;
import com.vertek.corporate.qto.common.AbstractResource;
import com.vertek.corporate.qto.notification.Notification;
import com.vertek.corporate.qto.notification.NotificationManager;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

/**
 * @author rcasey
 * @since 6/16/2023
 */
@Path("/notifications")
@Consumes("application/json")
@Produces("application/json")
public class NotificationResource extends AbstractResource<Notification> {

    /** Business methods for Notifications. */
    @Inject
    private NotificationManager manager;

    @POST
    @Path("/{id: \\d+}/dismiss")
    public Response dismiss(@PathParam("id") final Long id) {
        try {
            manager.dismiss(id);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/dismissAll")
    public Response dismissAll() {
        try {
            manager.dismissAll();
            return Response.ok().build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    /**
     * Test endpoint for creating notifications.
     * @param notification payload
     * @return ok response
     */
    @RequiresPermissions(Permissions.ADMIN)
    @POST
    public Response create(final Notification notification) {
        try {
            Notification created = manager.create(notification);
            return Response.ok(created).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }
}
