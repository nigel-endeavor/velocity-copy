package com.vertek.corporate.qto.service;

import com.vertek.corporate.qto.AbstractServiceResource;
import com.vertek.corporate.qto.service.mpls.MplsService;
import com.vertek.corporate.qto.service.mpls.MplsServiceManager;

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

}
