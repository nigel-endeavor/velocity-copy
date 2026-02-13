package com.endeavorms.velocity.qto.service;

import com.endeavorms.velocity.qto.AbstractServiceResource;
import com.endeavorms.velocity.qto.service.broadband.BroadbandService;
import com.endeavorms.velocity.qto.service.broadband.BroadbandServiceManager;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.inject.Inject;

/**
 * @author rcasey
 * @since 2/10/2023
 */
@RestController
@RequestMapping("/api/broadbandService")
public class BroadbandServiceResource extends AbstractServiceResource<BroadbandService> {

    @Inject
    private BroadbandServiceManager manager;

    @Override
    protected BroadbandServiceManager getManager() {
        return manager;
    }

    @Override
    protected String getResourcePath() {
        return "/broadbandService";
    }
}
