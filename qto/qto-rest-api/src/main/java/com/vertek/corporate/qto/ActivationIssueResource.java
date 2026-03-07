package com.vertek.corporate.qto;

import com.vertek.corporate.qto.activation.issue.ActivationIssue;
import com.vertek.corporate.qto.activation.issue.ActivationIssueManager;
import com.vertek.corporate.qto.authentication.Permissions;
import com.vertek.corporate.qto.common.AbstractResource;
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
 * @since 3/29/2023
 */
@Path("/activationIssues")
@Consumes("application/json")
@Produces("application/json")
public class ActivationIssueResource extends AbstractResource {

    /** Business methods for ActivationIssues. */
    @Inject
    private ActivationIssueManager manager;

    @GET
    @RequiresPermissions(value = {
            Permissions.INVENTORY_READ,
            Permissions.ORDER_READ}, logical = Logical.OR)
    public Response getActivationIssues(@QueryParam("activationAttemptId") final Long activationAttemptId) {
        List<ActivationIssue> issues = manager.findByAttemptId(activationAttemptId);
        return Response.ok(issues).build();
    }

    @POST
    @RequiresPermissions(value = {
            Permissions.INVENTORY_WRITE,
            Permissions.ORDER_WRITE}, logical = Logical.OR)
    public Response create(final ActivationIssue issue) {
        try {
            ActivationIssue created = manager.create(issue);
            return Response.ok(created).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id: \\d+}")
    @RequiresPermissions(value = {
            Permissions.INVENTORY_WRITE,
            Permissions.ORDER_WRITE}, logical = Logical.OR)
    public Response edit(@PathParam("id") final Long id, final ActivationIssue issue) {
        try {
            if (!id.equals(issue.getId())) {
                throw new IllegalArgumentException("identifier in path does not match that of passed entity");
            }
            ActivationIssue updated = manager.edit(issue);
            return Response.ok(updated).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }
}
