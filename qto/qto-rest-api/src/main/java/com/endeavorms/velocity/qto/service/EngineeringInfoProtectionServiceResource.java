package com.endeavorms.velocity.qto.service;

import com.endeavorms.velocity.qto.AbstractServiceResource;
import com.endeavorms.velocity.qto.service.engineeringInfoProtection.EngineeringInfoProtectionManager;
import com.endeavorms.velocity.qto.service.engineeringInfoProtection.EngineeringInfoProtectionService;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

@Path("/engineeringInfoProtectionService")
@Consumes("application/json")
@Produces("application/json")
public class EngineeringInfoProtectionServiceResource extends AbstractServiceResource<EngineeringInfoProtectionService> {

    @Inject
    private EngineeringInfoProtectionManager manager;

    @Override
    protected EngineeringInfoProtectionManager getManager() { return manager; }

    @Override
    protected String getResourcePath() {
        return "/engineeringInfoProtectionService";
    }
}
