package com.endeavorms.velocity.qto.service;

import com.endeavorms.velocity.qto.AbstractServiceResource;
import com.endeavorms.velocity.qto.service.broadband.BroadbandService;
import com.endeavorms.velocity.qto.service.broadband.BroadbandServiceManager;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

/**
 * @author rcasey
 * @since 2/10/2023
 */
@Path("/broadbandService")
@Consumes("application/json")
@Produces("application/json")
public class BroadbandServiceResource extends AbstractServiceResource<BroadbandService> {

    @Inject
    private BroadbandServiceManager manager;

    @Override
    protected BroadbandServiceManager getManager() {
        return manager;
    }

    @Override
    protected String getResourcePath() {
        return "/broadbandService";
    }
}
