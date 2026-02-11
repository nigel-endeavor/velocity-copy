package com.endeavorms.velocity.qto.service;


import com.endeavorms.velocity.qto.AbstractServiceResource;
import com.endeavorms.velocity.qto.service.cyber360MXDR.Cyber360MXDRManager;
import com.endeavorms.velocity.qto.service.cyber360MXDR.Cyber360MXDRService;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

@Path("/cyber360MXDRService")
@Consumes("application/json")
@Produces("application/json")
public class Cyber360MXDRResource extends AbstractServiceResource<Cyber360MXDRService> {

    @Inject
    private Cyber360MXDRManager manager;

    @Override
    protected Cyber360MXDRManager getManager() { return manager; }

    @Override
    protected String getResourcePath() {
        return "/cyber360MXDRService";
    }
}
