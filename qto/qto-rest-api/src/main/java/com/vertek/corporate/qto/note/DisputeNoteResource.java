package com.vertek.corporate.qto.note;

import com.vertek.corporate.qto.authentication.Permissions;
import com.vertek.corporate.qto.common.AbstractResource;
import com.vertek.corporate.qto.common.PaginatedResult;
import com.vertek.corporate.qto.common.PreconditionsUtil;
import com.vertek.corporate.qto.common.SecurityUtils;
import com.vertek.corporate.qto.subject.Subject;
import com.vertek.corporate.qto.subject.SubjectManager;
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
import java.util.Date;

/**
 * @author fcurran
 * @since 9/28/2023
 */
@Path("/disputeNotes")
@Consumes("application/json")
@Produces("application/json")
public class DisputeNoteResource extends AbstractResource<DisputeNote> {

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
        return getCollectionResource(result, criteria, getLocation(DisputeNoteResource.class));
    }

    @POST
    @RequiresPermissions(Permissions.INVENTORY_WRITE)
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
