package com.endeavorms.velocity.qto.service;

import com.endeavorms.velocity.qto.AbstractServiceResource;
import com.endeavorms.velocity.qto.service.crossconnect.CrossConnectService;
import com.endeavorms.velocity.qto.service.crossconnect.CrossConnectServiceManager;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.inject.Inject;

/**
 * Cross Connect Service Resource.
 * @author fcurran
 * @since 1/12/2024
 */
@RestController
@RequestMapping("/api/crossConnectService")
public class CrossConnectServiceResource extends AbstractServiceResource<CrossConnectService> {

    @Inject
    private CrossConnectServiceManager manager;

    @Override
    protected CrossConnectServiceManager getManager() {
        return manager;
    }

    @Override
    protected String getResourcePath() {
        return "/crossConnectService";
    }
}
