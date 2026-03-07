package com.vertek.corporate.qto;

import com.vertek.corporate.qto.authentication.Permissions;
import com.vertek.corporate.qto.common.SecurityUtils;
import com.vertek.corporate.qto.helpdesk.HelpDeskManager;
import com.vertek.corporate.qto.helpdesk.TicketRequest;
import com.vertek.corporate.qto.subject.Subject;
import com.vertek.corporate.qto.subject.SubjectManager;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

/**
 * @author bmccormick
 * @since 3/28/2024
 */
@Path("/helpdesk")
@Consumes({"application/json"})
@Produces("application/json")
@RequiresPermissions(value = {
        Permissions.INVENTORY_READ,
        Permissions.ORDER_READ}, logical = Logical.OR)
public class HelpDeskResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(HelpDeskResource.class);

    @Inject
    private HelpDeskManager manager;

    @Inject
    private SubjectManager subjectManager;

    @POST
    public Response createTicket(final TicketRequest requestData) {
        try {
            Subject subject = subjectManager.findByEmailAddress(SecurityUtils.getLoggedInUser());
            String emailAddress = subject.getEmailAddress();
            requestData.setUserEmail(emailAddress);
            manager.createTicket(requestData);
            return Response.ok().build();
        } catch (Exception e) {
            LOGGER.error("Error creating helpdesk ticket: {}", e.getMessage());
            return Response.serverError().entity(e.getMessage()).build();
        }
    }
}



