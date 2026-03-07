package com.vertek.corporate.qto.service;

import com.vertek.corporate.qto.AbstractServiceResource;
import com.vertek.corporate.qto.service.threatMDR.ThreatMDRService;
import com.vertek.corporate.qto.service.threatMDR.ThreatMDRServiceManager;

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
}
