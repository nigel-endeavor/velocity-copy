package com.endeavorms.velocity.qto.service;

import com.endeavorms.velocity.qto.AbstractServiceResource;
import com.endeavorms.velocity.qto.service.riskMDR.RiskMDRService;
import com.endeavorms.velocity.qto.service.riskMDR.RiskMDRServiceManager;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.inject.Inject;

/**
 * @author bmccormick
 * @since 11/6/2024
 */
@RestController
@RequestMapping("/api/riskMDRService")
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
