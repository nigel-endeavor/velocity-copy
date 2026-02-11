package com.endeavorms.velocity.qto.service;

import com.endeavorms.velocity.qto.AbstractServiceResource;
import com.endeavorms.velocity.qto.service.mpls.MplsService;
import com.endeavorms.velocity.qto.service.mpls.MplsServiceManager;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

/**
 * @author rcasey
 * @since 5/17/2024
 */
@Path("/mplsServices")
@Consumes("application/json")
@Produces("application/json")
public class MplsServiceResource extends AbstractServiceResource<MplsService> {

        @Inject
        private MplsServiceManager manager;

        @Override
        protected MplsServiceManager getManager() {
            return manager;
        }

    @Override
    protected String getResourcePath() {
        return "/mplsServices";
    }
}
