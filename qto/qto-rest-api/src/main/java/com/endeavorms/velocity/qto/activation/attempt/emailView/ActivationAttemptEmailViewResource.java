package com.endeavorms.velocity.qto.activation.attempt.emailView;

import com.endeavorms.velocity.qto.common.AbstractResource;
import com.endeavorms.velocity.qto.common.PaginatedResult;
import com.endeavorms.velocity.qto.template.email.EmailTemplate;
import com.endeavorms.velocity.qto.template.email.EmailTemplateManager;
import com.endeavorms.velocity.qto.template.email.EmailTemplateSearchCriteria;
import org.jboss.resteasy.annotations.Form;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

@Path("/activationAttemptEmailViews")
@Consumes("application/json")
@Produces("application/json")
public class ActivationAttemptEmailViewResource extends AbstractResource<ActivationAttemptEmailView> {

    @Override
    protected String getResourcePath() {
        return "/activationAttemptEmailViews";
    }
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
