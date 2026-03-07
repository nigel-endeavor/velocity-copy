package com.vertek.corporate.qto.service;

import com.vertek.corporate.qto.AbstractServiceResource;
import com.vertek.corporate.qto.service.ransomMDR.RansomMDRService;
import com.vertek.corporate.qto.service.ransomMDR.RansomMDRServiceManager;

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
}






