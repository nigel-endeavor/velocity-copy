package com.vertek.corporate.qto;

import com.vertek.corporate.qto.common.AbstractResource;
import com.vertek.corporate.qto.common.SecurityUtils;
import com.vertek.corporate.qto.subject.CustomWorklist;
import com.vertek.corporate.qto.subject.CustomWorklistManager;
import com.vertek.corporate.qto.subject.CustomWorklistSubjectDto;
import com.vertek.corporate.qto.subject.Subject;
import com.vertek.corporate.qto.subject.SubjectCustomWorklist;
import com.vertek.corporate.qto.subject.SubjectCustomWorklistManager;
import com.vertek.corporate.qto.subject.SubjectManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/subjectCustomWorklists")
@Consumes("application/json")
@Produces("application/json")
public class SubjectCustomWorklistResource extends AbstractResource<SubjectCustomWorklist> {
/** Logging Facade.*/
    protected static final Logger LOGGER = LoggerFactory.getLogger(SubjectCustomWorklistResource.class);

    @Inject
    private SubjectManager subjectManager;
    @Inject
    private SubjectCustomWorklistManager manager;

    @Inject
    private CustomWorklistManager customWorklistManager;

    /**
     * Retrieves all custom worklists for the logged in user.
     *
     * @return the tasks for the company.
     */
    @GET
    public Response getCustomWorklists(@QueryParam("worklistName") final String worklistName) {
        List<CustomWorklistSubjectDto> customWorklists = manager.findFullCustomWorklist(worklistName);
        return Response.ok(customWorklists).build();
    }

    @POST
    public Response create(final CustomWorklistSubjectDto customWorklist) {
        manager.createFromDto(customWorklist);
        return Response.ok().build();
    }

    @PUT
    @Path("/saveFavoriteWorklist")
    public Response saveFavoriteWorklist(final CustomWorklistSubjectDto customWorklist) {
        manager.editFavorite(customWorklist);
        return Response.ok().build();
    }
    @PUT
    @Path("/saveLastViewedDate")
    public Response saveLastViewedDate(final CustomWorklistSubjectDto customWorklist) {
        manager.editLastViewedDate(customWorklist);
        return Response.ok().build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") final Long id, final CustomWorklistSubjectDto customWorklist) {
        String username = SecurityUtils.getLoggedInUser();
        Subject subject = subjectManager.findByUsername(username);
        if (!subject.getId().equals(customWorklist.getAuthorId())) {
           LOGGER.info("Only the Author can edit a Custom Worklist");
            return Response.serverError().entity("Only the Author can edit a shared Custom Worklist.").build();
        }
        manager.editFromDto(customWorklist);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") final Long id) {
        CustomWorklist customWorklist = customWorklistManager.retrieve(id);
        String username = SecurityUtils.getLoggedInUser();
        Subject subject = subjectManager.findByUsername(username);
        if (!subject.getId().equals(customWorklist.getAuthorId())) {
           LOGGER.info("Only the Author can delete a Custom Worklist");
            return Response.serverError().entity("Only the Author can delete a Custom Worklist.").build();
        }
        List<SubjectCustomWorklist> subjectCustomWorklists = manager.findByCustomWorklistId(customWorklist.getId());
        for (SubjectCustomWorklist subjectCustomWorklist : subjectCustomWorklists) {
            manager.remove(subjectCustomWorklist.getId());
        }
        customWorklistManager.remove(id);
        return Response.ok().build();
    }

}
