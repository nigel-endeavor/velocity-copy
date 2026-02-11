package com.endeavorms.velocity.qto.service;

import com.endeavorms.velocity.qto.AbstractServiceResource;
import com.endeavorms.velocity.qto.service.engineeringEndpoint.EngineeringEndpointManager;
import com.endeavorms.velocity.qto.service.engineeringEndpoint.EngineeringEndpointService;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

@Path("/engineeringEndpointService")
@Consumes("application/json")
@Produces("application/json")
public class EngineeringEndpointResource extends AbstractServiceResource<EngineeringEndpointService> {

    @Inject
    private EngineeringEndpointManager manager;

    @Override
    protected EngineeringEndpointManager getManager() { return manager; }

    @Override
    protected String getResourcePath() {
        return "/engineeringEndpointService";
    }
}
