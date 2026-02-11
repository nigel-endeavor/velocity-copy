package com.endeavorms.velocity.qto.template;

import com.endeavorms.velocity.qto.common.AbstractResource;
import com.endeavorms.velocity.qto.common.PaginatedResult;
import com.endeavorms.velocity.qto.template.variable.TemplateVariable;
import com.endeavorms.velocity.qto.template.variable.TemplateVariableManager;
import com.endeavorms.velocity.qto.template.variable.TemplateVariableSearchCriteria;
import org.jboss.resteasy.annotations.Form;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

/**
 * REST API for template variables.
 */
@Path("/templateVariables")
@Consumes("application/json")
@Produces("application/json")
public class TemplateVariableResource extends AbstractResource<TemplateVariable> {

    @Override
    protected String getResourcePath() {
        return "/templateVariables";
    }
    /** Business logic layer for Template Variables. */
    @Inject
    private TemplateVariableManager manager;

    /**
     * API endpoint that returns all template variables for a given template type.
     * @param templateType the type of template to return variables for.
     * @param criteria the search criteria to use.
     * @return a response containing the matching template variables.
     */
     @GET
     @Path("/{templateType}")
     public Response getTemplateVariables(@PathParam("templateType") final String templateType,
                                          @Form final TemplateVariableSearchCriteria criteria) {
         PaginatedResult<TemplateVariable> result = manager.getTemplateVariablesByType(templateType);
         return toResponse(getCollectionResource(result, criteria, getLocation(TemplateVariableResource.class)));
     }
}
