package com.endeavorms.velocity.qto.service;

import com.endeavorms.velocity.qto.AbstractServiceResource;
import com.endeavorms.velocity.qto.service.engineeringIAM.EngineeringIAMService;
import com.endeavorms.velocity.qto.service.engineeringIAM.EngineeringIAMServiceManager;

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

    @Override
    protected String getResourcePath() {
        return "/engineeringIAMService";
    }
}
