package com.endeavorms.velocity.qto.service;

import com.endeavorms.velocity.qto.AbstractServiceResource;
import com.endeavorms.velocity.qto.service._4g5g.GService;
import com.endeavorms.velocity.qto.service._4g5g.GServiceManager;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

/**
 * @author rcasey
 * @sicne 6/7/2023
 */
@Path("/4G5GServices")
@Consumes("application/json")
@Produces("application/json")
public class GServiceResource extends AbstractServiceResource<GService> {

    @Inject
    private GServiceManager manager;

    @Override
    protected GServiceManager getManager() {
        return manager;
    }

    @Override
    protected String getResourcePath() {
        return "/4G5GServices";
    }
}
