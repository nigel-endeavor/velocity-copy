package com.vertek.corporate.qto.service;


import com.vertek.corporate.qto.AbstractServiceResource;
import com.vertek.corporate.qto.service.engineeringMDM.EngineeringMDMManager;
import com.vertek.corporate.qto.service.engineeringMDM.EngineeringMDMService;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

@Path("/engineeringMDMService")
@Consumes("application/json")
@Produces("application/json")
public class EngineeringMDMResource extends AbstractServiceResource<EngineeringMDMService> {

    @Inject
    private EngineeringMDMManager manager;

    @Override
    protected EngineeringMDMManager getManager() { return manager; }
}
