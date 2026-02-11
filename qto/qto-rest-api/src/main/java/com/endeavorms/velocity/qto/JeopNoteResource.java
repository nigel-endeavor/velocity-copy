package com.endeavorms.velocity.qto;

import com.endeavorms.velocity.qto.common.AbstractResource;
import com.endeavorms.velocity.qto.common.PaginatedResult;
import com.endeavorms.velocity.qto.note.JeopNote;
import com.endeavorms.velocity.qto.note.JeopNoteManager;
import com.endeavorms.velocity.qto.note.JeopNoteSearchCriteria;
import org.springframework.security.access.prepost.PreAuthorize;
import org.jboss.resteasy.annotations.Form;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

/**
 * @author llevit
 * @since 1/16/2023
 */
@Path("/jeopNotes")
@Consumes("application/json")
@Produces("application/json")
public class JeopNoteResource extends AbstractResource<JeopNote> {

    @Override
    protected String getResourcePath() {
        return "/jeopNotes";
    }

    /** Business methods for Jeops. */
    @Inject
    private JeopNoteManager manager;

    /**
     * Retrieves all JeopNotes matching the given criteria.
     * @param criteria the criteria to filter by.
     * @return matching jeopNotes.
     */
    @GET
    @PreAuthorize("hasAnyAuthority('inventory:read','order:read')")
    public Response getJeopNotes(@Form final JeopNoteSearchCriteria criteria) {
        PaginatedResult<JeopNote> result = manager.findBySearchCriteria(criteria);
        return toResponse(getCollectionResource(result, criteria, getLocation(JeopNoteResource.class)));
    }

    @POST
    @PreAuthorize("hasAnyAuthority('inventory:write','order:write')")
    public Response create(final JeopNote note) {
        try {
            JeopNote returnedNote;
            if (note.getJeopInstanceId() == null) {
                throw new IllegalArgumentException("Missing jeopId.");
            } else {
                returnedNote = manager.create(note);
            }
            return Response.ok(returnedNote).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }
}
