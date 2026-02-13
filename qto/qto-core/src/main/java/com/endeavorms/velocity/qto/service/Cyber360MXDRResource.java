package com.endeavorms.velocity.qto.service;


import com.endeavorms.velocity.qto.AbstractServiceResource;
import com.endeavorms.velocity.qto.service.cyber360MXDR.Cyber360MXDRManager;
import com.endeavorms.velocity.qto.service.cyber360MXDR.Cyber360MXDRService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.inject.Inject;

@RestController
@RequestMapping("/api/cyber360MXDRService")
public class Cyber360MXDRResource extends AbstractServiceResource<Cyber360MXDRService> {

    @Inject
    private Cyber360MXDRManager manager;

    @Override
    protected Cyber360MXDRManager getManager() { return manager; }

    @Override
    protected String getResourcePath() {
        return "/cyber360MXDRService";
    }
}
