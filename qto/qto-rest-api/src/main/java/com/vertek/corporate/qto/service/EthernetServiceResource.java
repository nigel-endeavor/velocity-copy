package com.vertek.corporate.qto.service;

import com.vertek.corporate.qto.AbstractServiceResource;
import com.vertek.corporate.qto.service.ethernet.EthernetService;
import com.vertek.corporate.qto.service.ethernet.EthernetServiceManager;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

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

}
