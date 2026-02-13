package com.endeavorms.velocity.qto.note;

import com.endeavorms.velocity.qto.common.AbstractResource;
import com.endeavorms.velocity.qto.common.PaginatedResult;
import com.endeavorms.velocity.qto.common.PreconditionsUtil;
import com.endeavorms.velocity.qto.common.SecurityUtils;
import com.endeavorms.velocity.qto.subject.Subject;
import com.endeavorms.velocity.qto.subject.SubjectManager;
import org.springframework.security.access.prepost.PreAuthorize;
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
import java.util.Date;

/**
 * @author fcurran
 * @since 9/28/2023
 */
@Path("/disputeNotes")
@Consumes("application/json")
@Produces("application/json")
public class DisputeNoteResource extends AbstractResource<DisputeNote> {

    @Override
    protected String getResourcePath() {
        return "/disputeNotes";
    }

    /** Business methods for services. */
    @Inject
    private DisputeNoteManager manager;

    /** Business methods for subjects. */
    @Inject
    private SubjectManager subjectManager;

    /**
     * Retrieves all ServiceNotes matching the given criteria.
     * @param criteria the criteria to filter by.
     * @return matching serviceNotes.
     */
    @GET
    public Response getDisputeNotes(@Form final DisputeNoteSearchCriteria criteria) {
        PaginatedResult<DisputeNote> result = manager.findBySearchCriteria(criteria);
        return toResponse(getCollectionResource(result, criteria, getLocation(DisputeNoteResource.class)));
    }

    @POST
    @PreAuthorize("hasAuthority('inventory:write')")
    public Response create(final DisputeNote note) {
        try {
            PreconditionsUtil.checkArgument(note.getDisputeId(), "A disputeId is required");
            String loggedInUser = SecurityUtils.getLoggedInUser();
            Subject subject = subjectManager.findByUsername(loggedInUser);
            loggedInUser = subject.getDisplayName();
            note.setCreatedById(subject.getId());
            note.setCreatedBy(loggedInUser);
            note.setCreatedDate(new Date());
            return Response.ok(manager.create(note)).build();
        } catch (IllegalArgumentException e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    /**
     * Edit a dispute note.
     * @param id the note id.
     * @param note the note to edit.
     * @return the edited note.
     */
    @PUT
    @Path("/{id: \\d+}")
    public Response edit(@PathParam("id") final Long id, final DisputeNote note) {
        try {
            PreconditionsUtil.checkArgument(note.getId(), "A note ID is required");
            DisputeNote existing = manager.retrieve(note.getId());
            if (!manager.determineEditability(existing)) {
                return Response.serverError().entity("You do not have permission to edit this note.").build();
            }
            String loggedInUser = SecurityUtils.getLoggedInUser();
            Subject subject = subjectManager.findByUsername(loggedInUser);
            loggedInUser = subject.getDisplayName();
            note.setEditedBy(loggedInUser);
            note.setEditedDate(new Date());
            return Response.ok(manager.edit(note)).build();
        } catch (IllegalArgumentException e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }
}
