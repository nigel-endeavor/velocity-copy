package com.vertek.corporate.qto.service;

import com.vertek.corporate.qto.AbstractServiceResource;
import com.vertek.corporate.qto.service.engineeringEndpoint.EngineeringEndpointManager;
import com.vertek.corporate.qto.service.engineeringEndpoint.EngineeringEndpointService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/engineeringEndpointService")
@Consumes("application/json")
@Produces("application/json")
public class EngineeringEndpointResource extends AbstractServiceResource<EngineeringEndpointService> {

    @Inject
    private EngineeringEndpointManager manager;

    @Override
    protected EngineeringEndpointManager getManager() { return manager; }
}
