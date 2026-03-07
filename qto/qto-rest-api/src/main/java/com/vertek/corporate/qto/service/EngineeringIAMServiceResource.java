package com.vertek.corporate.qto.service;

import com.vertek.corporate.qto.AbstractServiceResource;
import com.vertek.corporate.qto.service.engineeringIAM.EngineeringIAMService;
import com.vertek.corporate.qto.service.engineeringIAM.EngineeringIAMServiceManager;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;


/**
 * @author bmccormick
 * @since 11/12/2024
 */
@Path("/engineeringIAMService")
@Consumes("application/json")
@Produces("application/json")
public class EngineeringIAMServiceResource extends AbstractServiceResource<EngineeringIAMService> {

    @Inject
    private EngineeringIAMServiceManager manager;

    @Override
    protected EngineeringIAMServiceManager getManager() { return manager; }
}
