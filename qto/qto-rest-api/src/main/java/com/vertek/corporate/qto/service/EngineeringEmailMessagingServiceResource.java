package com.vertek.corporate.qto.service;

import com.vertek.corporate.qto.AbstractServiceResource;
import com.vertek.corporate.qto.service.engineeringEmailMessaging.EngineeringEmailMessagingManager;
import com.vertek.corporate.qto.service.engineeringEmailMessaging.EngineeringEmailMessagingService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/engineeringEmailMessagingService")
@Consumes("application/json")
@Produces("application/json")

public class EngineeringEmailMessagingServiceResource extends AbstractServiceResource<EngineeringEmailMessagingService> {

@Inject
    private EngineeringEmailMessagingManager manager;

@Override
    protected EngineeringEmailMessagingManager getManager() { return manager; }
}
