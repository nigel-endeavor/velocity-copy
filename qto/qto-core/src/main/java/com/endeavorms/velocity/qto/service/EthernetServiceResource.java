package com.endeavorms.velocity.qto.service;

import com.endeavorms.velocity.qto.AbstractServiceResource;
import com.endeavorms.velocity.qto.service.ethernet.EthernetService;
import com.endeavorms.velocity.qto.service.ethernet.EthernetServiceManager;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.inject.Inject;

/**
 * Ethernet Service Resource.
 * @author rcasey
 * @since 1/17/2024
 */
@RestController
@RequestMapping("/api/ethernetServices")
public class EthernetServiceResource extends AbstractServiceResource<EthernetService> {

    @Inject
    private EthernetServiceManager manager;

    @Override
    protected EthernetServiceManager getManager() {
        return manager;
    }

    @Override
    protected String getResourcePath() {
        return "/ethernetServices";
    }
}
