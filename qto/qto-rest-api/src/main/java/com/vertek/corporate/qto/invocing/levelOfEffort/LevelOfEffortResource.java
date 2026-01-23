package com.vertek.corporate.qto.invocing.levelOfEffort;

import com.vertek.corporate.qto.common.AbstractResource;
import com.vertek.corporate.qto.invoicing.levelOfEffort.LevelOfEffort;
import com.vertek.corporate.qto.invoicing.levelOfEffort.LevelOfEffortManager;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

/**
 * @author rcasey
 * @since 7/12/2023
 */
@Path("/levelOfEffort")
@Consumes("application/json")
@Produces("application/json")
public class LevelOfEffortResource extends AbstractResource<LevelOfEffort> {

    /** Business methods for LevelOfEffort. */
    @Inject
    private LevelOfEffortManager manager;

    @GET
    public Response getLevelOfEffort(@QueryParam("companyId") final Long companyId) {
        return Response.ok(manager.findByCompanyId(companyId)).build();
    }
}
