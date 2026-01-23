package com.vertek.corporate.qto;

import com.vertek.corporate.qto.activation.ActivationViewManager;
import com.vertek.corporate.qto.activation.ActivationView;
import com.vertek.corporate.qto.activation.ActivationViewSearchCriteria;
import com.vertek.corporate.qto.activation.ActivationWorklistMeta;
import com.vertek.corporate.qto.authentication.Permissions;
import com.vertek.corporate.qto.common.AbstractResource;
import com.vertek.corporate.qto.common.PaginatedResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.jboss.resteasy.annotations.Form;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * @author rcasey
 * @since 3/1/2023
 */
@Path("/activationViews")
@Consumes("application/json")
@Produces({"application/json", "application/vnd.ms-excel"})
public class ActivationViewResource extends AbstractResource<ActivationView> {

    @Inject
    private ActivationViewManager manager;

    @GET
    public Response getActivationViews(@Form final ActivationViewSearchCriteria criteria) {
        ActivationViewSearchCriteria crit = getExportCriteria(criteria);
        PaginatedResult<ActivationView> result = manager.findBySearchCriteria(crit);
        return getCollectionResource(result, crit, getLocation(ActivationViewResource.class));
    }

    @GET
    @Path("/meta")
    public Response getActivationWorklistMeta(@Form final ActivationViewSearchCriteria criteria) {
        ActivationWorklistMeta meta = manager.getWorklistMeta(criteria);
        return Response.ok(meta).build();
    }
}
