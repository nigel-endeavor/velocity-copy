package com.vertek.corporate.qto.invocing.levelOfEffort;

import com.vertek.corporate.qto.common.AbstractResource;
import com.vertek.corporate.qto.invoicing.levelOfEffort.LevelOfEffort;
import com.vertek.corporate.qto.invoicing.levelOfEffort.LevelOfEffortManager;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

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
