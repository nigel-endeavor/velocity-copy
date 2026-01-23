package com.vertek.corporate.qto.service;

import com.vertek.corporate.qto.AbstractServiceResource;
import com.vertek.corporate.qto.service.threatMDR.ThreatMDRService;
import com.vertek.corporate.qto.service.threatMDR.ThreatMDRServiceManager;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

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
