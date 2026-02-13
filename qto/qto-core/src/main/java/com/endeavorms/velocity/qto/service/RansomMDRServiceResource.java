package com.endeavorms.velocity.qto.service;

import com.endeavorms.velocity.qto.AbstractServiceResource;
import com.endeavorms.velocity.qto.service.ransomMDR.RansomMDRService;
import com.endeavorms.velocity.qto.service.ransomMDR.RansomMDRServiceManager;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.inject.Inject;

/**
 * @author bmccormick
 * @since 11/4/2024
 */
@RestController
@RequestMapping("/api/ransomMDRService")
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






