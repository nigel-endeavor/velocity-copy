package com.endeavorms.velocity.qto.service;

import com.endeavorms.velocity.qto.AbstractServiceResource;
import com.endeavorms.velocity.qto.service.engineeringEndpoint.EngineeringEndpointManager;
import com.endeavorms.velocity.qto.service.engineeringEndpoint.EngineeringEndpointService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.inject.Inject;

@RestController
@RequestMapping("/api/engineeringEndpointService")
public class EngineeringEndpointResource extends AbstractServiceResource<EngineeringEndpointService> {

    @Inject
    private EngineeringEndpointManager manager;

    @Override
    protected EngineeringEndpointManager getManager() { return manager; }

    @Override
    protected String getResourcePath() {
        return "/engineeringEndpointService";
    }
}
