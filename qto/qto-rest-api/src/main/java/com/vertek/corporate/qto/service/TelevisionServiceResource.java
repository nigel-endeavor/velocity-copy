package com.vertek.corporate.qto.service;

import com.vertek.corporate.qto.AbstractServiceResource;
import com.vertek.corporate.qto.service.television.TelevisionService;
import com.vertek.corporate.qto.service.television.TelevisionServiceManager;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * @author bmccormick
 * @since 2/7/2024
 */
@Path("/televisionService")
@Consumes("application/json")
@Produces("application/json")
public class TelevisionServiceResource extends AbstractServiceResource<TelevisionService> {

    @Inject
    private TelevisionServiceManager manager;

    @Override
    protected TelevisionServiceManager getManager() {
        return manager;
    }
}
