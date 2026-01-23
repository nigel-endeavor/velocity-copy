package com.vertek.corporate.qto.service;

import com.vertek.corporate.qto.AbstractServiceResource;
import com.vertek.corporate.qto.service.riskMDR.RiskMDRService;
import com.vertek.corporate.qto.service.riskMDR.RiskMDRServiceManager;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

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
