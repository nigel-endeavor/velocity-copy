package com.vertek.corporate.qto.service;

import com.vertek.corporate.qto.AbstractServiceResource;
import com.vertek.corporate.qto.service.ransomMDR.RansomMDRService;
import com.vertek.corporate.qto.service.ransomMDR.RansomMDRServiceManager;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

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






