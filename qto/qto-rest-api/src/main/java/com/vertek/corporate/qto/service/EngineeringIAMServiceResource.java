package com.vertek.corporate.qto.service;

import com.vertek.corporate.qto.AbstractServiceResource;
import com.vertek.corporate.qto.service.engineeringIAM.EngineeringIAMService;
import com.vertek.corporate.qto.service.engineeringIAM.EngineeringIAMServiceManager;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;


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
