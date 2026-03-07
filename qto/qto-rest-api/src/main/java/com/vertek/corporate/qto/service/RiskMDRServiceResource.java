package com.vertek.corporate.qto.service;

import com.vertek.corporate.qto.AbstractServiceResource;
import com.vertek.corporate.qto.service.riskMDR.RiskMDRService;
import com.vertek.corporate.qto.service.riskMDR.RiskMDRServiceManager;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

/**
 * @author bmccormick
 * @since 11/6/2024
 */

@Path("/riskMDRService")
@Consumes("application/json")
@Produces("application/json")
public class RiskMDRServiceResource extends AbstractServiceResource<RiskMDRService> {

    @Inject
    private RiskMDRServiceManager manager;

    @Override
    protected RiskMDRServiceManager getManager(){
        return manager;
    }
}
