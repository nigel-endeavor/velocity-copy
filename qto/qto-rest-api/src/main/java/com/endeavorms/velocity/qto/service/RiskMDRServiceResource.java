package com.endeavorms.velocity.qto.service;

import com.endeavorms.velocity.qto.AbstractServiceResource;
import com.endeavorms.velocity.qto.service.riskMDR.RiskMDRService;
import com.endeavorms.velocity.qto.service.riskMDR.RiskMDRServiceManager;

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

    @Override
    protected String getResourcePath() {
        return "/riskMDRService";
    }
}
