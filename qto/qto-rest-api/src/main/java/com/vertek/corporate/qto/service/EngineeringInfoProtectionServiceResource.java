package com.vertek.corporate.qto.service;

import com.vertek.corporate.qto.AbstractServiceResource;
import com.vertek.corporate.qto.service.engineeringInfoProtection.EngineeringInfoProtectionManager;
import com.vertek.corporate.qto.service.engineeringInfoProtection.EngineeringInfoProtectionService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/engineeringInfoProtectionService")
@Consumes("application/json")
@Produces("application/json")
public class EngineeringInfoProtectionServiceResource extends AbstractServiceResource<EngineeringInfoProtectionService> {

    @Inject
    private EngineeringInfoProtectionManager manager;

    @Override
    protected EngineeringInfoProtectionManager getManager() { return manager; }
}
