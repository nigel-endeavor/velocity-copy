package com.endeavorms.velocity.qto;

import com.endeavorms.velocity.qto.activation.ActivationViewManager;
import com.endeavorms.velocity.qto.activation.ActivationView;
import com.endeavorms.velocity.qto.activation.ActivationViewSearchCriteria;
import com.endeavorms.velocity.qto.activation.ActivationWorklistMeta;
import com.endeavorms.velocity.qto.common.AbstractResource;
import com.endeavorms.velocity.qto.common.PaginatedResult;
import org.springframework.security.access.prepost.PreAuthorize;
import org.jboss.resteasy.annotations.Form;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

/**
 * @author rcasey
 * @since 3/1/2023
 */
@Path("/activationViews")
@Consumes("application/json")
@Produces({"application/json", "application/vnd.ms-excel"})
public class ActivationViewResource extends AbstractResource<ActivationView> {

    @Override
    protected String getResourcePath() {
        return "/activationViews";
    }

    @Inject
    private ActivationViewManager manager;

    @GET
    public Response getActivationViews(@Form final ActivationViewSearchCriteria criteria) {
        ActivationViewSearchCriteria crit = getExportCriteria(criteria);
        PaginatedResult<ActivationView> result = manager.findBySearchCriteria(crit);
        return toResponse(getCollectionResource(result, crit, getLocation(ActivationViewResource.class)));
    }

    @GET
    @Path("/meta")
    public Response getActivationWorklistMeta(@Form final ActivationViewSearchCriteria criteria) {
        ActivationWorklistMeta meta = manager.getWorklistMeta(criteria);
        return Response.ok(meta).build();
    }
}
