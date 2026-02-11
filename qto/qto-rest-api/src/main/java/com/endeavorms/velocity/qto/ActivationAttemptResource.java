package com.endeavorms.velocity.qto;

import com.endeavorms.velocity.qto.activation.attempt.ActivationAttempt;
import com.endeavorms.velocity.qto.activation.attempt.ActivationAttemptManager;
import com.endeavorms.velocity.qto.activation.attempt.ActivationAttemptPushValidationException;
import com.endeavorms.velocity.qto.activation.attempt.IssActivationAttemptEventHandler;
import com.endeavorms.velocity.qto.common.AbstractResource;
import com.endeavorms.velocity.qto.common.PreconditionsUtil;
import org.springframework.security.access.prepost.PreAuthorize;

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
import java.util.Map;

/**
 * @author rcasey
 * @since 3/23/2023
 */
@Path("/activationAttempts")
@Consumes("application/json")
@Produces("application/json")
public class ActivationAttemptResource extends AbstractResource {

    @Override
    protected String getResourcePath() {
        return "/activationAttempts";
    }

    /** Business methods for ActivationAttempts. */
    @Inject
    private ActivationAttemptManager manager;

    @Inject
    private IssActivationAttemptEventHandler issEventHandler;

    @GET
    @PreAuthorize("hasAnyAuthority('inventory:read','order:read')")
    public Response getAttempts(@QueryParam("serviceId") final Long serviceId) {
        List<ActivationAttempt> attempts = manager.findByServiceId(serviceId);
        return Response.ok(attempts).build();
    }

    @POST
    @PreAuthorize("hasAnyAuthority('order:write','inventory:write')")
    public Response create(final ActivationAttempt attempt) {
        try {
            ActivationAttempt created = manager.create(attempt);
            return Response.ok(created).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id: \\d+}")
    @PreAuthorize("hasAnyAuthority('order:write','inventory:write')")
    public Response edit(@PathParam("id") final Long id, final ActivationAttempt attempt) {
        try {
            if (!id.equals(attempt.getId())) {
                throw new IllegalArgumentException("identifier in path does not match that of passed entity");
            }
            ActivationAttempt updated = null;

            if ("Complete".equals(attempt.getScheduledAttemptStatus())) {
                updated = issEventHandler.handlePushComplete(attempt);
            } else if ("Partial Complete - Pending Re-Schedule".equals(attempt.getScheduledAttemptStatus())) {
                updated = issEventHandler.handlePushPartial(attempt);
            } else if ("Incomplete - Pending Re-Schedule".equals(attempt.getScheduledAttemptStatus())) {
                updated = issEventHandler.handlePushIncomplete(attempt);
            } else {
                updated = manager.edit(attempt);
            }

            if (attempt.isDuplicateToRelated()
                    && ("Complete".equals(updated.getScheduledAttemptStatus()))) {
                manager.checkDuplicateToRelated(updated);
            }

            return Response.ok(updated).build();
        } catch (ActivationAttemptPushValidationException e) {
            return Response.serverError().entity(e.getErrors()).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/{id: \\d+}/cancel")
    @PreAuthorize("hasAnyAuthority('order:write','inventory:write')")
    public Response cancel(@PathParam("id") final Long id, final Map<String, Boolean> requestBody) {
        try {
            boolean applySameDayCancelSurcharge = requestBody.get("applySameDayCancelSurcharge");
            ActivationAttempt cancelled = manager.cancel(id, applySameDayCancelSurcharge);
            return Response.ok(cancelled).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/{id: \\d+}/rollback")
    @PreAuthorize("hasAuthority('order:write-terminal')")
    public Response rollBack(@PathParam("id") final Long id) {
        try {
            ActivationAttempt activationAttempt = issEventHandler.rollback(id);
            return Response.ok(activationAttempt).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

         /**
     * Adds a note to the dispatch and sends it to Endeavor. The note and appointment information associated with the
     * note is updated.
     * This is for testing the functionality through the ftdi code without pushing an Activation Attempt.
     * @param dispatchId the ID of the dispatch to add the note to.
     * @param note the edited Dispatch to add to the dispatch.
     * @return a response potentially including the created entity.
     */
    @POST
    @Path("/notes")
    @PreAuthorize("hasAnyAuthority('order:write','inventory:write')")
    public Response addNote(@QueryParam("dispatchId") final Long dispatchId,
                               @QueryParam("tenantId") final Long tenantId, final String note) {
        PreconditionsUtil.checkArgument(dispatchId, "Dispatch ID is required to add note.");


        LOGGER.debug("Received Note: " + note);
//        issEventHandler.sendNoteToFtdi(note, dispatchId, tenantId);

        return Response.ok().build();
    }
}
