package com.endeavorms.velocity.qto.service;

import com.endeavorms.velocity.qto.AbstractServiceResource;
import com.endeavorms.velocity.qto.service.engineeringIAM.EngineeringIAMService;
import com.endeavorms.velocity.qto.service.engineeringIAM.EngineeringIAMServiceManager;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.inject.Inject;

/**
 * @author bmccormick
 * @since 11/12/2024
 */
@RestController
@RequestMapping("/api/engineeringIAMService")
public class EngineeringIAMServiceResource extends AbstractServiceResource<EngineeringIAMService> {

    @Inject
    private EngineeringIAMServiceManager manager;

    @Override
    protected EngineeringIAMServiceManager getManager() { return manager; }

    @Override
    protected String getResourcePath() {
        return "/engineeringIAMService";
    }
}
