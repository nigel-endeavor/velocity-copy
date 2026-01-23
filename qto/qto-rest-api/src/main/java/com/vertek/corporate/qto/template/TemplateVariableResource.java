package com.vertek.corporate.qto.template;

import com.vertek.corporate.qto.common.AbstractResource;
import com.vertek.corporate.qto.common.PaginatedResult;
import com.vertek.corporate.qto.template.variable.TemplateVariable;
import com.vertek.corporate.qto.template.variable.TemplateVariableManager;
import com.vertek.corporate.qto.template.variable.TemplateVariableSearchCriteria;
import org.jboss.resteasy.annotations.Form;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * REST API for template variables.
 */
@Path("/templateVariables")
@Consumes("application/json")
@Produces("application/json")
public class TemplateVariableResource extends AbstractResource<TemplateVariable> {
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
         return getCollectionResource(result, criteria, getLocation(TemplateVariableResource.class));
     }
}
