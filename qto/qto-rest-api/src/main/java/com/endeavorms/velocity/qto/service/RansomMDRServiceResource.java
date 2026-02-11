package com.endeavorms.velocity.qto.service;

import com.endeavorms.velocity.qto.AbstractServiceResource;
import com.endeavorms.velocity.qto.service.ransomMDR.RansomMDRService;
import com.endeavorms.velocity.qto.service.ransomMDR.RansomMDRServiceManager;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

/**
 * @author bmccormick
 * @since 11/4/2024
 */
@Path("/ransomMDRService")
@Consumes("application/json")
@Produces("application/json")

public class RansomMDRServiceResource extends AbstractServiceResource<RansomMDRService> {
    @Inject
    private RansomMDRServiceManager manager;

    @Override
    protected RansomMDRServiceManager getManager() {
        return manager;
    }

    @Override
    protected String getResourcePath() {
        return "/ransomMDRService";
    }
}






