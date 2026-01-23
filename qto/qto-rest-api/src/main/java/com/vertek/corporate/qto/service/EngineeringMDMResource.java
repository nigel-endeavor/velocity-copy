package com.vertek.corporate.qto.service;


import com.vertek.corporate.qto.AbstractServiceResource;
import com.vertek.corporate.qto.service.engineeringMDM.EngineeringMDMManager;
import com.vertek.corporate.qto.service.engineeringMDM.EngineeringMDMService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/engineeringMDMService")
@Consumes("application/json")
@Produces("application/json")
public class EngineeringMDMResource extends AbstractServiceResource<EngineeringMDMService> {

    @Inject
    private EngineeringMDMManager manager;

    @Override
    protected EngineeringMDMManager getManager() { return manager; }
}
