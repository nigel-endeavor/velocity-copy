package com.vertek.corporate.qto.service;

import com.vertek.corporate.qto.AbstractServiceResource;
import com.vertek.corporate.qto.service._4g5g.GService;
import com.vertek.corporate.qto.service._4g5g.GServiceManager;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * @author rcasey
 * @sicne 6/7/2023
 */
@Path("/4G5GServices")
@Consumes("application/json")
@Produces("application/json")
public class GServiceResource extends AbstractServiceResource<GService> {

    @Inject
    private GServiceManager manager;

    @Override
    protected GServiceManager getManager() {
        return manager;
    }
}
