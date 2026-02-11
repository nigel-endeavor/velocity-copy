package com.endeavorms.velocity.qto.service;

import com.endeavorms.velocity.qto.AbstractServiceResource;
import com.endeavorms.velocity.qto.service.crossconnect.CrossConnectService;
import com.endeavorms.velocity.qto.service.crossconnect.CrossConnectServiceManager;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

/**
 * Cross Connect Service Resource.
 * @author fcurran
 * @since 1/12/2024
 */
@Path("/crossConnectService")
@Consumes("application/json")
@Produces("application/json")
public class CrossConnectServiceResource extends AbstractServiceResource<CrossConnectService> {

    @Inject
    private CrossConnectServiceManager manager;

    @Override
    protected CrossConnectServiceManager getManager() {
        return manager;
    }

    @Override
    protected String getResourcePath() {
        return "/crossConnectService";
    }
}
