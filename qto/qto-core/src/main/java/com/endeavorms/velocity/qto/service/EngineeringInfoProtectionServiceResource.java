package com.endeavorms.velocity.qto.service;

import com.endeavorms.velocity.qto.AbstractServiceResource;
import com.endeavorms.velocity.qto.service.engineeringInfoProtection.EngineeringInfoProtectionManager;
import com.endeavorms.velocity.qto.service.engineeringInfoProtection.EngineeringInfoProtectionService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.inject.Inject;

@RestController
@RequestMapping("/api/engineeringInfoProtectionService")
public class EngineeringInfoProtectionServiceResource extends AbstractServiceResource<EngineeringInfoProtectionService> {

    @Inject
    private EngineeringInfoProtectionManager manager;

    @Override
    protected EngineeringInfoProtectionManager getManager() { return manager; }

    @Override
    protected String getResourcePath() {
        return "/engineeringInfoProtectionService";
    }
}
