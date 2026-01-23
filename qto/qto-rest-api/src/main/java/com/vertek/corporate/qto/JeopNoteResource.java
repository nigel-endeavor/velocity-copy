package com.vertek.corporate.qto;

import com.vertek.corporate.qto.authentication.Permissions;
import com.vertek.corporate.qto.common.AbstractResource;
import com.vertek.corporate.qto.common.PaginatedResult;
import com.vertek.corporate.qto.note.JeopNote;
import com.vertek.corporate.qto.note.JeopNoteManager;
import com.vertek.corporate.qto.note.JeopNoteSearchCriteria;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.jboss.resteasy.annotations.Form;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * @author llevit
 * @since 1/16/2023
 */
@Path("/jeopNotes")
@Consumes("application/json")
@Produces("application/json")
public class JeopNoteResource extends AbstractResource<JeopNote> {

    /** Business methods for Jeops. */
    @Inject
    private JeopNoteManager manager;

    /**
     * Retrieves all JeopNotes matching the given criteria.
     * @param criteria the criteria to filter by.
     * @return matching jeopNotes.
     */
    @GET
    @RequiresPermissions(value = {
            Permissions.INVENTORY_READ,
            Permissions.ORDER_READ}, logical = Logical.OR)
    public Response getJeopNotes(@Form final JeopNoteSearchCriteria criteria) {
        PaginatedResult<JeopNote> result = manager.findBySearchCriteria(criteria);
        return getCollectionResource(result, criteria, getLocation(JeopNoteResource.class));
    }

    @POST
    @RequiresPermissions(value = {
            Permissions.INVENTORY_WRITE,
            Permissions.ORDER_WRITE}, logical = Logical.OR)
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
