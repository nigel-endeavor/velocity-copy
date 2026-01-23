package com.vertek.corporate.qto.service;

import com.vertek.corporate.qto.AbstractServiceResource;
import com.vertek.corporate.qto.service.crossconnect.CrossConnectService;
import com.vertek.corporate.qto.service.crossconnect.CrossConnectServiceManager;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * Cross Connect Service Resource.
 * @author fcurran
 * @since 1/12/2024
 */
@Path("/crossConnectService")
@Consumes("application/json")
@Produces("application/json")
public class CrossConnectServiceResource extends AbstractServiceResource<CrossConnectService> {

    @Inject
    private CrossConnectServiceManager manager;

    @Override
    protected CrossConnectServiceManager getManager() {
        return manager;
    }
}
