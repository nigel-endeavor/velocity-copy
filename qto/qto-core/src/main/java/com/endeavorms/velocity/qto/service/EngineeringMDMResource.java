package com.endeavorms.velocity.qto.service;


import com.endeavorms.velocity.qto.AbstractServiceResource;
import com.endeavorms.velocity.qto.service.engineeringMDM.EngineeringMDMManager;
import com.endeavorms.velocity.qto.service.engineeringMDM.EngineeringMDMService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.inject.Inject;

@RestController
@RequestMapping("/api/engineeringMDMService")
public class EngineeringMDMResource extends AbstractServiceResource<EngineeringMDMService> {

    @Inject
    private EngineeringMDMManager manager;

    @Override
    protected EngineeringMDMManager getManager() { return manager; }

    @Override
    protected String getResourcePath() {
        return "/engineeringMDMService";
    }
}
