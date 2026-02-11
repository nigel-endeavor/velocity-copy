package com.endeavorms.velocity.qto.service;

import com.endeavorms.velocity.qto.AbstractServiceResource;
import com.endeavorms.velocity.qto.service.engineeringEmailMessaging.EngineeringEmailMessagingManager;
import com.endeavorms.velocity.qto.service.engineeringEmailMessaging.EngineeringEmailMessagingService;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

@Path("/engineeringEmailMessagingService")
@Consumes("application/json")
@Produces("application/json")

public class EngineeringEmailMessagingServiceResource extends AbstractServiceResource<EngineeringEmailMessagingService> {

@Inject
    private EngineeringEmailMessagingManager manager;

@Override
    protected EngineeringEmailMessagingManager getManager() { return manager; }

    @Override
    protected String getResourcePath() {
        return "/engineeringEmailMessagingService";
    }
}
