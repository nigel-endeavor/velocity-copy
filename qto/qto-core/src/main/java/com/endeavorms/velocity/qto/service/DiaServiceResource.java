package com.endeavorms.velocity.qto.service;

import com.endeavorms.velocity.qto.AbstractServiceResource;
import com.endeavorms.velocity.qto.service.dia.DiaService;
import com.endeavorms.velocity.qto.service.dia.DiaServiceManager;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.inject.Inject;

/**
 * @author rcasey
 * @since 2/10/2023
 */
@RestController
@RequestMapping("/api/diaServices")
public class DiaServiceResource extends AbstractServiceResource<DiaService> {

    @Inject
    private DiaServiceManager manager;

    @Override
    protected DiaServiceManager getManager() {
        return manager;
    }

    @Override
    protected String getResourcePath() {
        return "/diaServices";
    }
}
