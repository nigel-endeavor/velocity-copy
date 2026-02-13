package com.endeavorms.velocity.qto.service;

import com.endeavorms.velocity.qto.AbstractServiceResource;
import com.endeavorms.velocity.qto.service.engineeringEmailMessaging.EngineeringEmailMessagingManager;
import com.endeavorms.velocity.qto.service.engineeringEmailMessaging.EngineeringEmailMessagingService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.inject.Inject;

@RestController
@RequestMapping("/api/engineeringEmailMessagingService")
public class EngineeringEmailMessagingServiceResource extends AbstractServiceResource<EngineeringEmailMessagingService> {

@Inject
    private EngineeringEmailMessagingManager manager;

@Override
    protected EngineeringEmailMessagingManager getManager() { return manager; }

    @Override
    protected String getResourcePath() {
        return "/engineeringEmailMessagingService";
    }
}
