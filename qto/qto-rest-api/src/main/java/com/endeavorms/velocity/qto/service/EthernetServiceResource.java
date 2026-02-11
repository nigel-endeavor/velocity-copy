package com.endeavorms.velocity.qto.service;

import com.endeavorms.velocity.qto.AbstractServiceResource;
import com.endeavorms.velocity.qto.service.ethernet.EthernetService;
import com.endeavorms.velocity.qto.service.ethernet.EthernetServiceManager;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

/**
 * Ethernet Service Resource.
 * @author rcasey
 * @since 1/17/2024
 */
@Path("/ethernetServices")
@Consumes("application/json")
@Produces("application/json")
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
