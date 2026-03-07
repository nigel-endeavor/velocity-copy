package com.vertek.corporate.qto;

import com.vertek.corporate.qto.authentication.Permissions;
import com.vertek.corporate.qto.common.AbstractResource;
import com.vertek.corporate.qto.common.PaginatedResult;
import com.vertek.corporate.qto.common.PreconditionsUtil;
import com.vertek.corporate.qto.common.SecurityUtils;
import com.vertek.corporate.qto.note.LocationNote;
import com.vertek.corporate.qto.note.LocationNoteManager;
import com.vertek.corporate.qto.note.NoteUnionView;
import com.vertek.corporate.qto.note.NoteUnionViewSearchCriteria;
import com.vertek.corporate.qto.subject.Subject;
import com.vertek.corporate.qto.subject.SubjectManager;
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
import java.util.Date;

/**
 * @author llevit
 * @since 1/16/2023
 */
@Path("/locationNotes")
@Consumes("application/json")
@Produces("application/json")
public class LocationNoteResource extends AbstractResource<NoteUnionView> {

    /** Business methods for Locations. */
    @Inject
    private LocationNoteManager manager;

    /** Business methods for subjects. */
    @Inject
    private SubjectManager subjectManager;

    /**
     * Retrieves all LocationNotes matching the given criteria.
     * @param criteria the criteria to filter by.
     * @return matching locationNotes.
     */
    @GET
    @RequiresPermissions(value = {
            Permissions.INVENTORY_READ,
            Permissions.ORDER_READ}, logical = Logical.OR)
    public Response getLocationNotes(@Form final NoteUnionViewSearchCriteria criteria) {
        PaginatedResult<NoteUnionView> result = manager.findBySearchCriteria(criteria, false);
        return getCollectionResource(result, criteria, getLocation(LocationNoteResource.class));
    }

    @GET
    @Path("/audit")
    @RequiresPermissions(value = {
            Permissions.INVENTORY_READ,
            Permissions.ORDER_READ}, logical = Logical.OR)
    public Response getLocationAuditNotes(@Form final NoteUnionViewSearchCriteria criteria) {
        PaginatedResult<NoteUnionView> result = manager.findBySearchCriteria(criteria, true);
        return getCollectionResource(result, criteria, getLocation(LocationNoteResource.class));
    }

    @POST
    @RequiresPermissions(value = {
            Permissions.INVENTORY_WRITE,
            Permissions.ORDER_WRITE}, logical = Logical.OR)
    public Response create(final LocationNote note) {
        try {
            PreconditionsUtil.checkArgument(note.getLocationId(), "A locationId is required");
            String loggedInUser = SecurityUtils.getLoggedInUser();
            Subject subject = subjectManager.findByUsername(loggedInUser);
            loggedInUser = subject.getDisplayName();
            note.setCreatedById(subject.getId());
            note.setCreatedBy(loggedInUser);
            note.setCreatedDate(new Date());
            return Response.ok(manager.create(note)).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    /**
     * Edit a location note.
     * @param id the note id.
     * @param note the note to edit.
     * @return the edited note.
     */
    @PUT
    @Path("/{id: \\d+}")
    public Response edit(@PathParam("id") final Long id, final LocationNote note) {
        try {
            PreconditionsUtil.checkArgument(note.getId(), "A note ID is required");
            LocationNote existing = manager.retrieve(note.getId());
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
