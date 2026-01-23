package com.vertek.corporate.qto.service;

import com.vertek.corporate.qto.AbstractServiceResource;
import com.vertek.corporate.qto.service.broadband.BroadbandService;
import com.vertek.corporate.qto.service.broadband.BroadbandServiceManager;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * @author rcasey
 * @since 2/10/2023
 */
@Path("/broadbandService")
@Consumes("application/json")
@Produces("application/json")
public class BroadbandServiceResource extends AbstractServiceResource<BroadbandService> {

    @Inject
    private BroadbandServiceManager manager;

    @Override
    protected BroadbandServiceManager getManager() {
        return manager;
    }
}
