package com.endeavorms.velocity.qto.service;

import com.endeavorms.velocity.qto.AbstractServiceResource;
import com.endeavorms.velocity.qto.service.threatMDR.ThreatMDRService;
import com.endeavorms.velocity.qto.service.threatMDR.ThreatMDRServiceManager;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.inject.Inject;

/**
 * @author bmccormick
 * @since 2/10/2023
 */
@RestController
@RequestMapping("/api/threatMDRService")
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
