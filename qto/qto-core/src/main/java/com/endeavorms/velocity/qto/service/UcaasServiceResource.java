package com.endeavorms.velocity.qto.service;

import com.endeavorms.velocity.qto.AbstractServiceResource;
import com.endeavorms.velocity.qto.service.ucaas.UcaasService;
import com.endeavorms.velocity.qto.service.ucaas.UcaasServiceManager;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.inject.Inject;

/**
 * @author rcasey
 * @since 6/7/2023
 */
@RestController
@RequestMapping("/api/ucaasServices")
public class UcaasServiceResource extends AbstractServiceResource<UcaasService> {

    @Inject
    private UcaasServiceManager manager;

    @Override
    protected UcaasServiceManager getManager() {
        return manager;
    }

    @Override
    protected String getResourcePath() {
        return "/ucaasServices";
    }
}
