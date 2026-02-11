package com.endeavorms.velocity.qto.service;

import com.endeavorms.velocity.qto.AbstractServiceResource;
import com.endeavorms.velocity.qto.service.threatMDR.ThreatMDRService;
import com.endeavorms.velocity.qto.service.threatMDR.ThreatMDRServiceManager;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

/**
 * @author bmccormick
 * @since 2/10/2023
 */
@Path("/threatMDRService")
@Consumes("application/json")
@Produces("application/json")
public class ThreatMDRServiceResource extends AbstractServiceResource<ThreatMDRService> {

    @Inject
    private ThreatMDRServiceManager manager;

    @Override
    protected ThreatMDRServiceManager getManager() {
        return manager;
    }

    @Override
    protected String getResourcePath() {
        return "/threatMDRService";
    }
}
