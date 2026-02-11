package com.endeavorms.velocity.qto.service;

import com.endeavorms.velocity.qto.AbstractServiceResource;
import com.endeavorms.velocity.qto.service.television.TelevisionService;
import com.endeavorms.velocity.qto.service.television.TelevisionServiceManager;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

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

    @Override
    protected String getResourcePath() {
        return "/televisionService";
    }
}
