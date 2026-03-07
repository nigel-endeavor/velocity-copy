package com.vertek.corporate.qto.service;

import com.vertek.corporate.qto.AbstractServiceResource;
import com.vertek.corporate.qto.service.engineeringEndpoint.EngineeringEndpointManager;
import com.vertek.corporate.qto.service.engineeringEndpoint.EngineeringEndpointService;

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
}
