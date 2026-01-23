package com.vertek.corporate.qto.activation.attempt.emailView;

import com.vertek.corporate.qto.common.AbstractResource;
import com.vertek.corporate.qto.common.PaginatedResult;
import com.vertek.corporate.qto.template.email.EmailTemplate;
import com.vertek.corporate.qto.template.email.EmailTemplateManager;
import com.vertek.corporate.qto.template.email.EmailTemplateSearchCriteria;
import org.jboss.resteasy.annotations.Form;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/activationAttemptEmailViews")
@Consumes("application/json")
@Produces("application/json")
public class ActivationAttemptEmailViewResource extends AbstractResource<ActivationAttemptEmailView> {
    /** Business logic layer for Email Templates. */
    @Inject
    private ActivationAttemptEmailViewManager manager;

    /**
     * API endpoint that returns an activation attempt email view by id.
     * @param id the id of the activation attempt email view to return.
     * @return a response containing the matching activation attempt email view.
     */
    @GET
    @Path("/{id}")
    public Response getActivationAttemptEmailView(@PathParam("id") final Long id) {
        ActivationAttemptEmailView retrieved = manager.retrieve(id);
        return Response.ok(retrieved).build();
    }
}
